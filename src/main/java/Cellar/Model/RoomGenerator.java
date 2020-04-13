package Cellar.Model;

import Cellar.Model.Rooms.BasicRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Cellar.Model.Model.*;
public class RoomGenerator {
    static int maxId=1;
    static Random rand=new Random();
    static RoomShapeManager shapeGen;
    public static void loadRoomGenerator()
    {
        shapeGen=new RoomShapeManager();
        shapeGen.add(500,(Room x) -> //Basic rectangle
        {
            x.exitable=true;
            int width = rand.nextInt(roomSize/2-1)+roomSize-roomSize/2;
            int height = rand.nextInt(roomSize/2-1)+roomSize-roomSize/2;
            int cornerY=rand.nextInt(roomSize-1-height);
            int cornerX=rand.nextInt( roomSize-1-width);
            for(int i=0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    x.fields[1+cornerX+i][1+cornerY+j].typeOfField= Field.TypeOfField.floor;
                }
            }
        });
        shapeGen.add(75,(Room x) -> //Do you know who ate all the donuts?
        {
            for(int i=1;i<roomSize-1;i++)
            {
                for(int j=1;j<roomSize-1;j++) x.fields[i][j].typeOfField= Field.TypeOfField.floor;
            }
            x.fields[1][1].typeOfField= Field.TypeOfField.wall;
            x.fields[1][roomSize-2].typeOfField= Field.TypeOfField.wall;
            x.fields[roomSize-2][1].typeOfField= Field.TypeOfField.wall;
            x.fields[roomSize-2][roomSize-2].typeOfField= Field.TypeOfField.wall;
            int width = rand.nextInt(2)+4;
            int height = rand.nextInt(2)+4;
            int cornerX=3+rand.nextInt(roomSize-5-width);
            int cornerY=3+rand.nextInt(roomSize-5-height);
            //System.out.println(width+" "+height+" "+cornerX+" "+cornerY);
            for(int i = 0;i<width;i++)
            {
                for(int j=0;j<height;j++)
                {
                    x.fields[cornerX+i][cornerY+j].typeOfField= Field.TypeOfField.wall;
                }
            }
            x.fields[cornerX][cornerY].typeOfField= Field.TypeOfField.floor;
            x.fields[cornerX+width-1][cornerY].typeOfField= Field.TypeOfField.floor;
            x.fields[cornerX][cornerY+height-1].typeOfField= Field.TypeOfField.floor;
            x.fields[cornerX+width-1][cornerY+height-1].typeOfField= Field.TypeOfField.floor;
        });
        shapeGen.add(75, (Room x)->  // Mini rooms
        {
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    x.fields[i+1][j+1].typeOfField= Field.TypeOfField.floor;
                    x.fields[i+1][j+7].typeOfField= Field.TypeOfField.floor;
                    x.fields[i+7][j+1].typeOfField= Field.TypeOfField.floor;
                    x.fields[i+7][j+7].typeOfField= Field.TypeOfField.floor;
                }

            }
            IntStream.range(2,9).forEach(n->{ x.fields[5][n].typeOfField= Field.TypeOfField.floor;});
            x.fields[4][2].typeOfField= Field.TypeOfField.floor;
            x.fields[6][2].typeOfField= Field.TypeOfField.floor;
            x.fields[4][8].typeOfField= Field.TypeOfField.floor;
            x.fields[6][8].typeOfField= Field.TypeOfField.floor;

            /*for(int i=1;i<roomSize-1;i++)
            {
                for(int j=1;j<roomSize-1;j++) x.fields[i][j].typeOfField= Field.TypeOfField.floor;
            }
            for(int i=1;i<roomSize-1;i++)
            {
                x.fields[4][i].typeOfField= Field.TypeOfField.wall;
                x.fields[6][i].typeOfField= Field.TypeOfField.wall;
                x.fields[i][4].typeOfField= Field.TypeOfField.wall;
                x.fields[i][6].typeOfField= Field.TypeOfField.wall;
            }
            x.fields[4][5].typeOfField= Field.TypeOfField.floor;
            x.fields[6][5].typeOfField= Field.TypeOfField.floor;
            x.fields[5][4].typeOfField= Field.TypeOfField.floor;
            x.fields[5][6].typeOfField= Field.TypeOfField.floor;
            x.fields[4][3].typeOfField= Field.TypeOfField.floor;
            x.fields[6][3].typeOfField= Field.TypeOfField.floor;
            x.fields[4][7].typeOfField= Field.TypeOfField.floor;
            x.fields[6][7].typeOfField= Field.TypeOfField.floor;*/
        });
        shapeGen.add(50,new Maze());
    }
    int roomCounter;
    public RoomGenerator()
    {
        roomCounter=0;
    }
    public Room generateRoom(int id){
        return null;
    }

    public Room nextRoom()
    {
        Room out = new BasicRoom();
        shapeGen.fill(rand,out);
        return out;
    }
}
interface roomShapeGenerator{
    void fillRoom(Room x);
}
class RoomShapeManager{
    int probSum=0;
    int noShapes=0;
    ArrayList<roomShapeGenerator> shapes=new ArrayList<>();
    ArrayList<Integer> probability = new ArrayList<>();
    void fill(Random r,Room x)
    {
        int rand = r.nextInt(probSum);
        int i=0;
        while(i<noShapes&& probability.get(i)<=rand) i++;
        shapes.get(i).fillRoom(x);
    }
    void add(int p, roomShapeGenerator b)
    {
        probSum+=p;
        probability.add(probSum);
        noShapes++;
        shapes.add(b);
    }
}
class Maze implements roomShapeGenerator{
    class Pair{
        public int x,y;
        Pair parent;
        public Pair(int a,int b)
        {
            x=a; y=b;
        }
        public boolean equals(Object o)
        {
            if(o==null) return false;
            if(!(o instanceof Pair)) return false;
            Pair a = (Pair) o;
            if(a.x==x&&a.y==y) return true;
            return false;
        }
        public boolean isClose(Pair c)
        {
            if((c.x-x)*(c.x-x)+(c.y-y)*(c.y-y)==4) return true;
            return false;
        }
    }
    Random rand = new Random();
    public void fillRoom(Room x)
    {
        ArrayList<Pair> notPicked = new ArrayList<>();
        ArrayList<Pair> notDone = new ArrayList<>();
        ArrayList<Pair> done = new ArrayList<>();
        for(int i=1;i<10;i+=2)
        {
            for(int j=1;j<10;j+=2)
            {
                notPicked.add(new Pair(i,j));
            }
        }
        Pair s = notPicked.get(rand.nextInt(notPicked.size()));
        Pair n=null;
        notPicked.remove(s);
        notDone.add(s);
        while (!notDone.isEmpty())
        {
            s = notDone.get(rand.nextInt(notDone.size()));
            n=getChild(s,notPicked);
            if(n==null)
            {
                notDone.remove(s);
                done.add(s);
                continue;
            }
            n.parent=s;
            notPicked.remove(n);
            notDone.add(n);
        }
        done.stream().forEach(p->{
            x.fields[p.x][p.y].typeOfField= Field.TypeOfField.floor;
            if(p.parent!=null)
            {
                x.fields[(p.x+p.parent.x)/2][(p.y+p.parent.y)/2].typeOfField= Field.TypeOfField.floor;
            }
        });
    }
    Pair getChild(Pair s,ArrayList<Pair> notPicked)
    {
        List<Pair> close = notPicked.stream().filter(s::isClose).collect(Collectors.toList());
        if(close.size()==0) return null;
        return close.get(rand.nextInt(close.size()));
    }
}
