package Cellar.Model;

public class Field {
    public boolean wall=false;
    //coordinates of field in room
    public int x;
    public int y;
    public enum typeOfField{
        wall, floor, corridor
    }

    public Field(int x, int y){
        this.x=x;
        this.y=y;
    }
}
