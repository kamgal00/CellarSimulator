package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;

public abstract class WalkingEnemy extends Enemy {
    public WalkingEnemy(Level world) { super(world);}
    public void moveMob()
    {
        Mob m = move(decide());
        if(m==null) return;
        if(m!=this)
        {
            if(m instanceof Player)
            {
                attack(m);
            }
        }
    }
    Model.Dir decide()
    {
        int dist=world.field[y][x].distance;
        if(dist==-1)
        {
            return Model.Dir.none;
        }
        if(world.field[y+1][x].distance==dist-1)
        {
            return Model.Dir.down;
        }
        else if(world.field[y-1][x].distance==dist-1)
        {
            return Model.Dir.up;
        }
        else if(world.field[y][x-1].distance==dist-1)
        {
            return Model.Dir.left;
        }
        else if(world.field[y][x+1].distance==dist-1)
        {
            return Model.Dir.right;
        }
        return null;
    }
}
