package Cellar.Model.Items;

import Cellar.Model.Equipment;
import Cellar.Model.Level;

public abstract class Weapon extends Item {
    public int attack;

    public Weapon(Level level, int y, int x) {
        super(level, y, x);
    }

    @Override
    public void loadBonus(Equipment.Bonus b, int ind) {
        if(ind==0){
            b.additionalAttack+=(attack/2);
            return;
        }
        b.additionalAttack+=attack;
    }
}
