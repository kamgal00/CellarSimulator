package Cellar.Model.Mobs;
import Cellar.Controller.Controller;
import Cellar.Model.Equipment;
import Cellar.Model.Items.Item;
import Cellar.Model.Level;
import Cellar.Model.Model.*;
import Cellar.Model.PathFinder;
import javafx.scene.image.Image;
import javafx.util.Pair;

import static Cellar.Model.Model.*;

public class Player extends Mob {
    MoveAutomation auto;
    Pair<Integer,Integer> mouse;
    Equipment eq;
    public int level;
    public int exp;
    boolean isEnemyNearby;
    boolean surprise;
    void gainExp(int expp)
    {
        exp+=expp;
        while(exp>=level*level*10){
            exp-=level*level*10;
            level++;
            maxHp*=11;
            maxHp/=10;
            hp=player.maxHp;
            attack*=11;
            attack/=10;
            defense*=6;
            defense/=5;
        }
    }
    void findEnemy()
    {
        for(int j=Math.max(y-7,0);j<Math.min(y+8,levelSize*roomSize);j++)
        {
            for(int i=Math.max(x-7,0);i<Math.min(x+8,levelSize*roomSize);i++)
            {
                if(world.field[j][i].mob instanceof Enemy)
                {
                    if(isEnemyNearby)
                    {
                        surprise=false;
                        return;
                    }
                    isEnemyNearby=true;
                    surprise=true;
                    //System.out.println("Surprise!");
                    return;
                }
            }
        }
        isEnemyNearby=false;
        surprise=false;

    }
    public Player(Level w)
    {
        super(w);
        auto=new MoveAutomation(this,true) {
            @Override
            public void setActions() {
                addAction(new GoTo(true) {
                    @Override
                    public Pair<Integer, Integer> getTarget() {
                        return mouse;
                    }
                });
                addAction(new SimpleAction(()->pickup()));
            }
            @Override
            public boolean interruptCondition()
            {
                if(direction!=Dir.none) return true;
                if(Controller.action.newInput()) return true;
                if(surprise) return true;
                return false;
            }
        };
        eq= new Equipment();
    }

    @Override
    public int getAttack() {
        return attack+eq.currentBonus.additionalAttack;
    }

    @Override
    public int getDefense() {
        return defense+eq.currentBonus.additionalDefense;
    }

    @Override
    public int getBlockChance() {
        return blockChance+eq.currentBonus.additionalBlockChance;
    }

    public boolean pickup()
    {
        if(!world.field[y][x].hasItem()) return false;
        Item item = world.field[y][x].getItem();
        if(eq.equip(item))
        {
            System.out.println("Player found "+item.getClass().getSimpleName()+"!");
            return true;
        }
        world.field[y][x].putItem(item);
        return false;
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
        findEnemy();
        if(auto.isActive())
        {
            auto.step();
            if(auto.currentState!= MoveAutomation.Status.interrupted) return;
        }
        mouse=Controller.action.getMouse();
        if(mouse!=null)
        {
            //System.out.println("Wcisnięto pole "+mouse.getValue()+" "+mouse.getKey());
            if(mouse.getKey()-y>=-1&&mouse.getKey()-y<=1&&mouse.getValue()-x>=-1&&mouse.getValue()-x<=1&&world.field[mouse.getKey()][mouse.getValue()].mob!=null)
            {
                Dir direct=null;
                switch (mouse.getKey()-y)
                {
                    case -1:
                        switch (mouse.getValue()-x)
                        {
                            case -1:
                                direct=Dir.leftUp;
                                break;
                            case 0:
                                direct=Dir.up;
                                break;
                            case 1:
                                direct=Dir.rightUp;
                                break;
                        }
                        break;
                    case 0:
                        switch (mouse.getValue()-x)
                        {
                            case -1:
                                direct=Dir.left;
                                break;
                            case 0:
                                if(world.field[y][x].hasItem())
                                {
                                    if(pickup())
                                    {
                                        currentAction=actionType.pickup;
                                        return;
                                    }
                                    direct=Dir.none;
                                    break;
                                }
                                currentAction=actionType.wait;
                                return;
                            case 1:
                                direct=Dir.right;
                                break;
                        }
                        break;
                    case 1:
                        switch (mouse.getValue()-x)
                        {
                            case -1:
                                direct=Dir.leftDown;
                                break;
                            case 0:
                                direct=Dir.down;
                                break;
                            case 1:
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

    @Override
    void onDeath() {
        endGame=true;
    }
}
