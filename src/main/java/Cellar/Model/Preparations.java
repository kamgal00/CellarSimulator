package Cellar.Model;

import Cellar.Model.Items.Armors.BasicArmor;
import Cellar.Model.Items.Jar;
import Cellar.Model.Items.Shields.BasicShield;
import Cellar.Model.Items.Weapons.BasicWeapon;
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
        System.out.println("generated jar at y="+lastLevel.exitY+", x="+lastLevel.exitX);

        //making player
        currentLevel=levels.get(0);
        player=new Player(currentLevel);
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
            levels.get(i).mobTypes.add(Witch.class);
            if(i>=4){levels.get(i).mobTypes.add(BigSlime.class);}
            else if(i>=2){levels.get(i).mobTypes.add(LittleSlime.class);}
        }
    }

    public static void assignItems(){
        for(int i=0; i<maxLevel; i++){
            levels.get(i).itemTypes.add(BasicWeapon.class);
            levels.get(i).itemTypes.add(BasicShield.class);
            levels.get(i).itemTypes.add(BasicArmor.class);
        }
    }

    public static void clearGame(){
        levels=new ArrayList<>();
        currentLevelIndex=0;
        endGame=false;
    }
}
