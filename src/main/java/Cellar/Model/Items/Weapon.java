package Cellar.Model.Items;

import Cellar.Model.Level;

public abstract class Weapon extends Item {
    public int attack;

    public Weapon(Level level, int y, int x) {
        super(level, y, x);
    }
}
