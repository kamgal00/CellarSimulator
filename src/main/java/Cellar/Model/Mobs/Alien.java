package Cellar.Model.Mobs;

import Cellar.Model.Items.Weapons.Lightsaber;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import java.util.Random;

public class Alien extends WalkingEnemy {
    public Alien(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=60;
        maxHp=90;
        hp=90;
        attack=60;
        defense=10;
        blockChance=20;
        rightIm=new Image("file:resources/mobs/alienRight.gif");
        leftIm=new Image("file:resources/mobs/alienLeft.gif");
    }

    @Override
    void onDeath()
    {
        Random rand = new Random();
        int prob=rand.nextInt(100);
        if(prob<10){
            new Lightsaber(world, y, x);
        }
    }
}
