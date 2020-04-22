package Cellar.Model;

import Cellar.Model.Items.*;
import javafx.util.Pair;

import java.lang.reflect.Constructor;
import java.util.Random;

public class ItemGenerator {
    public static void generateItems(Level level){
        Random rand = new Random();
        int numberOfItems=rand.nextInt(level.maxItems+1-level.minItems)+level.minItems;
        for(int i=0; i<numberOfItems; i++){
            int k=rand.nextInt(level.itemTypes.size());
            placeItem(level, level.itemTypes.get(k));
        }

    }

    public static void placeItem(Level level, Class<? extends Item> itemClass){
        Pair<Integer, Integer> fieldCo=level.randomField();
        int y=fieldCo.getKey();
        int x=fieldCo.getValue();
        try{
           itemClass.getConstructor(Level.class, int.class , int.class).newInstance(level, y, x);
        }catch (Exception e){
            System.out.println("No such item");
        }
    }
}
