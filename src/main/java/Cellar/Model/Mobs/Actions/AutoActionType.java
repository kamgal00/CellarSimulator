package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.MoveAutomation;

public class AutoActionType extends ActionType{
    @Override
    public boolean trigger(Mob s) {
        if(!s.hasAuto) return true;
        s.auto.start();
        if(s.auto.isActive())
        {
            s.auto.step();
            if(s.auto.currentState== MoveAutomation.Status.interrupted) return false;
        }
        return true;
    }
}
