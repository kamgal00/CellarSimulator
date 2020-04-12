package Cellar.Model;

import Cellar.Model.Mobs.Rat;

import static Cellar.Model.Model.*;
import static Cellar.Model.UberBrain.discover;

public class Preparations {
    public static void prepareLevels(){

        for(int i=0; i<maxLevel; i++){
            RoomGenerator.loadRoomGenerator();
            LevelGenerator levelGenerator=new LevelGenerator();
            levels.add(levelGenerator.levelGenerate());
        }

        currentLevel=levels.get(0);
        currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
        currentLevel.addMob(new Rat(currentLevel),currentLevel.exitY+1, currentLevel.exitX);
        player.world=currentLevel;
        discover();
    }
}
