package Cellar.Model.Items.Armors;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class PickleArmor extends Armor {
    public PickleArmor(Level level, int y, int x) {
        super(level, y, x);
        defence=60;
        texture=new Image("resources/items/armors/pickleArmor.png");
    }
}
