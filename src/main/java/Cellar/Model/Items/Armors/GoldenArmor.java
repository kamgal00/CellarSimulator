package Cellar.Model.Items.Armors;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class GoldenArmor extends Armor {
    public GoldenArmor(Level level, int y, int x) {
        super(level, y, x);
        defence=40;
        texture=new Image("file:resources/items/armors/goldenArmor.png");
    }
}
