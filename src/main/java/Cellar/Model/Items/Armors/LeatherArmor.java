package Cellar.Model.Items.Armors;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class LeatherArmor extends Armor {
    public LeatherArmor(Level level, int y, int x) {
        super(level, y, x);
        defence=10;
        texture=new Image("file:resources/items/armors/leatherArmor.png");
    }
}
