package Cellar.View;

import static Cellar.Model.Model.*;
import static Cellar.View.View.*;

import Cellar.Model.Field;
import Cellar.Model.Items.Item;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RightInterface {
    public static Image interfaceBackground=new Image("file:resources/interface/rightInterface.png");
    public static Image minimapBackground=new Image("file:resources/interface/minimap.png");
    public static Image eqSlot=new Image("file:resources/interface/eqslot.png");
    public static Image eqSelected = new Image("file:resources/interface/selectedeqslot.png");
    public static Image gameOverTexture=new Image("file:resources/interface/gameOver.png");

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
                    if(!currentLevel.field[j][i].items.isEmpty()){
                        gc.setFill(Color.DARKORANGE);
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

        if(player.hp>0){
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(width*cornerSize+cornerSize/2-2, 45+3*roomSize*levelSize+cornerSize-2, cornerSize*5*player.hp/player.maxHp+4, cornerSize/4+4);
            gc.setFill(Color.GREEN);
            gc.fillRect(width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+cornerSize, cornerSize*5*player.hp/player.maxHp, cornerSize/4);
        }

        //show stats
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("HP "+player.hp+"/"+player.maxHp, width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+4*cornerSize/2-5);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("Lvl "+player.level + " exp: "+player.exp+"/"+player.level*player.level*10, width*cornerSize+6*cornerSize/2, 45+3*roomSize*levelSize+4*cornerSize/2-5);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("ATK "+player.getAttack(), width*cornerSize+cornerSize/2, 45+3*roomSize*levelSize+5*cornerSize/2-5);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", cornerSize/3));
        gc.fillText("DEF "+player.getDefense(), width*cornerSize+6*cornerSize/2, 45+3*roomSize*levelSize+5*cornerSize/2-5);

        /*//show eq slots
        //equipped
        gc.drawImage(eqSlot, (width+1)*cornerSize+24, 425, cornerSize, cornerSize);
        gc.drawImage(eqSlot, (width+2)*cornerSize+24, 425, cornerSize, cornerSize);
        gc.drawImage(eqSlot, (width+3)*cornerSize+24, 425, cornerSize, cornerSize);

        //in inventory
        for(int i=0; i<=4; i++){
            for(int j=0; j<=2; j++){
                gc.drawImage(eqSlot, (width+i)*cornerSize+24, 500+j*cornerSize, cornerSize, cornerSize);
            }
        }

        //show items in eq
        Item[] items=player.eq.items;
        for(int i=0; i<equipmentSize; i++){
            if(items[i]!=null){
                if(i<=2){showItemInHand(i, items[i]);}
                else {showItemInEq(i, items[i]);}
            }
        }*/

        //show eq slots
        eqIn.draw();
        //show GAME OVER
        if(endGame){
            gc.drawImage(gameOverTexture, 0, 0, width*cornerSize, height*cornerSize);
        }
    }

    public static void showItemInEq(int s, Item item){
        s=s-3;
        gc.drawImage(item.texture, (width+s%5)*cornerSize+24+2, 500+3+(s/5)*cornerSize, cornerSize-6, cornerSize-6);

    }
    public static void showItemInHand(int s, Item item){
        gc.drawImage(item.texture, (width+s+1)*cornerSize+24+2, 425+3, cornerSize-6, cornerSize-6);
    }
}
