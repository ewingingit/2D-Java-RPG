package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjChest extends SuperObject{
   public ObjChest(){
    name = "Chest";
    try{
      image=ImageIO.read(getClass().getResourceAsStream("/res/objects/Chest.png"));
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}

