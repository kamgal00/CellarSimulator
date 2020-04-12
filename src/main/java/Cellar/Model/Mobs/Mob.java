package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;

public abstract class Mob {
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int x;
    public int y;
    public Level world;
    public Mob attackedMob;
    public Mob(Level world)
    {
        this.world=world;
        setParams();
        currentAction=actionType.none;
        attackedMob=null;
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
    public enum actionType{none, up,down,left,right,pickup,attack}
    public actionType currentAction;
    public void attack(Mob enemy)
    {
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
}
