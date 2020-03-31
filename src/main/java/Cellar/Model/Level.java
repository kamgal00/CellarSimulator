package Cellar.Model;

import static Cellar.Model.Model.roomSize;

public class Level {
    public Field[][] field;
    public int width;
    public int height;

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
                        field[i*roomSize+n][j*roomSize+m]=rooms[i][j].fields[n][m];
                    }
                }
            }
        }
    }
}
