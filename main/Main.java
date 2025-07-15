package main;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ablke to close window with x
    window.setResizable(false); //window is not resizable
    window.setTitle("2D game");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    window.pack(); //causes this window to be sized to fit the preferred size and layouts of its subcompenents(GamePanel), in this case making it visible
    window.setLocationRelativeTo(null); //make it appear centered to the screen
    window.setVisible(true);
    gamePanel.startGameThread();
  }
}