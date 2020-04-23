package Cellar.Model.Mobs.Actions;

import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.MoveAutomation;
import Cellar.Model.Mobs.Player;

import static Cellar.Model.Mobs.Mob.actionType.*;

public abstract class ActionType {
    public abstract boolean trigger(Mob s);
}

