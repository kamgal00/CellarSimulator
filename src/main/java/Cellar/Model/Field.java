package Cellar.Model;

public class Field {

    TypeOfField typeOfField;

    public enum TypeOfField{
        wall, floor, corridor, entrance, exit
    }

    public Field(TypeOfField type){
        typeOfField=type;
    }

    public TypeOfField getType(){
        return typeOfField;
    }
}
