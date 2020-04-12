package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
public class Zombie extends Enemy{
    @Override
    public void moveMob() {
        //System.out.println("Zyje!");
        int dist=world.field[y][x].distance;
        if(dist==-1)
        {
            //System.out.println("Czekam");
            currentAction=actionType.none;
            return;
        }
        else if(isClose(Model.player.y,Model.player.x))
        {
            currentAction=actionType.attack;
            attack(Model.player);
        }
        else
        {
            if(world.field[y+1][x].distance==dist-1)
            {
                currentAction=actionType.down;
                y++;
            }
            else if(world.field[y-1][x].distance==dist-1)
            {
                currentAction=actionType.up;
                y--;
            }
            else if(world.field[y][x-1].distance==dist-1)
            {
                currentAction=actionType.left;
                x--;
            }
            else if(world.field[y][x+1].distance==dist-1)
            {
                currentAction=actionType.right;
                x++;
            }
            //System.out.println("Ruszam zombie na "+y+" "+x);
        }
    }
    public Zombie(Level world){super(world);}
    public void setParams()
    {
        maxHp=50;
        hp=50;
        attack=7;
        defense=4;
    }
}
