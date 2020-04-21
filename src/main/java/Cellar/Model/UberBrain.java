package Cellar.Model;

import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

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
                    showMob(mob);
                });
                turn=false;
                break;
        }
        if(turn) {
            if(player.hp == 0) System.out.println("GAME OVER");
            boolean changedLevel = false;
            //nextLevel
            if (currentLevelIndex < maxLevel - 1) {
                if (player.y == currentLevel.exitY && player.x == currentLevel.exitX) {
                    currentLevelIndex++;
                    currentLevel.mobs.remove(player);
                    currentLevel = levels.get(currentLevelIndex);
                    currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
                    player.world = currentLevel;
                    changedLevel = true;
                }
            }

            //prevLevel
            if (currentLevelIndex > 0) {
                if (player.y == currentLevel.entranceY && player.x == currentLevel.entranceX && !changedLevel) {
                    currentLevelIndex--;
                    currentLevel.mobs.remove(player);
                    currentLevel = levels.get(currentLevelIndex);
                    currentLevel.addMob(player, currentLevel.exitY, currentLevel.exitX);
                    player.world = currentLevel;
                }
            }
            generateEnemy();
            discover();
            showBackground(gc);
            calculateDistance();
            showMob(player);
            /*ArrayList<Mob> dead = new ArrayList<Mob>();
            for(int i = 0; i < currentLevel.mobs.size(); i++)
            {
                if(currentLevel.mobs.get(i).hp <= 0)
                {
                    //gaining experience
                    player.exp+=currentLevel.mobs.get(i).expForKill;
                    while(player.exp>=player.level*player.level*10){
                        player.exp-=player.level*player.level*10;
                        player.level++;
                        player.maxHp*=11;
                        player.maxHp/=10;
                        player.hp=player.maxHp;
                        player.attack*=11;
                        player.attack/=10;
                        player.defense*=6;
                        player.defense/=5;
                    }

                    dead.add(currentLevel.mobs.get(i));
                    currentLevel.field[dead.get(dead.size() - 1).y][dead.get(dead.size() - 1).x].mob=null;
                }
            }
            currentLevel.mobs.removeAll(dead);*/
            currentLevel.mobs.stream().forEach(mob -> {
                if (mob instanceof Player) return;
                mob.moveMob();
                showMob(mob);
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

    public static void generateEnemy(){
        Random rand = new Random();
        try {
            //if not enough enemies
            if(currentLevel.mobs.size()<currentLevel.minEnemies+1){
                int eId=rand.nextInt(currentLevel.mobTypes.size());
                placeEnemy(currentLevel.mobTypes.get(eId).getConstructor(Level.class).newInstance(currentLevel));
            }
            else if(currentLevel.mobs.size()<=currentLevel.maxEnemies){
                if(rand.nextInt(1000)<100/currentLevel.mobs.size()){
                    int eId=rand.nextInt(currentLevel.mobTypes.size());
                    placeEnemy(currentLevel.mobTypes.get(eId).getConstructor(Level.class).newInstance(currentLevel));
                }
            }
        }catch (Exception e){
            System.out.println("No such mob");
        }
    }

    public static void placeEnemy(Mob mob){
        Random rand = new Random();
        while(true){
            int x=rand.nextInt(levelSize*roomSize);
            int y=rand.nextInt(levelSize*roomSize);
            if( (x>player.x+width/2 || x<player.x-width/2) && (y>player.y+height/2 || y<player.y-height/2)){
                if(currentLevel.field[y][x].getType()!= Field.TypeOfField.wall){
                    boolean placeable=true;
                    for(Mob fieldMob: currentLevel.mobs){
                        if(fieldMob.y==y && fieldMob.x==x){
                            placeable=false;
                            break;
                        }
                    }
                    if(placeable){
                        currentLevel.addMob(mob, y, x);
                        return;
                    }
                }
            }
        }
    }
}
