package Cellar.Model;

import static Cellar.Model.Model.*;

public class Room {

    //data
    //coordinates of left upper corner of the room
    public int x;
    public int y;
    public int id;

    public Field[][] fields=new Field[roomSize][roomSize];

    public void moveRoom(int x, int y){
        this.x+=x;
        this.y+=y;
    }
}
