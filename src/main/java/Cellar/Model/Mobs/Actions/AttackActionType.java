package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;

public class AttackActionType extends ActionType{
    Mob attackedMob;
    public AttackActionType(Mob attacked) {attackedMob=attacked;}
    @Override
    public boolean trigger(Mob s) {
        s.attack(attackedMob);
        s.currentAction= Mob.actionType.attack;
        return true;
    }
}
