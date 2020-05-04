package Cellar.Model;

import Cellar.Model.Items.Armor;
import Cellar.Model.Items.Item;
import Cellar.Model.Items.Shield;
import Cellar.Model.Items.Weapon;

import static Cellar.Model.Model.*;

public class Equipment {
    public class Bonus {
        public int additionalAttack=0;
        public int additionalDefense=0;
        public int additionalBlockChance=0;
        public void resetBonuses()
        {
            additionalAttack=0;
            additionalDefense=0;
            additionalBlockChance=0;
        }
    }
    public Equipment()
    {
        for(int i=0;i<equipmentSize;i++) items[i]=null;
    }
    public Item[] items= new Item[equipmentSize]; //0--left hand, 1--armor, 2--right hand
    public Bonus currentBonus=new Bonus();
    void loadBonuses()
    {
        currentBonus.resetBonuses();
        if(items[0]!=null) items[0].loadBonus(currentBonus, 0);
        if(items[1]!=null) items[1].loadBonus(currentBonus, 1);
        if(items[2]!=null) items[2].loadBonus(currentBonus, 2);
    }
    public void swapSlots(int i,int j)
    {
        if(i<0||j<0||j>equipmentSize-1||i>equipmentSize-1) return;
        Item a = items[i];
        items[i]=items[j];
        items[j]=a;
        loadBonuses();
    }
    public boolean equip(Item x)
    {
        for(int i=0;i<equipmentSize;i++)
        {
            if(items[i]==null)
            {
                //left hand
                if(i==0 && !(Shield.class.isAssignableFrom(x.getClass()) || Weapon.class.isAssignableFrom(x.getClass())) ){
                    continue;
                }
                //armor
                if(i==1 && !(Armor.class.isAssignableFrom(x.getClass())) ){
                    continue;
                }
                //right hand
                if(i==2 && !(Weapon.class.isAssignableFrom(x.getClass())) ){
                    continue;
                }
                items[i]=x;
                loadBonuses();
                return true;
            }
        }
        return false;
    }
    public Item drop(int index)
    {
        Item out=items[index];
        items[index]=null;
        loadBonuses();
        return out;
    }
}
