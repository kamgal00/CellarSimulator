package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import java.util.Random;

import static Cellar.Model.Model.currentLevel;

public class WalkingBomb extends WalkingEnemy {
    public WalkingBomb(Level w) {
        super(w);
    }

    @Override
    public void setParams() {
        expForKill=0;
        maxHp=1;
        hp=1;
        attack=0;
        defense=0;
        blockChance=0;
        rightIm=new Image("file:resources/mobs/bombRight.gif");
        leftIm=new Image("file:resources/mobs/bombLeft.gif");
    }

    @Override
    void onDeath()
    {
        ActiveBomb AB=new ActiveBomb(world);
        if(currIm==rightIm){
            AB.currIm=AB.rightIm;
        }
        else {
            AB.currIm=AB.leftIm;
        }
        currentLevel.addMob(AB, this.y, this.x);
    }
}
