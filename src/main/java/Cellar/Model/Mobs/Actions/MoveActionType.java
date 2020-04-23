package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;

import static Cellar.Model.Mobs.Mob.actionType.move;

public class MoveActionType extends ActionType{
    public Mob.Directions direction;
    public MoveActionType(Mob.Directions dir) {direction=dir;}
    @Override
    public boolean trigger(Mob s) {
        s.currentAction=move;
        return direction.pushMob(s);
    }
}
