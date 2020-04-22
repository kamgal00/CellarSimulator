package Cellar.Model.Items.Armors;

import Cellar.Model.Items.*;
import Cellar.Model.Level;

public class BasicArmor extends Armor {
    public BasicArmor(Level level, int y, int x) {
        super(level, y, x);
        defence=20;
    }
}
