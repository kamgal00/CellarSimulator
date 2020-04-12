package Cellar.Model.Mobs;
import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model.*;

import java.util.Optional;

import static Cellar.Model.Model.*;
import static Cellar.Model.Model.player;
import static Cellar.View.View.*;
import static Cellar.View.View.playerRight;

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
    }
    @Override
    public void moveMob() {
        switch (direction)
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
        }
    }
}
