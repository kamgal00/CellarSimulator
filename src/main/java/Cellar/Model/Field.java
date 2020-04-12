package Cellar.Model;

public class Field {
    public int distance;

    TypeOfField typeOfField;
    public boolean discovered=false;

    public enum TypeOfField{
        wall, floor, corridor, entrance, exit
    }

    public Field(TypeOfField type){
        typeOfField=type;
    }

    public TypeOfField getType(){
        return typeOfField;
    }
    public void setDiscovered(boolean dis){
        discovered=dis;
    }
}
