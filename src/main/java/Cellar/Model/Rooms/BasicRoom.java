package Cellar.Model.Rooms;

import Cellar.Model.Field;
import Cellar.Model.Room;

//square 9x9

public class BasicRoom extends Room {
     public BasicRoom(){
         for(int i=1; i<10; i++){
             for (int j=1; j<10; j++){
                 fields[i][j]=new Field(Field.TypeOfField.wall);
             }
         }
     }
}
