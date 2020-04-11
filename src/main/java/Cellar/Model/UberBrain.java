package Cellar.Model;

import javafx.scene.canvas.GraphicsContext;

import static Cellar.View.View.*;

import static Cellar.Model.Model.*;

public class UberBrain {


    public static void brainTick(GraphicsContext gc){
        boolean turn=false;
        if(direction == Model.Dir.none){
            showBackground(gc);
            //todo: show all mobs
            showMob(player, gc);
        }
        else {
            switch (direction){
                case up:
                    if(currentLevel.field[player.y-1][player.x].getType()!= Field.TypeOfField.wall){
                        //todo: moveMob
                        player.y--;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
                case down:
                    if(currentLevel.field[player.y+1][player.x].getType()!= Field.TypeOfField.wall){
                        //todo: moveMob
                        player.y++;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
                case left:
                    if(currentLevel.field[player.y][player.x-1].getType()!= Field.TypeOfField.wall){
                        //todo: moveMob
                        player.x--;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
                case right:
                    if(currentLevel.field[player.y][player.x+1].getType()!= Field.TypeOfField.wall){
                        //todo: moveMob
                        player.x++;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
            }
            showBackground(gc);
            showMob(player, gc);
        }
    }
}
