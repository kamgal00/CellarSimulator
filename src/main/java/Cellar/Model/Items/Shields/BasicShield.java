package Cellar.Model.Items.Shields;

import Cellar.Model.Items.*;
import Cellar.Model.Level;

public class BasicShield extends Shield {
    public BasicShield(Level level, int y, int x) {
        super(level, y, x);
        defence=10;
        block=10;
    }
}
