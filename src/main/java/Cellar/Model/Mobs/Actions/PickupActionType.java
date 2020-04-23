package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.Player;

import static Cellar.Model.Mobs.Mob.actionType.pickup;
import static Cellar.Model.Mobs.Mob.actionType.wait;

public class PickupActionType extends ActionType{
    boolean breakCondition;
    public PickupActionType(boolean br) {breakCondition=br;}
    @Override
    public boolean trigger(Mob s){
        if(s instanceof Player)
        {
            s.currentAction=pickup;
            return ((Player) s).pickup(breakCondition);
        }
        s.currentAction=wait;
        return true;
    }
}
