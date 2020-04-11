package Cellar.Model.Mobs;

public abstract class Mob {
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int x;
    public int y;

    public abstract void moveMob();
}
