package Cellar.Model.Items;

import Cellar.Model.Equipment;
import Cellar.Model.Level;
import javafx.scene.image.Image;

public abstract class Item {
    public Image texture;

    public Item(Level level, int y, int x){
        level.field[y][x].items.add(this);
    }

    public abstract void loadBonus(Equipment.Bonus b, int ind);
}
