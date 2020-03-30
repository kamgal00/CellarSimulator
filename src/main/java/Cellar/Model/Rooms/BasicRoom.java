package Cellar.Model.Rooms;

import Cellar.Model.Field;
import Cellar.Model.Room;

//square 5x5

public class BasicRoom extends Room {
     public BasicRoom(int x, int y){
         this.x=x;
         this.y=y;
         for(int i=5; i<10; i++){
             for (int j=5; j<10; j++){
                 fields.add(new Field(i, j));
             }
         }
     }
}
