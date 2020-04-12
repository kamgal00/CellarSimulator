package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
public class Zombie extends WalkingEnemy{
    public Zombie(Level world){super(world);}
    @Override
    public void setParams()
    {
        maxHp=50;
        hp=50;
        attack=7;
        defense=4;
    }
}
