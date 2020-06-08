package Cellar.Model.Items.Shields;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class PickleShield extends Shield {
    public PickleShield(Level level, int y, int x) {
        super(level, y, x);
        defence=45;
        block=40;
        texture=new Image("resources/items/shields/pickleShield.png");
    }
}
