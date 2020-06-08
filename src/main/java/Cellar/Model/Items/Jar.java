package Cellar.Model.Items;

import Cellar.Model.Equipment;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public class Jar extends Item {
    public Jar(Level level, int y, int x) {
        super(level, y, x);
        texture=new Image("file:resources/items/jar.png");
    }

    @Override
    public void loadBonus(Equipment.Bonus b, int ind) {

    }
}
