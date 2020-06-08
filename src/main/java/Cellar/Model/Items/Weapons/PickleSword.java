package Cellar.Model.Items.Weapons;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class PickleSword extends Weapon {
    public PickleSword(Level level, int y, int x){
        super(level, y, x);
        attack=40;
        texture=new Image("resources/items/weapons/pickleSword.png");
    }
}
