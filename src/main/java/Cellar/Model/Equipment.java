package Cellar.Model;

import Cellar.Model.Items.Item;

import java.lang.reflect.Array;

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
        for(int i=0;i<15;i++) items[i]=null;
    }
    public Item[] items= new Item[15];
    public Bonus currentBonus=new Bonus();
    void loadBonuses()
    {
        currentBonus.resetBonuses();
        if(items[0]!=null) items[0].loadBonus(currentBonus);
        if(items[1]!=null) items[1].loadBonus(currentBonus);
        if(items[2]!=null) items[2].loadBonus(currentBonus);
    }
    public void swapSlots(int i,int j)
    {
        if(i<0||j<0||j>14||i>14) return;
        Item a = items[i];
        items[i]=items[j];
        items[j]=a;
        loadBonuses();
    }
    public boolean equip(Item x)
    {
        for(int i=0;i<15;i++)
        {
            if(items[i]==null)
            {
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
