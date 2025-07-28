package main;

import entity.Entity;

public class CollisionChecker {
  GamePanel gp;

  public CollisionChecker(GamePanel gp) {
    this.gp = gp;
  }

  public void CheckTile(Entity entity) {
    // calculate pixel coordinates of each edge of the collision box
    int entityLeftWorldX = entity.worldX + entity.solidArea.x; // left edge of collision box
    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // right edge of the box
    int entityTopWorldY = entity.worldY + entity.solidArea.y;// top edge of solid area
    int entityDownWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; // left edge of the box

    // divide with tilesize to determine which tile col&row the collision box edges
    // are at
    int entityLeftCol = entityLeftWorldX / gp.tileSize;
    int entityRightCol = entityRightWorldX / gp.tileSize;
    int entityTopRow = entityTopWorldY / gp.tileSize;
    int entityDownRow = entityDownWorldY / gp.tileSize;

    int tileNum1, tileNum2;

    switch (entity.direction) {
      case "up":
        // subtract speed to give illusion of character sprite colliding, not the sprite
        // box colliding
        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityDownRow = (entityDownWorldY + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityDownRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityDownRow];
        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityDownRow];
        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityDownRow];
        if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
          entity.collisionOn = true;
        }
        break;
      default:
        break;
    }
  }

  public int checkObject(Entity entity, boolean player) {
    int index = 999;

    for (int i = 0; i < gp.obj.length; i++) {
      if (gp.obj[i] != null) {
        // get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // get the objects solid area postion
        gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
        gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

        switch (entity.direction) {
          case "up":
            entity.solidArea.y -= entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }
              if (player == true) {
                index = i;
              }
            }
            break;
          case "down":
            entity.solidArea.y += entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }
              if (player == true) {
                index = i;
              }
            }
            break;
          case "left":
            entity.solidArea.x -= entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }
              if (player == true) {
                index = i;
              }
            }
            break;
          case "right":
            entity.solidArea.x += entity.speed;
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
              if (gp.obj[i].collision == true) {
                entity.collisionOn = true;
              }
              if (player == true) {
                index = i;
              }
            }
            break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
        gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
      }
    }
    return index;
  }
}