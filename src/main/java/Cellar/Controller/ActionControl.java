package Cellar.Controller;

import Cellar.Model.Field;
import Cellar.Model.Mobs.*;
import Cellar.Model.Mobs.Actions.*;
import Cellar.Model.Model;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import Cellar.Model.Model.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import static Cellar.Model.Model.*;
import static Cellar.Model.Preparations.*;

public class ActionControl {
    Object lock;
    volatile boolean isW,isA,isD,isS,isQ,isE,isZ,isC,isUp,isDown,isLeft,isRight, isRestart;
    Object pickupLock;
    volatile boolean isXHandler;
    boolean isPickup()
    {
        synchronized (pickupLock)
        {
            if(isXHandler)
            {
                isXHandler=false;
                return true;
            }
            return false;
        }
    }
    boolean isWait() { synchronized (lock) {return isSpace; }}
    volatile boolean isSpace;
    volatile KeyCode lastPressed;
    Object mouseLock;
    boolean isPressed;
    int x,y;
    long restartTime;
    long pressTime;
    boolean firstTime;
    public ActionControl()
    {
        lock=new Object();
        mouseLock=new Object();
        pickupLock = new Object();
        isPressed=false;
        isW=false;
        isA=false;
        isD=false;
        isS=false;
        isUp=false;
        isDown=false;
        isLeft=false;
        isRight=false;
        isSpace=false;
        lastPressed=null;
        isRestart=false;
        isXHandler=false;
    }
    public void keyPressed(KeyEvent key)
    {
        if(key.getCode()==KeyCode.X){
            synchronized (pickupLock) {isXHandler=true;}
            return;
        }
        synchronized (lock)
        {
            if (key.getCode() == KeyCode.W) isW=true;
            if (key.getCode() == KeyCode.A) isA=true;
            if (key.getCode() == KeyCode.S) isS=true;
            if (key.getCode() == KeyCode.D) isD=true;
            if (key.getCode() == KeyCode.Q) isQ=true;
            if (key.getCode() == KeyCode.E) isE=true;
            if (key.getCode() == KeyCode.Z) isZ=true;
            if (key.getCode() == KeyCode.C) isC=true;
            if (key.getCode() == KeyCode.UP) isUp=true;
            if (key.getCode() == KeyCode.DOWN) isDown=true;
            if (key.getCode() == KeyCode.LEFT) isLeft=true;
            if (key.getCode() == KeyCode.RIGHT) isRight=true;
            if (key.getCode() == KeyCode.SPACE) isSpace=true;
            if (key.getCode() == KeyCode.R) {
                if(!isRestart){restartTime=System.currentTimeMillis();}
                isRestart=true;
            }
            if(lastPressed==null)
                firstTime=true;
            lastPressed=key.getCode();
            updateDirection();
        }
    }
    public void keyReleased(KeyEvent key)
    {
        synchronized (lock)
        {
            if (key.getCode() == KeyCode.W) isW=false;
            if (key.getCode() == KeyCode.A) isA=false;
            if (key.getCode() == KeyCode.S) isS=false;
            if (key.getCode() == KeyCode.D) isD=false;
            if (key.getCode() == KeyCode.Q) isQ=false;
            if (key.getCode() == KeyCode.E) isE=false;
            if (key.getCode() == KeyCode.Z) isZ=false;
            if (key.getCode() == KeyCode.C) isC=false;
            if (key.getCode() == KeyCode.UP) isUp=false;
            if (key.getCode() == KeyCode.DOWN) isDown=false;
            if (key.getCode() == KeyCode.LEFT) isLeft=false;
            if (key.getCode() == KeyCode.RIGHT) isRight=false;
            if (key.getCode() == KeyCode.SPACE) isSpace=false;
            if (key.getCode() == KeyCode.R) isRestart=false;
            //teleport to exit
            if(key.getCode()==KeyCode.T){
                player.y=currentLevel.exitY;
                player.x=currentLevel.exitX;
            }
            if(lastPressed==key.getCode())
            {
                if(isW) lastPressed=KeyCode.W;
                else if(isA) lastPressed=KeyCode.A;
                else if(isS) lastPressed=KeyCode.S;
                else if(isD) lastPressed=KeyCode.D;
                else if(isQ) lastPressed=KeyCode.Q;
                else if(isE) lastPressed=KeyCode.E;
                else if(isZ) lastPressed=KeyCode.Z;
                else if(isC) lastPressed=KeyCode.C;
                else if(isUp) lastPressed=KeyCode.UP;
                else if(isDown) lastPressed=KeyCode.DOWN;
                else if(isLeft) lastPressed=KeyCode.LEFT;
                else if(isRight) lastPressed=KeyCode.RIGHT;
                else if(isSpace) lastPressed=KeyCode.SPACE;
                else if (isRestart) lastPressed=KeyCode.R;
                else lastPressed=null;
            }
            updateDirection();
        }
    }
    public void updateDirection()
    {
        if(lastPressed==null) Model.direction=Dir.none;
        else if(lastPressed==KeyCode.W) Model.direction=Dir.up;
        else if(lastPressed==KeyCode.A) Model.direction=Dir.left;
        else if(lastPressed==KeyCode.D) Model.direction=Dir.right;
        else if(lastPressed==KeyCode.S) Model.direction=Dir.down;
        else if(lastPressed==KeyCode.Q) Model.direction=Dir.leftUp;
        else if(lastPressed==KeyCode.E) Model.direction=Dir.rightUp;
        else if(lastPressed==KeyCode.Z) Model.direction=Dir.leftDown;
        else if(lastPressed==KeyCode.C) Model.direction=Dir.rightDown;
        else if(lastPressed==KeyCode.UP) Model.direction=Dir.up;
        else if(lastPressed==KeyCode.LEFT) Model.direction=Dir.left;
        else if(lastPressed==KeyCode.RIGHT) Model.direction=Dir.right;
        else if(lastPressed==KeyCode.DOWN) Model.direction=Dir.down;
        else if(lastPressed==KeyCode.SPACE ) Model.direction=Dir.wait;
        else if(isRestart){
            if(System.currentTimeMillis()-restartTime>=3000){
                System.out.println("RESTART");
                isRestart=false;
                clearGame();
                prepareLevels();
            }
        }
    }
    public void mouseClick(MouseEvent click)
    {
        synchronized (mouseLock)
        {
            int a= (int) Math.floor(click.getSceneX());
            int b= (int) Math.floor(click.getSceneY());
            if(eqIn.onClick(a,b)) return;
            if(a<Model.width*Model.cornerSize&&b<Model.height*Model.cornerSize)
            {
                isPressed=true;
                x=a/Model.cornerSize+Model.player.x-7;
                y=b/Model.cornerSize+Model.player.y-7;
            }
            else if(a>=width*cornerSize+cornerSize/2&&a<width*cornerSize+cornerSize/2+3*levelSize*roomSize
            && b>=30&&b<30+3*levelSize*roomSize)
            {
                isPressed=true;
                x=(a-(width*cornerSize+cornerSize/2))/3;
                y=(b-30)/3;

            }
            else
            {
                return;
            }
        }
    }
    public Pair<Integer,Integer> getMouse()
    {
        synchronized (mouseLock)
        {
            if(!isPressed) return null;
            isPressed=false;
            return new Pair<>(y,x);
        }
    }
    public boolean newInput()
    {
        synchronized (mouseLock)
        {
            return isPressed;
        }
    }
    public ActionType getPlayerAction()
    {
        if(isPickup()){
            return new PickupActionType(false);
        }
        if(isWait()) return new WaitActionType();
        Mob.Directions dir=null;
        synchronized (lock)
        {
            if(lastPressed!=null)
                switch (lastPressed)
                {
                    case W: dir= Mob.Directions.up; break;
                    case A: dir= Mob.Directions.left; break;
                    case S: dir= Mob.Directions.down; break;
                    case D: dir= Mob.Directions.right; break;
                    case Q: dir= Mob.Directions.leftUp; break;
                    case E: dir= Mob.Directions.rightUp; break;
                    case Z: dir= Mob.Directions.leftDown; break;
                    case C: dir= Mob.Directions.rightDown; break;
                }
        }
        if(dir!=null && !(!firstTime&& System.currentTimeMillis()-pressTime<195))
        {
            if(firstTime) {
                firstTime=false;
                pressTime=System.currentTimeMillis();
            }
            Field n = dir.getNeighborField(player);
            if(n.mob instanceof Enemy) return new AttackActionType(n.mob);
            return new MoveActionType(dir);
        }
        if(newInput()) {
            Pair<Integer,Integer> m = getMouse();
            Mob mobx = currentLevel.field[m.getKey()][m.getValue()].mob;
            if(mobx==null){
                player.mouse=m;
                return new AutoActionType();
            }
            if(mobx instanceof Player)
            {
                if(currentLevel.field[player.y][player.x].hasItem())
                {
                    return new PickupActionType(false);
                }
                else
                {
                    if(currentLevel.field[player.y][player.x].getType()== Field.TypeOfField.entrance||currentLevel.field[player.y][player.x].getType()== Field.TypeOfField.exit)
                        return new PickupActionType(false);
                    return new WaitActionType();
                }
            }
            else if(mobx instanceof Enemy)
            {
                if(player.nearestFields().anyMatch(f-> f.mob==mobx)) return new AttackActionType(mobx);
                else
                {
                    player.mouse=m;
                    return new AutoActionType();
                }
            }
            else
            {
                player.mouse=m;
                return new AutoActionType();
            }
        }
        return new NoneActionType();
    }
}
