package Cellar.Model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayDeque;

import static Cellar.View.View.*;

import static Cellar.Model.Model.*;

public class UberBrain {


    private Field currentField;

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
                        playerTexture=playerLeft;
                        //todo: moveMob
                        player.x--;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
                case right:
                    if(currentLevel.field[player.y][player.x+1].getType()!= Field.TypeOfField.wall){
                        playerTexture=playerRight;
                        //todo: moveMob
                        player.x++;
                        turn=true;
                    }
                    //direction=Dir.none;
                    break;
            }

            boolean changedLevel=false;
            //nextLevel
            if(currentLevelIndex<maxLevel-1){
                if(player.y==currentLevel.exitY && player.x==currentLevel.exitX){
                    currentLevelIndex++;
                    currentLevel.mobs.remove(player);
                    currentLevel=levels.get(currentLevelIndex);
                    currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
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
                }
            }

            showBackground(gc);
            calculateDistance();
            showMob(player, gc);

        }
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
}
