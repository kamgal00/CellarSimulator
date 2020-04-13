package Cellar.Model.Mobs;
import Cellar.Controller.Controller;
import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Model;
import Cellar.Model.Model.*;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.Optional;

import static Cellar.Model.Model.*;

public class Player extends Mob {
    MoveAutomation auto;
    Pair<Integer,Integer> mouse;
    public Player(Level world)
    {
        super(world);
        auto=new MoveAutomation(this,true) {
            @Override
            public void setActions() {
                addAction(new Sleep(3));
            }
            @Override
            public boolean interruptCondition() {
                return false;
            }
        };
    }
    @Override
    public void setParams() {
        maxHp=100;
        hp=100;
        attack=15;
        defense=5;
        rightIm=new Image("file:resources/manright.gif");
        leftIm=new Image("file:resources/manleft.gif");
    }
    @Override
    public void moveMob() {
        if(auto.isActive())
        {
            auto.step();
            if(auto.currentState!= MoveAutomation.Status.interrupted) return;
        }
        mouse=Controller.action.getMouse();
        if(mouse!=null)
        {
            System.out.println("Wcisnięto pole "+mouse.getValue()+" "+mouse.getKey());
            auto.start();
            if(auto.isActive())
            {
                auto.step();
                if(auto.currentState!= MoveAutomation.Status.interrupted) return;
            }
        }
        Mob en = move(direction);
        if(en!=this)
        {
            if(en==null) return;
            if(en instanceof Enemy)
            {
                attack(en);
            }
        }
    }
}
