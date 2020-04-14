package Cellar.Model.Mobs;
import Cellar.Controller.Controller;
import Cellar.Model.Level;
import Cellar.Model.Model.*;
import Cellar.Model.PathFinder;
import javafx.scene.image.Image;
import javafx.util.Pair;

import static Cellar.Model.Model.*;

public class Player extends Mob {
    MoveAutomation auto;
    Pair<Integer,Integer> mouse;
    public int level;
    public int exp;
    public Player(Level w)
    {
        super(w);
        auto=new MoveAutomation(this,true) {
            @Override
            public void setActions() {
                addAction(new GoTo() {
                    @Override
                    public Pair<Integer, Integer> getTarget() {
                        Pair<Integer,Integer> target=new Pair<>(y+mouse.getKey()-7,x+mouse.getValue()-7);
                        //System.out.println("próbuje znaleźć cel");
                        if(world.field[target.getKey()][target.getValue()].distance==-1) return null;
                        return target;
                    }
                });
            }
            @Override
            public boolean interruptCondition()
            {
                if(direction!=Dir.none) return true;
                return false;
            }
        };
    }
    @Override
    public void setParams() {
        level=1;
        exp=0;
        maxHp=100;
        hp=100;
        attack=15;
        defense=5;
        blockChance=15;
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
            //System.out.println("Wcisnięto pole "+mouse.getValue()+" "+mouse.getKey());
            if(mouse.getKey()>=6&&mouse.getKey()<=8&&mouse.getValue()>=6&&mouse.getValue()<=8)
            {
                Dir direct=null;
                switch (mouse.getKey())
                {
                    case 6:
                        switch (mouse.getValue())
                        {
                            case 6:
                                direct=Dir.leftUp;
                                break;
                            case 7:
                                direct=Dir.up;
                                break;
                            case 8:
                                direct=Dir.rightUp;
                                break;
                        }
                        break;
                    case 7:
                        switch (mouse.getValue())
                        {
                            case 6:
                                direct=Dir.left;
                                break;
                            case 7:
                                currentAction=actionType.wait;
                                return;
                            case 8:
                                direct=Dir.right;
                                break;
                        }
                        break;
                    case 8:
                        switch (mouse.getValue())
                        {
                            case 6:
                                direct=Dir.leftDown;
                                break;
                            case 7:
                                direct=Dir.down;
                                break;
                            case 8:
                                direct=Dir.rightDown;
                                break;
                        }
                        break;
                }
                Mob en = move(direct);
                if(en!=this)
                {
                    if(en==null) return;
                    if(en instanceof Enemy)
                    {
                        attack(en);
                    }
                }
                return;
            }
            else
            {
                auto.start();
                if(auto.isActive())
                {
                    auto.step();
                    if(auto.currentState!= MoveAutomation.Status.interrupted) return;
                }
                /*Pair<Integer,Integer> xd=new Pair<>(y+mouse.getKey()-7,x+mouse.getValue()-7);
                System.out.println(xd);
                System.out.println(PathFinder.findPath(world,new Pair<>(y,x),xd));*/
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
