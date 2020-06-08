package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
import javafx.scene.image.Image;

public class Rat extends WalkingEnemy{
    public Rat(Level world){super(world);}
    @Override
    public void setParams()
    {
        expForKill=10;
        maxHp=50;
        hp=50;
        attack=7;
        defense=4;
        blockChance=10;
        rightIm=new Image("file:resources/mobs/ratRight.gif");
        leftIm=new Image("file:resources/mobs/ratLeft.gif");
    }
}
