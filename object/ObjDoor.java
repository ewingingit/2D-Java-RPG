package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjDoor extends SuperObject {
  public ObjDoor(){
    name = "Door";
    try{
      image=ImageIO.read(getClass().getResourceAsStream("/res/objects/door.jpg"));
    }catch(IOException e){
      e.printStackTrace();
    }
    collision=true;
  }
}
