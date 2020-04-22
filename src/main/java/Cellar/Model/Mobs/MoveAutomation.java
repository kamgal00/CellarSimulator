package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Model;
import Cellar.Model.PathFinder;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class MoveAutomation {
    Mob child;
    public enum Status{stopped,started,interrupted}
    boolean obstacleCase;
    Status currentState;
    ArrayList<Action> actionList;
    int currentAction;
    public MoveAutomation(Mob a,boolean interruptWhenObstacle)
    {
        child = a;
        obstacleCase=interruptWhenObstacle;
        actionList=new ArrayList<>();
        currentState=Status.stopped;
        setActions();
        currentAction=0;
    }
    public abstract void setActions();
    public abstract boolean interruptCondition();
    public void addAction(Action a)
    {
        actionList.add(a);
    }
    public boolean isActive() {return currentState==Status.started; }
    public void start()
    {
        currentState=Status.stopped;
        currentAction=0;
        while(currentAction<actionList.size())
        {
            actionList.get(currentAction).init();
            if(actionList.get(currentAction).state==Status.stopped)
            {
                currentAction++;
                continue;
            }
            currentState=Status.started;
            return;
        }
    }
    public void step()
    {
        if(currentState==Status.stopped)
        {
            currentState=Status.interrupted;
            return;
        }
        if(currentState==Status.interrupted) return;
        if(interruptCondition())
        {
            currentState=Status.interrupted;
            return;
        }
        actionList.get(currentAction).step();
        if(actionList.get(currentAction).state==Status.interrupted) {
            currentState=Status.interrupted;
            return;
        }
        if(actionList.get(currentAction).state==Status.stopped)
        {
            currentAction++;
            currentState=Status.stopped;
            while(currentAction<actionList.size())
            {
                actionList.get(currentAction).init();
                if(actionList.get(currentAction).state==Status.stopped)
                {
                    currentAction++;
                    continue;
                }
                currentState=Status.started;
                return;
            }
        }
    }


    //Action classes
    public interface quickAction{
        boolean act();
    }
    public abstract class Action{
        Status state;
        public abstract void step();
        public void init()
        {
            state=Status.started;
        }
        public Action()
        {
            state=Status.stopped;
        }
    }
    public abstract class GoTo extends Action
    {
        int c;
        boolean discoveredMatters;
        ArrayList<Pair<Integer,Integer>> path;
        public GoTo(boolean discoveredMatters)
        {
            super();
            this.discoveredMatters=discoveredMatters;
        }
        public void step()
        {
            if(state!=Status.started)
            {
                state=Status.interrupted;
                return;
            }
            if(child.world.field[path.get(c).getKey()][path.get(c).getValue()].mob!=null)
            {
                if(obstacleCase)
                {
                    state=Status.interrupted;
                    return;
                }
                child.currentAction= Mob.actionType.wait;
                return;
            }
            if(child.world.field[path.get(c).getKey()][path.get(c).getValue()].getType()== Field.TypeOfField.wall)
            {
                state=Status.interrupted;
                return;
            }
            boolean ok=false;
            switch(path.get(c).getKey()-child.y)
            {
                case 1:
                    switch(path.get(c).getValue()-child.x)
                    {
                        case 1:
                            child.move(Model.Dir.rightDown); ok=true;
                            break;
                        case 0:
                            child.move(Model.Dir.down); ok=true;
                            break;
                        case -1:
                            child.move(Model.Dir.leftDown); ok=true;
                            break;
                    }
                    break;
                case 0:
                    switch(path.get(c).getValue()-child.x)
                    {
                        case 1:
                            child.move(Model.Dir.right); ok=true;
                            break;
                        case 0:
                            child.move(Model.Dir.none); ok=true;
                            break;
                        case -1:
                            child.move(Model.Dir.left); ok=true;
                            break;
                    }
                    break;
                case -1:
                    switch(path.get(c).getValue()-child.x)
                    {
                        case 1:
                            child.move(Model.Dir.rightUp); ok=true;
                            break;
                        case 0:
                            child.move(Model.Dir.up); ok=true;
                            break;
                        case -1:
                            child.move(Model.Dir.leftUp); ok=true;
                            break;
                    }
            }
            if(!ok) child.move(Model.Dir.none);
            c++;
            if(c==path.size()) state=Status.stopped;
        }
        public void init()
        {
            super.init();
            Pair<Integer,Integer> target=getTarget();
            if(target==null)
            {
                state=Status.interrupted;
                return;
            }
            path= PathFinder.findPath(discoveredMatters,child.world,new Pair<>(child.y,child.x),target);
            if(path==null||path.size()==0)
            {
                state=Status.interrupted;
                return;
            }
            c=0;
            state=Status.started;
        }
        public abstract Pair<Integer,Integer> getTarget();
    }
    public class Sleep extends Action{
        int counter,sleepTime;
        public Sleep(int time)
        {
            sleepTime=time;
        }
        @Override
        public void step() {
            if(state==Status.stopped)
            {
                state=Status.interrupted;
                return;
            }
            if(state==Status.interrupted) return;
            counter--;
            child.currentAction= Mob.actionType.wait;
            if(counter==0) state=Status.stopped;
        }
        @Override
        public void init() {
            super.init();
            counter=sleepTime;
        }
    }
    public  class SimpleAction extends Action{
        quickAction a;
        public SimpleAction(quickAction action)
        {
            super();
            a=action;
        }

        @Override
        public void step() {
            if(state!=Status.started)
            {
                state=Status.interrupted;
                return;
            }
            if(a.act()) state=Status.stopped;
            else state=Status.interrupted;
        }
    }
}
