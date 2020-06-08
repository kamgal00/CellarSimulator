package Cellar.Model.Items.Shields;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class SteelShield extends Shield {
    public SteelShield(Level level, int y, int x) {
        super(level, y, x);
        defence=15;
        block=18;
        texture=new Image("file:resources/items/shields/steelShield.png");
    }
}
