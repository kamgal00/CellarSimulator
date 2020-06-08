package Cellar.Model.Items.Shields;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class WoodenShield extends Shield {
    public WoodenShield(Level level, int y, int x) {
        super(level, y, x);
        defence=8;
        block=10;
        texture=new Image("resources/items/shields/woodenShield.png");
    }
}
