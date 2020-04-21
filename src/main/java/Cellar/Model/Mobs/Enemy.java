package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;

public abstract class Enemy extends Mob {
    public Enemy(Level world){super(world);}
    @Override
    void die()
    {
        super.die();
        Model.player.gainExp(expForKill);
    }
}
