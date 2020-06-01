package Cellar.Model;

import Cellar.Model.Items.Item;
import Cellar.Model.Mobs.Mob;

import java.util.ArrayList;

public class Field {
    public int distance;
    public Mob mob;
    public TypeOfField typeOfField;
    public boolean discovered=false;
    public ArrayList<Item> items=new ArrayList<>();

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
    public boolean hasItem(){
        return !items.isEmpty();
    }

    public void putItem(Item item){
        items.add(item);
    }

    public Item getItem(){
        if(!hasItem()){return null;}
        Item i=items.get(items.size()-1);
        items.remove(i);
        return i;
    }
}
