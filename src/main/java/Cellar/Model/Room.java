package Cellar.Model;

import java.util.ArrayList;

public class Room {

    //data
    //coordinates of left upper corner of the room
    public int x;
    public int y;

    public ArrayList<Field> fields=new ArrayList<>();

    public void moveRoom(int x, int y){
        this.x+=x;
        this.y+=y;
    }
}
