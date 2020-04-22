package Cellar.Model.Items;

import Cellar.Model.Equipment;
import Cellar.Model.Level;

public abstract class Shield extends Item {
    public int defence;
    public int block;

    public Shield(Level level, int y, int x) {
        super(level, y, x);
    }

    @Override
    public void loadBonus(Equipment.Bonus b) {
        b.additionalDefense+=defence;
        b.additionalBlockChance+=block;
    }
}
