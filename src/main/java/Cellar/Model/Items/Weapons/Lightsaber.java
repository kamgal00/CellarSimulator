package Cellar.Model.Items.Weapons;

import Cellar.Model.Items.*;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class Lightsaber extends Weapon {
    public Lightsaber(Level level, int y, int x){
        super(level, y, x);
        attack=12;
        texture=new Image("file:resources/items/weapons/lightsaber.png");
    }
}
