package Cellar.Model;

import Cellar.Model.Mobs.BigSlime;
import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.Rat;
import Cellar.Model.Mobs.Witch;

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
        //currentLevel.addMob(new Rat(currentLevel),currentLevel.exitY+1, currentLevel.exitX);
        player.world=currentLevel;
        discover();

        //adding mob types
        //all levels
        for(int i=0; i<maxLevel; i++){
            levels.get(i).mobTypes.add(Rat.class);
            levels.get(i).mobTypes.add(Witch.class);
            if(i>=4){levels.get(i).mobTypes.add(BigSlime.class);}
        }
    }
}
