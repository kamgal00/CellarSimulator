package Cellar.Model;

import java.util.ArrayList;

import static Cellar.Model.Model.*;

public class Room {

    //data
    public int id;
    ArrayList<Integer> connected=new ArrayList<>();

    public Field[][] fields=new Field[roomSize][roomSize];

    public void setId(int id){
        this.id=id;
    }

    public boolean equal(Room room){
        if(id==room.id){return true;}
        return false;
    }

}
