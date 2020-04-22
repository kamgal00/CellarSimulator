package Cellar.Model;

import Cellar.Model.Mobs.*;

import java.util.ArrayList;

import static Cellar.Model.ItemGenerator.generateItems;
import static Cellar.Model.Model.*;
import static Cellar.Model.UberBrain.discover;

public class Preparations {
    public static void prepareLevels(){

        for(int i=0; i<maxLevel; i++){
            RoomGenerator.loadRoomGenerator();
            LevelGenerator levelGenerator=new LevelGenerator();
            levels.add(levelGenerator.levelGenerate());
            generateItems(levels.get(i));
        }

        currentLevel=levels.get(0);
        player=new Player(currentLevel);
        currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
        discover();

        if(levels.get(0).mobTypes.isEmpty()){
            assignMobs();
        }

    }

    public static void assignMobs(){
        for(int i=0; i<maxLevel; i++){
            levels.get(i).mobTypes.add(Rat.class);
            levels.get(i).mobTypes.add(Witch.class);
            if(i>=4){levels.get(i).mobTypes.add(BigSlime.class);}
            else if(i>=2){levels.get(i).mobTypes.add(LittleSlime.class);}
        }
    }

    public static void clearGame(){
        levels=new ArrayList<>();
        currentLevelIndex=0;
    }
}
