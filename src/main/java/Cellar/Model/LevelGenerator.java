package Cellar.Model;

import java.util.Random;

import static Cellar.Model.Model.*;

public class LevelGenerator {
    public Level levelGenerate(){
        Level gen=new Level(levelSize, levelSize);
        Random rand = new Random();
        RoomGenerator roomGenerator=new RoomGenerator();

        int numberOfRooms=0;

        //inserting rooms
        Room[][] rooms=new Room[levelSize][levelSize];
        for(int i=0; i<levelSize; i++){
            for (int j=0; j<levelSize; j++){
                if(rand.nextInt(100)<40){
                    rooms[i][j]=roomGenerator.nextRoom();
                    numberOfRooms++;
                }
            }
        }

        //connecting with corridors

        //fuse to array
        gen.fuse(rooms);
        return gen;
    }
}
