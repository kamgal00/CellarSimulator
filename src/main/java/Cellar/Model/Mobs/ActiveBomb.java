package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import javafx.scene.image.Image;

import static Cellar.Model.Model.*;

public class ActiveBomb extends WalkingEnemy {
    public ActiveBomb(Level w) {
        super(w);
    }

    @Override
    public void setParams() {
        expForKill=50;
        maxHp=100;
        hp=100;
        attack=50;
        defense=0;
        blockChance=0;
        rightIm=new Image("file:resources/mobs/activeBombRight.gif");
        leftIm=new Image("file:resources/mobs/activeBombLeft.gif");
    }

    @Override
    public boolean moveMob(){
        die();
        for(int i=this.y-1; i<=this.y+1; i++){
            for(int j=this.x-1; j<=this.x+1; j++){
                if(i>0 && i<roomSize*levelSize && j>0 && j<roomSize*levelSize){
                    if(world.field[i][j].getType()== Field.TypeOfField.wall){
                        world.field[i][j]=new Field(Field.TypeOfField.floor);
                    }
                    else{
                        if(world.field[i][j].mob!=null){
                            Mob exploded=world.field[i][j].mob;
                            this.attack(exploded);
                        }
                    }
                }
            }
        }
        return true;
    }
}
