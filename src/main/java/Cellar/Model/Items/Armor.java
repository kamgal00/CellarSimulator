package Cellar.Model.Items;

import Cellar.Model.Level;

public abstract class Armor extends Item {
    public int defence;

    public Armor(Level level, int y, int x) {
        super(level, y, x);
    }
}
