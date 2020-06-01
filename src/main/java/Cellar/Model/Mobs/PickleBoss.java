package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
import javafx.scene.image.Image;

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
        defense=30;
        blockChance=10;
        rightIm=new Image("file:resources/mobs/pickleBossRight.gif");
        leftIm=new Image("file:resources/mobs/pickleBossLeft.gif");
    }
    @Override
    void onDeath(){
        for(int xd=0;xd<3;xd++) for(int yd=0;yd<3;yd++) {
            if(xd==1 && yd==1) continue;
            currentLevel.field[currentLevel.entranceY-1+yd][currentLevel.entranceX-1+xd].typeOfField= Field.TypeOfField.floor;
        }
    }
}
