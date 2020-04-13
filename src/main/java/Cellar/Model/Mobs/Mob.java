package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model;
import javafx.scene.image.Image;
import java.util.Random;

public abstract class Mob {
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int x;
    public int y;
    public Level world;
    public Mob attackedMob;
    public enum actionType{none, up,down,left,right,leftUp,leftDown,rightUp,rightDown,pickup,attack,wait}
    public actionType currentAction;
    public Image leftIm,rightIm,currIm;
    public Mob(Level world)
    {
        this.world=world;
        setParams();
        currentAction=actionType.none;
        attackedMob=null;
        currIm=rightIm;
    }
    public abstract void setParams();
    public abstract void moveMob();
    public void attack(Mob enemy)
    {
        Random rand = new Random();
        int dmg_min = 8 * this.attack / 10;
        int dmg_max = (125 * this.attack + 99) / 100;
        int dmg = (rand.nextInt((dmg_max - dmg_min) + 1) + dmg_min) - enemy.defense;
        if(dmg <= 0)
        {
            enemy.hp -= 1;
            System.out.println(this.getClass().getSimpleName()+" dealt 1 damage to a "+enemy.getClass().getSimpleName()+"!");
        }
        else
        {
            enemy.hp -= dmg;
            System.out.println(this.getClass().getSimpleName()+" dealt "+dmg+" damage to a "+enemy.getClass().getSimpleName()+"!");
        }
        if(enemy.hp < 0) enemy.hp = 0;
        currentAction=actionType.attack;
    }
    public boolean isVisible()
    {
        return( Model.player.x-Model.width/2<=x&&Model.player.x+Model.width/2>=x&&Model.player.y-Model.height/2<=y&&Model.player.y+Model.height/2>=y);
    }
    public String toString()
    {
        return this.getClass().toString()+" "+y+" "+x;
    }
    public Mob move(Model.Dir direct)
    {
        switch (direct)
        {
            case none:
                currentAction=actionType.none;
                return this;
            case up:
                if(world.field[y-1][x].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x].mob==null)
                    {
                        world.field[y][x].mob=null;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.up;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case down:
                if(world.field[y+1][x].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x].mob==null)
                    {
                        world.field[y][x].mob=null;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.down;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case left:
                currIm=leftIm;
                if(world.field[y][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.left;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case right:
                currIm=rightIm;
                if(world.field[y][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.right;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case rightUp:
                currIm=rightIm;
                if(world.field[y-1][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.rightUp;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case rightDown:
                currIm=rightIm;
                if(world.field[y+1][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.rightDown;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case leftUp:
                currIm=leftIm;
                if(world.field[y-1][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.leftUp;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case leftDown:
                currIm=leftIm;
                if(world.field[y+1][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.leftDown;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
        }
        return null;
    }
}
