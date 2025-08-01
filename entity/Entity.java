package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
  public int worldX,worldY; //top left of the sprite
  public int speed;

  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public String direction;

  public int spriteCounter =0;
  public int spriteNum =1;
  public Rectangle solidArea; //can be made invisible/abstract for collision detection
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collisionOn=false;
}