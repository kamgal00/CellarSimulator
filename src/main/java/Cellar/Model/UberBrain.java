package Cellar.Model;

import Cellar.Model.Mobs.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayDeque;

import static Cellar.View.RightInterface.showRightInterface;
import static Cellar.View.View.*;

import static Cellar.Model.Model.*;

public class UberBrain {

    private Field currentField;

    public static void brainTick(GraphicsContext gc){
        boolean turn=true;
        player.moveMob();
        switch (player.currentAction) {
            case none:
                showBackground(gc);
                currentLevel.mobs.stream().forEach(mob ->{
                    showMob(mob,gc);
                });
                turn=false;
                break;
            case left:
                playerTexture=playerLeft;
                break;
            case right:
                playerTexture=playerRight;
                break;

        }
        if(turn)
        {
            boolean changedLevel=false;
            //nextLevel
            if(currentLevelIndex<maxLevel-1){
                if(player.y==currentLevel.exitY && player.x==currentLevel.exitX){
                    currentLevelIndex++;
                    currentLevel.mobs.remove(player);
                    currentLevel=levels.get(currentLevelIndex);
                    currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
                    player.world=currentLevel;
                    changedLevel=true;
                }
            }

            //prevLevel
            if(currentLevelIndex>0){
                if(player.y==currentLevel.entranceY && player.x==currentLevel.entranceX && !changedLevel){
                    currentLevelIndex--;
                    currentLevel.mobs.remove(player);
                    currentLevel=levels.get(currentLevelIndex);
                    currentLevel.addMob(player, currentLevel.exitY, currentLevel.exitX);
                    player.world=currentLevel;
                }
            }
            discover();
            showBackground(gc);
            calculateDistance();
            showMob(player, gc);
            currentLevel.mobs.stream().forEach(mob -> {
                if(mob instanceof Player) return;
                mob.moveMob();
                showMob(mob,gc);
            });
        }
        showRightInterface();
    }

    static void calculateDistance(){
        currentLevel.cleanDistance();
        currentLevel.field[player.y][player.x].distance=0;
        ArrayDeque<Integer> XS=new ArrayDeque<>();
        ArrayDeque<Integer> YS=new ArrayDeque<>();
        XS.add(player.x);
        YS.add(player.y);
        while (!XS.isEmpty()){
            Integer currX=XS.poll();
            Integer currY=YS.poll();
            //for neighbouring fields
            //upper
            if(currentLevel.field[currY-1][currX].getType()!= Field.TypeOfField.wall && currY-1>=player.y-height/2){
                if(currentLevel.field[currY-1][currX].distance==-1){
                    currentLevel.field[currY-1][currX].distance=currentLevel.field[currY][currX].distance+1;
                    XS.add(currX);
                    YS.add(currY-1);
                }
                else if(currentLevel.field[currY-1][currX].distance>currentLevel.field[currY][currX].distance+1){
                    currentLevel.field[currY-1][currX].distance=currentLevel.field[currY][currX].distance+1;
                }
            }

            //below
            if(currentLevel.field[currY+1][currX].getType()!= Field.TypeOfField.wall && currY+1<=player.y+height/2){
                if(currentLevel.field[currY+1][currX].distance==-1){
                    currentLevel.field[currY+1][currX].distance=currentLevel.field[currY][currX].distance+1;
                    XS.add(currX);
                    YS.add(currY+1);
                }
                else if(currentLevel.field[currY+1][currX].distance>currentLevel.field[currY][currX].distance+1){
                    currentLevel.field[currY+1][currX].distance=currentLevel.field[currY][currX].distance+1;
                }
            }

            //left
            if(currentLevel.field[currY][currX-1].getType()!= Field.TypeOfField.wall && currX-1>=player.x-width/2){
                if(currentLevel.field[currY][currX-1].distance==-1){
                    currentLevel.field[currY][currX-1].distance=currentLevel.field[currY][currX].distance+1;
                    XS.add(currX-1);
                    YS.add(currY);
                }
                else if(currentLevel.field[currY][currX-1].distance>currentLevel.field[currY][currX].distance+1){
                    currentLevel.field[currY][currX-1].distance=currentLevel.field[currY][currX].distance+1;
                }
            }

            //right
            if(currentLevel.field[currY][currX+1].getType()!= Field.TypeOfField.wall && currX+1<=player.x+width/2){
                if(currentLevel.field[currY][currX+1].distance==-1){
                    currentLevel.field[currY][currX+1].distance=currentLevel.field[currY][currX].distance+1;
                    XS.add(currX+1);
                    YS.add(currY);
                }
                else if(currentLevel.field[currY][currX+1].distance>currentLevel.field[currY][currX].distance+1){
                    currentLevel.field[currY][currX+1].distance=currentLevel.field[currY][currX].distance+1;
                }
            }
        }
    }

    static void discover(){
        for(int i=0; i<height; i++){
            int y=player.y+i-height/2;
            for (int j=0; j<width; j++){
                int x=player.x+j-width/2;
                if( !(x<0 || y<0 || x>=levelSize*roomSize || y>=levelSize*roomSize)){
                    if(currentLevel.field[y][x].getType()!= Field.TypeOfField.wall) {
                        currentLevel.field[y][x].setDiscovered(true);
                    }
                }

            }
        }
    }
}
