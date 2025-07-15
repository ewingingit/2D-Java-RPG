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
    mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
    loadMap("/res/maps/map01.txt");
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

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

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

      while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
        String line = br.readLine(); // reads A line of text in the txt file

        while (col < gp.maxScreenCol) {
          String numbers[] = line.split(" ");
          int num = Integer.parseInt(numbers[col]); // after reading, turns string into integer
          mapTileNum[col][row] = num;
          col++;
        }
        if (col == gp.maxScreenCol) {
          col = 0;
          row++;
        }

      }
      br.close();
    } catch (Exception e) {
    }
  }

  // loop to render the background tiles
  public void draw(Graphics2D g2) {
    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
      int tileNum = mapTileNum[col][row]; // extracts the tile number

      g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
      col++; // the next tile to the right
      x += gp.tileSize; // the next tile

      if (col == gp.maxScreenCol) {
        col = 0;
        x = 0;
        row++;
        y += gp.tileSize;
      }
    }
  }
}
