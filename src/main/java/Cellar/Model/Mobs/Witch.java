package Cellar.Model.Mobs;

import Cellar.Model.Level;
import javafx.scene.image.Image;

public class Witch extends WalkingEnemy {
    public Witch(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=20;
        maxHp=30;
        hp=30;
        attack=15;
        defense=2;
        blockChance=3;
        rightIm=new Image("file:resources/mobs/witchRight.gif");
        leftIm=new Image("file:resources/mobs/witchLeft.gif");
    }
}
