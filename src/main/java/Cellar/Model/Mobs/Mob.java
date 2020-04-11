package Cellar.Model.Mobs;

import Cellar.Model.Level;

public abstract class Mob {
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int x;
    public int y;
    Level world;
    public Mob(Level world){this.world=world; setParams();}
    public abstract void setParams();
    public abstract void moveMob();
}
