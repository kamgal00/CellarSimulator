package Cellar.Model.Mobs;

import Cellar.Model.Level;
import javafx.scene.image.Image;

public class BigSlime extends WalkingEnemy {
    public BigSlime(Level world){super(world);}
    @Override
    public void setParams() {
        expForKill=40;
        maxHp=50;
        hp=50;
        attack=20;
        defense=10;
        blockChance=3;
        rightIm=new Image("file:resources/mobs/bigSlimeRight.gif");
        leftIm=new Image("file:resources/mobs/bigSlimeLeft.gif");
    }
}
