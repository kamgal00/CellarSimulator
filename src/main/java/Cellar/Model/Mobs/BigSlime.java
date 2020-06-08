package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import java.util.Random;

import static Cellar.Model.Model.*;

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
        rightIm=new Image("resources/mobs/bigSlimeRight.gif");
        leftIm=new Image("resources/mobs/bigSlimeLeft.gif");
    }

    @Override
    void onDeath()
    {
        LittleSlime slime1=new LittleSlime(currentLevel);

        currentLevel.addMob(slime1, this.y, this.x);

        //checking if there is place for other slime
        boolean isPlace=false;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(currentLevel.field[this.y+j][this.x+i].getType()!= Field.TypeOfField.wall && currentLevel.field[this.y+j][this.x+i].mob==null){
                    isPlace=true;
                    break;
                }
            }
            if(isPlace){
                LittleSlime slime2=new LittleSlime(currentLevel);
                Random rand = new Random();
                int xx=rand.nextInt(3);
                int yy=rand.nextInt(3);
                while ( !(currentLevel.field[this.y-1+yy][this.x-1+xx].getType()!= Field.TypeOfField.wall && currentLevel.field[this.y-1+yy][this.x-1+xx].mob==null)){
                    xx=rand.nextInt(3);
                    yy=rand.nextInt(3);
                }
                currentLevel.addMob(slime2, this.y-1+yy, this.x-1+xx);
                break;
            }
        }
    }
}
