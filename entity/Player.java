package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  GamePanel gp;
  KeyHandler keyH;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;
    setDefaultValues();
    getPlayerImage();
    spriteNum = 1;
  }

  public void setDefaultValues() {
    x = 100;
    y = 100;
    speed = 4;
    direction = "right";
  }

  public void getPlayerImage() {
    System.out.println("image loading started");
    try {// setting each image to a direction
      up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierup1.png"));
      up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierup2.png"));
      down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierdown1.png"));
      down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierdown2.png"));
      left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierleft1.png"));
      left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierleft2.png"));
      right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierright1.png"));
      right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/Soldierright2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("image loading ended");
  }

  public void update() { // gets called 60 times per second as its in the game loop
    // this if statement makes it so that the sprites only change when you are
    // pressing the keys
    if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
      if (keyH.upPressed == true) {
        direction = "up";
        y -= speed;
      } else if (keyH.rightPressed == true) {
        direction = "right";
        x += speed;
      } else if (keyH.leftPressed == true) {
        direction = "left";
        x -= speed;
      } else if (keyH.downPressed == true) {
        direction = "down";
        y += speed;
      }

      spriteCounter++;
      if (spriteCounter > 8) { // this means every player image changes every 12 frames
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }
  }

  public void draw(Graphics2D g2) {
    // g2.setColor(Color.white); // setting the color to use for drawing objects.
    // g2.fillRect(x, y, gp.tileSize, gp.tileSize); // draws a rectangle with a
    // specific colour

    BufferedImage image = null;

    // switch-cases to determine which img var to render depending on direction and
    // spriteNum
    switch (direction) {
      case "up":
        if (spriteNum == 1) {
          image = up1;
        }
        if (spriteNum == 2) {
          image = up2;
        }
        break;
      case "down":
        if (spriteNum == 1) {
          image = down1;
        }
        if (spriteNum == 2) {
          image = down2;
        }
        break;
      case "left":
        if (spriteNum == 1) {
          image = left1;
        }
        if (spriteNum == 2) {
          image = left2;
        }
        break;
      case "right":
        if (spriteNum == 1) {
          image = right1;
        }
        if (spriteNum == 2) {
          image = right2;
        }
        break;
    }
    g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
  }
}
