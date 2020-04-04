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

    public boolean connect(Room con){
        if(connected.contains(con.connected.get(0))){
            return true;
        }
        //if werent connected
        con.connected.addAll(connected);
        for(Integer id: con.connected){
            if(!connected.contains(id)){
                connected.add(id);
            }
        }
        return false;
    }

    public boolean equals(Room room){
        if(id==room.id){return true;}
        return false;
    }

}
