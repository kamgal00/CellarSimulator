package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Items.Armors.PickleArmor;
import Cellar.Model.Items.Shields.PickleShield;
import Cellar.Model.Items.Weapons.Lightsaber;
import Cellar.Model.Items.Weapons.PickleSword;
import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
import javafx.scene.image.Image;

import java.util.Random;

import static Cellar.Model.Model.*;

public class PickleBoss extends WalkingEnemy {
    public PickleBoss(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=100;
        maxHp=200;
        hp=200;
        attack=100;
        /*
        hp=1;
        attack=1;
        */
        defense=30;
        blockChance=10;
        rightIm=new Image("file:resources/mobs/pickleBossRight.gif");
        leftIm=new Image("file:resources/mobs/pickleBossLeft.gif");
    }
    @Override
    void onDeath(){
        for(int xd=0;xd<3;xd++) for(int yd=0;yd<3;yd++) {
            if(xd==1 && yd==1) continue;
            currentLevel.field[currentLevel.entranceY][currentLevel.entranceX].typeOfField= Field.TypeOfField.entrance;
        }

        Random rand = new Random();
        int prob=rand.nextInt(3);
        switch (prob){
            case 0:
                new PickleSword(world, y, x);
                break;
            case 1:
                new PickleShield(world, y, x);
                break;
            case 2:
                new PickleArmor(world, y, x);
                break;
        }
    }
}
