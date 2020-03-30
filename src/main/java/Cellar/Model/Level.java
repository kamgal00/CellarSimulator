package Cellar.Model;

import java.util.ArrayList;

public class Level {
    public static ArrayList<Room> rooms=new ArrayList<>();

    public static void addRoom(Room room){
        rooms.add(room);
    }

    public static void cleanLevel(){
        rooms.clear();
    }
}
