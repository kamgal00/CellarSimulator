package Cellar.View;

import static Cellar.Model.Model.*;
import static Cellar.View.View.*;

import Cellar.Model.Field;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RightInterface {
    public static Image interfaceBackground=new Image("file:resources/rightInterface.png");
    public static Image minimapBackground=new Image("file:resources/minimap.png");


    public static void showRightInterface(){
        gc.drawImage(interfaceBackground, width*cornerSize, 0, 6*cornerSize, height*cornerSize);

        //show minimap
        gc.drawImage(minimapBackground, width*cornerSize, 0, 6*cornerSize, 6*cornerSize );
        for(int i=0; i<levelSize*roomSize; i++){ //by row
            for (int j=0; j<levelSize*roomSize; j++){ //by column
                if(currentLevel.field[j][i].discovered){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(width*cornerSize+cornerSize/2+3*i, 30+3*j, 3, 3);
                    if(currentLevel.field[j][i].getType()== Field.TypeOfField.entrance){
                        gc.setFill(Color.DARKRED);
                        gc.fillRect(width*cornerSize+cornerSize/2+3*i, 30+3*j, 3, 3);
                    }
                    if(currentLevel.field[j][i].getType()== Field.TypeOfField.exit){
                        gc.setFill(Color.DARKGREEN);
                        gc.fillRect(width*cornerSize+cornerSize/2+3*i, 30+3*j, 3, 3);
                    }
                    if(j==player.y && i==player.x){
                        gc.setFill(Color.LIGHTBLUE);
                        gc.fillRect(width*cornerSize+cornerSize/2+3*i, 30+3*j, 3, 3);
                    }
                }
            }
        }

        //show number of level
        gc.drawImage(entranceTexture,width*cornerSize+cornerSize/2, 10+3*roomSize*levelSize+cornerSize, cornerSize/2, cornerSize/2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/2));
        gc.fillText(currentLevelIndex+1+"", width*cornerSize+3*cornerSize/2-5, 10+3*roomSize*levelSize+3*cornerSize/2-5);

        //show HP bar
        gc.setFill(Color.rgb(90, 0,0));
        gc.fillRect(width*cornerSize+cornerSize/2-2, 45+3*roomSize*levelSize+cornerSize-2, cornerSize*5+4, cornerSize/4+4);
        gc.setFill(Color.DARKRED);
        gc.fillRect(width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+cornerSize, cornerSize*5, cornerSize/4);

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(width*cornerSize+cornerSize/2-2, 45+3*roomSize*levelSize+cornerSize-2, cornerSize*5*player.hp/player.maxHp+4, cornerSize/4+4);
        gc.setFill(Color.GREEN);
        gc.fillRect(width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+cornerSize, cornerSize*5*player.hp/player.maxHp, cornerSize/4);

        //show stats
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("HP "+player.hp+"/"+player.maxHp, width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+4*cornerSize/2-5);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("Lvl "+player.level + " exp: "+player.exp+"/"+player.level*player.level*10, width*cornerSize+6*cornerSize/2, 45+3*roomSize*levelSize+4*cornerSize/2-5);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("ATK "+player.attack, width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+5*cornerSize/2-5);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("DEF "+player.defense, width*cornerSize+6*cornerSize/2, 45+3*roomSize*levelSize+5*cornerSize/2-5);
    }
}
