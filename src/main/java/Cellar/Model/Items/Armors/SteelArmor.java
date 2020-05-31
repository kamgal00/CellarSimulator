package Cellar.Model.Items.Armors;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class SteelArmor extends Armor {
    public SteelArmor(Level level, int y, int x) {
        super(level, y, x);
        defence=20;
        texture=new Image("file:resources/items/armors/steelArmor.png");
    }
}
