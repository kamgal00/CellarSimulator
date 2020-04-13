package Cellar.Model.Mobs;

import Cellar.Model.Model;

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

    }
    public class Sleep extends Action{
        int counter,sleepTime;
        public Sleep(int time)
        {
            sleepTime=time;
        }
        @Override
        public void step() {
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
}
