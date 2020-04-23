package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;

import static Cellar.Model.Mobs.Mob.actionType.none;

public class NoneActionType extends ActionType{
    public boolean trigger(Mob s)
    {
        s.currentAction=none;
        return false;
    }
}
