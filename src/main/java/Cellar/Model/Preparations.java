package Cellar.Model;

import Cellar.Model.Items.Armors.*;
import Cellar.Model.Items.Jar;
import Cellar.Model.Items.Shields.*;
import Cellar.Model.Items.Weapons.*;
import Cellar.Model.Mobs.*;

import java.util.ArrayList;

import static Cellar.Model.ItemGenerator.generateItems;
import static Cellar.Model.Model.*;
import static Cellar.Model.UberBrain.discover;

public class Preparations {
    public static void prepareLevels(){

        //generating all levels
        for(int i=0; i<maxLevel; i++){
            RoomGenerator.loadRoomGenerator();
            LevelGenerator levelGenerator=new LevelGenerator();
            levels.add(levelGenerator.levelGenerate());
        }

        //generating jar
        Level lastLevel=levels.get(maxLevel-1);
        lastLevel.field[lastLevel.exitY][lastLevel.exitX]=new Field(Field.TypeOfField.floor);
        new Jar(lastLevel, lastLevel.exitY, lastLevel.exitX);

        //making player
        currentLevel=levels.get(0);
        player=new Player(currentLevel);
        eqIn=new EqInterface(player.eq);
        currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
        discover();

        //adjusting levels
        assignMobs();
        assignItems();
        for(int i=0; i<maxLevel; i++){
            generateItems(levels.get(i));
        }
    }

    public static void assignMobs(){
        for(int i=0; i<maxLevel; i++){
            levels.get(i).mobTypes.add(Rat.class);
            if(i>=6){levels.get(i).mobTypes.add(Alien.class);}
            if(i>=5){levels.get(i).mobTypes.add(Skeleton.class);}
            if(i>=2){levels.get(i).mobTypes.add(Zombie.class);}
            if(i>=1){levels.get(i).mobTypes.add(Witch.class);}
            if(i>=4){levels.get(i).mobTypes.add(BigSlime.class);}
            else if(i>=2){levels.get(i).mobTypes.add(LittleSlime.class);}
            if(i>=3){levels.get(i).mobTypes.add(WalkingBomb.class);}
        }
    }

    public static void assignItems(){
        for(int i=0; i<maxLevel; i++){
            if(i<5){levels.get(i).itemTypes.add(LeatherArmor.class);}
            if(i>=5){levels.get(i).itemTypes.add(SteelArmor.class);}
            if(i<5){levels.get(i).itemTypes.add(WoodenShield.class);}
            if(i>=5){levels.get(i).itemTypes.add(SteelShield.class);}
            if(i<5){levels.get(i).itemTypes.add(WoodenSword.class);}
            if(i>=5){levels.get(i).itemTypes.add(SteelSword.class);}
        }
    }

    public static void clearGame(){
        levels=new ArrayList<>();
        currentLevelIndex=0;
        endGame=false;
    }
}
