package Cellar.Model.Mobs;

import Cellar.Model.Items.Shields.GoldenShield;
import Cellar.Model.Items.Weapons.Lightsaber;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import java.util.Random;

public class Skeleton extends WalkingEnemy {
    public Skeleton(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=50;
        maxHp=120;
        hp=120;
        attack=20;
        defense=15;
        blockChance=40;
        rightIm=new Image("file:resources/mobs/skeletonRight.gif");
        leftIm=new Image("file:resources/mobs/skeletonLeft.gif");
    }

    @Override
    void onDeath()
    {
        Random rand = new Random();
        int prob=rand.nextInt(100);
        if(prob<10){
            new GoldenShield(world, y, x);
        }
    }
}
