package Cellar.Model.Items.Weapons;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class BasicWeapon extends Weapon {
    public BasicWeapon(Level level, int y, int x){
        super(level, y, x);
        attack=10;
        texture=new Image("file:resources/items/weapons/sword.png");
    }
}
