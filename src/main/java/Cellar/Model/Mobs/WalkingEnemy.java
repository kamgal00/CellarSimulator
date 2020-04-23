package Cellar.Model.Mobs;

import Cellar.Model.Level;
import Cellar.Model.Mobs.Actions.*;
import Cellar.Model.Model;
import javafx.util.Pair;

import static Cellar.Model.Mobs.Mob.Directions.*;

public abstract class WalkingEnemy extends Enemy {
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
        hasAuto=true;
    }

    @Override
    ActionType getAction() {
        if(world.field[y][x].distance==-1) return new AutoActionType();
        if(nearestFields().anyMatch(x->x.mob instanceof Player)) return new AttackActionType(Model.player);
        return new MoveActionType(decide());
    }

    /*public void moveMob()
        {
            /*if(auto.isActive())
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
        }*/
    Directions decide()
    {
        int dist=world.field[y][x].distance;
        if(dist==-1)
        {
            return none;
        }
        if(dist>1)
        {
            if(world.field[y+1][x+1].distance==dist-2)
            {
                if(world.field[y+1][x+1].mob==null||world.field[y+1][x+1].mob instanceof Player)
                return rightDown;
            }
            if(world.field[y+1][x-1].distance==dist-2)
            {
                if(world.field[y+1][x-1].mob==null||world.field[y+1][x-1].mob instanceof Player)
                return leftDown;
            }
            if(world.field[y-1][x+1].distance==dist-2)
            {
                if(world.field[y-1][x+1].mob==null||world.field[y-1][x+1].mob instanceof Player)
                return rightUp;
            }
            if(world.field[y-1][x-1].distance==dist-2)
            {
                if(world.field[y-1][x-1].mob==null||world.field[y-1][x-1].mob instanceof Player)
                return leftUp;
            }
        }
        if(world.field[y+1][x].distance==dist-1)
        {
            if(world.field[y+1][x].mob==null||world.field[y+1][x].mob instanceof Player)
            return down;
        }
        if(world.field[y-1][x].distance==dist-1)
        {
            if(world.field[y-1][x].mob==null||world.field[y-1][x].mob instanceof Player)
            return up;
        }
        if(world.field[y][x-1].distance==dist-1)
        {
            if(world.field[y][x-1].mob==null||world.field[y][x-1].mob instanceof Player)
            return left;
        }
        if(world.field[y][x+1].distance==dist-1)
        {
            if(world.field[y][x+1].mob==null||world.field[y][x+1].mob instanceof Player)
            return right;
        }
        if(world.field[y+1][x+1].distance==dist)
        {
            if(world.field[y+1][x+1].mob==null||world.field[y+1][x+1].mob instanceof Player)
                return rightDown;
        }
        if(world.field[y+1][x-1].distance==dist)
        {
            if(world.field[y+1][x-1].mob==null||world.field[y+1][x-1].mob instanceof Player)
                return leftDown;
        }
        if(world.field[y-1][x+1].distance==dist)
        {
            if(world.field[y-1][x+1].mob==null||world.field[y-1][x+1].mob instanceof Player)
                return rightUp;
        }
        if(world.field[y-1][x-1].distance==dist)
        {
            if(world.field[y-1][x-1].mob==null||world.field[y-1][x-1].mob instanceof Player)
                return leftUp;
        }
        return none;
    }
}
