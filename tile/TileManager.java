package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.Graphics2D;

public class TileManager {
  GamePanel gp;
  Tile[] tile;
  int mapTileNum[][];

  public TileManager(GamePanel gp) {
    this.gp = gp;
    tile = new Tile[10];
    getTileImage();
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    loadMap("/res/maps/world01.txt");
  }

  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass01.png"));

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));

      tile[3] = new Tile();
      tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));

      tile[5] = new Tile();
      tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // method to render the map
  public void loadMap(String map) {
    try {
      // boiler plate code to read text in the text file
      InputStream is = getClass().getResourceAsStream(map);
      BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read the content of the text file

      int col = 0;
      int row = 0;
      // World map has become the boundary as compared to just the screen/frame
      while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
        String line = br.readLine(); // reads A line of text in the txt file

        while (col < gp.maxWorldCol) {
          String numbers[] = line.split(" ");
          int num = Integer.parseInt(numbers[col]); // after reading, turns string into integer
          mapTileNum[col][row] = num;
          col++;
          System.out.println("tile " + col + " row " + row);
        }
        if (col == gp.maxWorldCol) {
          col = 0;
          row++;
          System.out.println("row done loading" + row);
        }

      }
      br.close();
    } catch (Exception e) {
    }
  }

  // loop to render the background tiles
  public void draw(Graphics2D g2) {
    int worldCol = 0;
    int worldRow = 0;

    while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
      int tileNum = mapTileNum[worldCol][worldRow]; // extracts the tile number

      int worldX = worldCol * gp.tileSize; // position of the tile on the map(txt file)
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;// subtracting player.worldx value to represent where
                                                                  // the tile loads relative to the player
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      // ONLY WHEN TILE IS WITIHN THE BOUNDARY OF SCREEN X AND Y, IT WILL BE DRAWN!!
      //do this to save processing power if map is too big and has many tiles to render
      if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
          worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
          worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
          worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
      }
      worldCol++; // the next tile to the right

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}
