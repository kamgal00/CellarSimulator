package Cellar.Model.Items.Weapons;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class SteelSword extends Weapon {
    public SteelSword(Level level, int y, int x){
        super(level, y, x);
        attack=18;
        texture=new Image("file:resources/items/weapons/steelSword.png");
    }
}
