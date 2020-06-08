package Cellar.Model.Items.Shields;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class GoldenShield extends Shield {
    public GoldenShield(Level level, int y, int x) {
        super(level, y, x);
        defence=35;
        block=25;
        texture=new Image("resources/items/shields/goldenShield.png");
    }
}
