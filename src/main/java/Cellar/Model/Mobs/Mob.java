package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model;
import javafx.scene.image.Image;

import static Cellar.Model.Model.direction;

public abstract class Mob {
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int x;
    public int y;
    public Level world;
    public Mob attackedMob;
    public enum actionType{none, up,down,left,right,leftUp,leftDown,rightUp,rightDown,pickup,attack}
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
    public boolean isHere(int y,int x)
    {
        return (this.x==x&&this.y==y);
    }
    public boolean isClose(int y,int x)
    {
        if(x==this.x &&this.y-y==1) return true;
        if(x==this.x &&this.y-y==-1) return true;
        if(y==this.y &&this.x-x==1) return true;
        if(y==this.y &&this.x-x==-1) return true;
        return false;
    }
    public abstract void setParams();
    public abstract void moveMob();
    public void attack(Mob enemy)
    {
        System.out.println(this.getClass().getName()+" attacked "+enemy.getClass().getName()+"!");
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
