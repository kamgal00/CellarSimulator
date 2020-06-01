package Cellar.Model;

import Cellar.Model.Items.*;
import Cellar.Model.Mobs.PickleBoss;

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
        if(i==0 && items[j]!=null && !(Shield.class.isAssignableFrom(items[j].getClass()) || Weapon.class.isAssignableFrom(items[j].getClass()))) return;
        if(i==1 && items[j]!=null && !(Armor.class.isAssignableFrom(items[j].getClass()))) return;
        if(i==2 && items[j]!=null && !(Weapon.class.isAssignableFrom(items[j].getClass()))) return;
        if(j==0 && items[i]!=null && !(Shield.class.isAssignableFrom(items[i].getClass()) || Weapon.class.isAssignableFrom(items[i].getClass()))) return;
        if(j==1 && items[i]!=null && !(Armor.class.isAssignableFrom(items[i].getClass()))) return;
        if(j==2 && items[i]!=null && !(Weapon.class.isAssignableFrom(items[i].getClass()))) return;
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
                if(PICKLE && Jar.class.isAssignableFrom(x.getClass())) {
                    PICKLE=false;
                    currentLevel.addMob(new PickleBoss(currentLevel),player.y+1,player.x);
                    for(int xd=0;xd<3;xd++) for(int yd=0;yd<3;yd++) {
                        if(xd==1 && yd==1) continue;
                        currentLevel.field[currentLevel.entranceY-1+yd][currentLevel.entranceX-1+xd].typeOfField= Field.TypeOfField.wall;
                    }
                }
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
