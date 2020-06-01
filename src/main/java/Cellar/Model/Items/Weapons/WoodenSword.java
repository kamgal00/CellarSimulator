package Cellar.Model.Items.Weapons;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class WoodenSword extends Weapon {
    public WoodenSword(Level level, int y, int x){
        super(level, y, x);
        attack=10;
        texture=new Image("file:resources/items/weapons/woodenSword.png");
    }
}
