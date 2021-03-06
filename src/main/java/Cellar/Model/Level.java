package Cellar.Model;

import Cellar.Model.Items.Item;
import Cellar.Model.Mobs.Mob;
import Cellar.Model.Mobs.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static Cellar.Model.Model.levelSize;
import static Cellar.Model.Model.roomSize;

public class Level {
    public Field[][] field;
    public int width;
    public int height;
    public int playerX;
    public int playerY;
    public int entranceX;
    public int entranceY;
    public int exitX;
    public int exitY;
    public ArrayList<Mob> mobs=new ArrayList<>();
    public ArrayList<Class<? extends Item>> itemTypes=new ArrayList<>();
    public ArrayList<Class<? extends Mob>> mobTypes=new ArrayList<>();
    public int minEnemies=3;
    public int maxEnemies=10;
    public int minItems=3;
    public int maxItems=5;

    public Mob nextMob()
    {
        Mob out = mobs.remove(0);
        mobs.add(out);
        return out;
    }
    //width -- number of rooms in row
    //height -- number of rooms in column
    public Level(int width, int height){
        this.width=width;
        this.height=height;
        //field[n][m] -- n-th row, m-th column
        field=new Field[height*roomSize][width*roomSize];
    }

    public void fuse(Room[][] rooms){
        //iterating by room
        for(int i=0; i<height; i++){ //by row
            for (int j=0; j<width; j++){ //by column

                //iterating by field
                for(int n=0; n<roomSize; n++){ //by row
                    for (int m=0; m<roomSize; m++){ //by column
                        if(rooms[i][j].fields[n][m]!=null){
                            field[i*roomSize+n][j*roomSize+m]=rooms[i][j].fields[n][m];
                        }
                        else{
                            field[i*roomSize+n][j*roomSize+m]=new Field(Field.TypeOfField.wall);
                        }
                    }
                }
            }
        }
    }

    public void addMob(Mob mob, int y, int x){
        mob.y=y;
        mob.x=x;

        mobs.add(mob);
        field[mob.y][mob.x].mob=mob;
        System.out.println("Generated "+mob.getClass().getSimpleName() + " at x="+ x+ ", y="+y);
    }

    public void cleanDistance(){
        for(int i=0; i<levelSize*roomSize; i++){
            for(int j=0; j<levelSize*roomSize; j++){
                field[i][j].distance=-1;
            }
        }
    }

    public Pair<Integer, Integer> randomField(){

        Random rand = new Random();
        int y=0, x=0;
        while(field[y][x].getType()!= Field.TypeOfField.floor){
            y=rand.nextInt(roomSize*levelSize);
            x=rand.nextInt(roomSize*levelSize);
        }

        return new Pair<>(y, x);
    }
}
