package Cellar.Model.Mobs;
import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model.*;
import javafx.scene.image.Image;

import java.util.Optional;

import static Cellar.Model.Model.*;

public class Player extends Mob {
    public Player(Level world)
    {
        super(world);
    }
    @Override
    public void setParams() {
        maxHp=100;
        hp=100;
        attack=15;
        defense=5;
        rightIm=new Image("file:resources/manright.gif");
        leftIm=new Image("file:resources/manleft.gif");
    }
    @Override
    public void moveMob() {
        Mob en = move(direction);
        if(en!=this)
        {
            if(en==null) return;
            if(en instanceof Enemy)
            {
                attack(en);
            }
        }
        /*switch (direction)
        {
            case none:
                currentAction=actionType.none;
                break;
            case up:
                if(world.field[y-1][x].getType()!= Field.TypeOfField.wall)
                {
                    Mob en=null;
                    for(Mob m: world.mobs) if(m.isHere(y-1,x)) en=m;
                    if(en==null)
                    {
                        y--;
                        currentAction=actionType.up;
                    }
                    else if(en instanceof Enemy)
                    {
                        attack(en);
                        currentAction=actionType.attack;
                    }
                    else currentAction=actionType.none;
                }
                else currentAction=actionType.none;
                break;
            case down:
                if(world.field[y+1][x].getType()!= Field.TypeOfField.wall)
                {
                    Mob en=null;
                    for(Mob m: world.mobs) if(m.isHere(y+1,x)) en=m;
                    if(en==null)
                    {
                        y++;
                        currentAction=actionType.down;
                    }
                    else if(en instanceof Enemy)
                    {
                        attack(en);
                        currentAction=actionType.attack;
                    }
                    else currentAction=actionType.none;
                }
                else currentAction=actionType.none;
                break;
            case left:
                if(world.field[y][x-1].getType()!= Field.TypeOfField.wall)
                {
                    Mob en=null;
                    for(Mob m: world.mobs) if(m.isHere(y,x-1)) en=m;
                    if(en==null)
                    {
                        x--;
                        currentAction=actionType.left;
                    }
                    else if(en instanceof Enemy)
                    {
                        attack(en);
                        currentAction=actionType.attack;
                    }
                    else currentAction=actionType.none;
                }
                else currentAction=actionType.none;
                break;
            case right:
                if(world.field[y][x+1].getType()!= Field.TypeOfField.wall)
                {
                    Mob en=null;
                    for(Mob m: world.mobs) if(m.isHere(y,x+1)) en=m;
                    if(en==null)
                    {
                        x++;
                        currentAction=actionType.right;
                    }
                    else if(en instanceof Enemy)
                    {
                        attack(en);
                        currentAction=actionType.attack;
                    }
                    else currentAction=actionType.none;
                }
                else currentAction=actionType.none;
                break;
        }*/
    }
}
