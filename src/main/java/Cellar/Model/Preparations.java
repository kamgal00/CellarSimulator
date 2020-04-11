package Cellar.Model;

import static Cellar.Model.Model.*;

public class Preparations {
    public static void prepareLevels(){

        for(int i=0; i<maxLevel; i++){
            System.out.println(i);
            RoomGenerator.loadRoomGenerator();
            LevelGenerator levelGenerator=new LevelGenerator();
            levels.add(levelGenerator.levelGenerate());
        }

        currentLevel=levels.get(0);
        currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
    }
}
