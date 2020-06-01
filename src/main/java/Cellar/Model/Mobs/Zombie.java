package Cellar.Model.Mobs;

import Cellar.Model.Items.Armors.GoldenArmor;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import java.util.Random;

public class Zombie extends WalkingEnemy {
    public Zombie(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=40;
        maxHp=100;
        hp=100;
        attack=20;
        defense=20;
        blockChance=20;
        rightIm=new Image("file:resources/mobs/zombieRight.gif");
        leftIm=new Image("file:resources/mobs/zombieLeft.gif");
    }

    @Override
    void onDeath()
    {
        Random rand = new Random();
        int prob=rand.nextInt(100);
        if(prob<10){
            new GoldenArmor(world, y, x);
        }
    }
}
