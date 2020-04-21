package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Model;
import javafx.util.Pair;

public abstract class WalkingEnemy extends Enemy {
    MoveAutomation auto;
    public WalkingEnemy(Level w) {
        super(w);
        auto = new MoveAutomation(this,true) {
            @Override
            public void setActions() {
                addAction(new Sleep(2));
                addAction(new GoTo(false) {
                    @Override
                    public Pair<Integer, Integer> getTarget() {
                        return world.randomField();
                    }
                });
                addAction(new Sleep(3));
            }

            @Override
            public boolean interruptCondition() {
                if(world.field[y][x].distance!=-1) return true;
                return false;
            }
        };
    }
    public void moveMob()
    {
        if(auto.isActive())
        {
            auto.step();
            if(auto.currentState!= MoveAutomation.Status.interrupted) return;
        }
        if(world.field[y][x].distance==-1)
        {
            auto.start();
            if(auto.isActive())
            {
                auto.step();
                if(auto.currentState!= MoveAutomation.Status.interrupted) return;
            }
        }
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
        if(dist>1)
        {
            if(world.field[y+1][x+1].distance==dist-2)
            {
                if(world.field[y+1][x+1].mob==null||world.field[y+1][x+1].mob instanceof Player)
                return Model.Dir.rightDown;
            }
            if(world.field[y+1][x-1].distance==dist-2)
            {
                if(world.field[y+1][x-1].mob==null||world.field[y+1][x-1].mob instanceof Player)
                return Model.Dir.leftDown;
            }
            if(world.field[y-1][x+1].distance==dist-2)
            {
                if(world.field[y-1][x+1].mob==null||world.field[y-1][x+1].mob instanceof Player)
                return Model.Dir.rightUp;
            }
            if(world.field[y-1][x-1].distance==dist-2)
            {
                if(world.field[y-1][x-1].mob==null||world.field[y-1][x-1].mob instanceof Player)
                return Model.Dir.leftUp;
            }
        }
        if(world.field[y+1][x].distance==dist-1)
        {
            if(world.field[y+1][x].mob==null||world.field[y+1][x].mob instanceof Player)
            return Model.Dir.down;
        }
        if(world.field[y-1][x].distance==dist-1)
        {
            if(world.field[y-1][x].mob==null||world.field[y-1][x].mob instanceof Player)
            return Model.Dir.up;
        }
        if(world.field[y][x-1].distance==dist-1)
        {
            if(world.field[y][x-1].mob==null||world.field[y][x-1].mob instanceof Player)
            return Model.Dir.left;
        }
        if(world.field[y][x+1].distance==dist-1)
        {
            if(world.field[y][x+1].mob==null||world.field[y][x+1].mob instanceof Player)
            return Model.Dir.right;
        }
        if(world.field[y+1][x+1].distance==dist)
        {
            if(world.field[y+1][x+1].mob==null||world.field[y+1][x+1].mob instanceof Player)
                return Model.Dir.rightDown;
        }
        if(world.field[y+1][x-1].distance==dist)
        {
            if(world.field[y+1][x-1].mob==null||world.field[y+1][x-1].mob instanceof Player)
                return Model.Dir.leftDown;
        }
        if(world.field[y-1][x+1].distance==dist)
        {
            if(world.field[y-1][x+1].mob==null||world.field[y-1][x+1].mob instanceof Player)
                return Model.Dir.rightUp;
        }
        if(world.field[y-1][x-1].distance==dist)
        {
            if(world.field[y-1][x-1].mob==null||world.field[y-1][x-1].mob instanceof Player)
                return Model.Dir.leftUp;
        }
        return Model.Dir.none;
    }
}
