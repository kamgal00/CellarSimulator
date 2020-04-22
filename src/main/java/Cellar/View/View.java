package Cellar.View;

import Cellar.Model.Field;
import Cellar.Model.Items.Armor;
import Cellar.Model.Items.Item;
import Cellar.Model.Items.Shield;
import Cellar.Model.Items.Weapon;
import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static Cellar.Model.Model.*;


public class View {

    static Image floorTexture=new Image("file:resources/floor.png");
    static Image wallTexture=new Image("file:resources/bricks.png");
    public static Image exitTexture=new Image("file:resources/exit.png");
    public static Image entranceTexture=new Image("file:resources/entrance.png");

    public static void showBackground(GraphicsContext gc){
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, width * cornerSize, height * cornerSize);
        for(int i=0; i<height; i++){
            int y=player.y+i-height/2;
            for (int j=0; j<width; j++){
                int x=player.x+j-width/2;
                if(x<0 || y<0 || x>=levelSize*roomSize || y>=levelSize*roomSize){
                    showField(new Field(Field.TypeOfField.wall), j, i, gc);
                }
                else {
                    showField(currentLevel.field[y][x], j, i, gc);
                }
            }
        }
    }

    static void showField(Field field, int x, int y, GraphicsContext gc){
        switch (field.getType()){
            case wall:
                gc.drawImage(wallTexture, x*cornerSize, y*cornerSize, cornerSize, cornerSize);
                break;
            case floor:
            case corridor:
                gc.drawImage(floorTexture, x*cornerSize, y*cornerSize, cornerSize, cornerSize);
                break;
            case exit:
                gc.drawImage(exitTexture, x*cornerSize, y*cornerSize, cornerSize, cornerSize);
                break;
            case entrance:
                gc.drawImage(floorTexture, x*cornerSize, y*cornerSize, cornerSize, cornerSize);
                gc.drawImage(entranceTexture, x*cornerSize, y*cornerSize, cornerSize, cornerSize);
                break;
        }
        if(field.mob==null && !field.items.isEmpty()){
            showItem(field.items.get(field.items.size()-1), y, x);
        }
    }

    public static void showItem(Item item, int y, int x){
        if(Weapon.class.isAssignableFrom(item.getClass())){
            gc.setFill(Color.DARKBLUE);
        }
        else if(Armor.class.isAssignableFrom(item.getClass())){
            gc.setFill(Color.DARKGREEN);
        }
        else if(Shield.class.isAssignableFrom(item.getClass())){
            gc.setFill(Color.DARKGOLDENROD);
        }
        else {
            gc.setFill(Color.WHITE);
        }
        gc.fillOval(x*cornerSize, y*cornerSize, cornerSize, cornerSize);
    }

    public static void showMob(Mob mob){
        if(mob.getClass().isAssignableFrom(Player.class)){
            gc.drawImage(mob.currIm, (player.x-mob.x+width/2)*cornerSize, (player.y-mob.y+height/2)*cornerSize, cornerSize, cornerSize);
        }
        else {
            if(!mob.isVisible()) return;
            gc.drawImage(mob.currIm,  (mob.x-player.x + width / 2) * cornerSize, (mob.y - player.y + height / 2) * cornerSize, cornerSize, cornerSize);
        }
    }
}
