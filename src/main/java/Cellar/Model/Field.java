package Cellar.Model;

import Cellar.Model.Mobs.Mob;

public class Field {
    public int distance;
    public Mob mob;
    TypeOfField typeOfField;
    public boolean discovered=false;

    public enum TypeOfField{
        wall, floor, corridor, entrance, exit
    }

    public Field(TypeOfField type){
        typeOfField=type;
        mob=null;
    }

    public TypeOfField getType(){
        return typeOfField;
    }
    public void setDiscovered(boolean dis){
        discovered=dis;
    }
}
