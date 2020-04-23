package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;

import static Cellar.Model.Mobs.Mob.actionType.wait;

public class WaitActionType extends ActionType{
    public boolean trigger(Mob s)
    {
        s.currentAction=wait;
        return true;
    }
}
