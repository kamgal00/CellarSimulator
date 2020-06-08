package Cellar.Model.Mobs;

import Cellar.Model.Level;
import javafx.scene.image.Image;

public class LittleSlime extends WalkingEnemy {
    public LittleSlime(Level world){super(world);}
    @Override
    public void setParams() {
        expForKill=15;
        maxHp=20;
        hp=20;
        attack=5;
        defense=10;
        blockChance=10;
        rightIm=new Image("resources/mobs/littleSlimeRight.gif");
        leftIm=new Image("resources/mobs/littleSlimeLeft.gif");
    }
}
