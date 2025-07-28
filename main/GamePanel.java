package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

  // SCREEN SETTINGS
  public final int originalTileSize = 16; // 16x16 tile size, most 2D game elements and chars use this size
  public final int scale = 3; // to scale up
  public final int tileSize = originalTileSize * scale; // 48x48 tile size
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
  public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

  // WORLD SETTINGS
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  public final int worldWidth = tileSize * maxWorldCol;
  public final int worldHeight = tileSize * maxWorldRow;
  // FPS
  int FPS = 60;

  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  public CollisionChecker cCheck = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public Player player = new Player(this, keyH); // pass gamepanel class and keyhandler to player class
  public SuperObject obj[]= new SuperObject[10]; //array to store objects to be displayed on the screen at all times

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of the class JPanel
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);// all drawings from this component will be done in an offscreen pointing buffer
    this.setFocusable(true); // allows the GamePanel to be able to be "focused" on to recieve key inputs.
    this.addKeyListener(keyH); // gamepanel will recognise the key inputs
  }

  public void setupGame(){ //this method to add other setup stuff in the future
    aSetter.setObject();
  }

  public void startGameThread() {
    // This refers to GamePanel, basically passing GamePanel to this thread
    gameThread = new Thread(this);
    gameThread.start();
  }

  // This game loop waits for the timer to beep, then only updating+repainting
  // public void run() {

  // double drawInterval = 1000000000 / FPS; // 0.01666 seconds, means drawing the
  // screen 60 times per sec
  // double nextDrawTime = System.nanoTime() + drawInterval; // allocates 0.01666
  // seconds to update and repaint

  // // this loop acts as the core of our game
  // // 2 things will be done within this loop
  // while (gameThread != null) {
  // // 1. UPDATE: update info such as character postiions
  // update();
  // // 2. Draw:draw the screen with the updated information
  // // repaint is how you call the paintComponent method
  // repaint();

  // try {
  // double remainingTime = nextDrawTime - System.nanoTime();
  // remainingTime = remainingTime/1000000; //converts nano to milli

  // if (remainingTime<0) {
  // remainingTime=0;
  // }

  // Thread.sleep((long) remainingTime);//the thread pauses for the remainding
  // time left after update and repaint is run
  // nextDrawTime +=drawInterval;
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // }
  // }

  // this game loop waits for the water bucket to fill up, only then it
  // update+repaint
  public void run() {
    double drawInterval = 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;
      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }
      if (timer >= 1000000000) {
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {
    player.update();
  }

  // paintComponent is an in-built method in Java, used to draw things in Jpanel
  public void paintComponent(Graphics g) {
    // when using paintComponent, must write this super.paintComponent()
    super.paintComponent(g);

    // Graphics2D extends graphics class, provides more control over geometry,
    // coordinate transformations, color mngmnt & text layout
    Graphics2D g2 = (Graphics2D) g;

    //TILE
    tileM.draw(g2);
    
    //OBJECT
    for(int i=0;i<obj.length;i++){
      if(obj[i]!=null){
        obj[i].draw(g2,this);
      }
    }

    //PLAYER
    player.draw(g2);
    g2.dispose();
    // dispose of this graphics context and release any system resource that it is
    // using
  }
}