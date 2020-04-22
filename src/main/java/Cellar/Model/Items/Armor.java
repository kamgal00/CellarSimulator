package Cellar.Model.Items;

import Cellar.Model.Equipment;
import Cellar.Model.Level;

public abstract class Armor extends Item {
    public int defence;

    public Armor(Level level, int y, int x) {
        super(level, y, x);
    }

    @Override
    public void loadBonus(Equipment.Bonus b) {
        b.additionalDefense+=defence;
    }
}
