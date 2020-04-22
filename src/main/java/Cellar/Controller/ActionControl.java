package Cellar.Controller;

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
    volatile boolean isW,isA,isD,isS,isQ,isE,isZ,isC,isX,isUp,isDown,isLeft,isRight,isSpace, isRestart;
    volatile KeyCode lastPressed;
    Object mouseLock;
    boolean isPressed;
    int x,y;
    long restartTime;
    public ActionControl()
    {
        lock=new Object();
        mouseLock=new Object();
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
        isX=false;
    }
    public void keyPressed(KeyEvent key)
    {
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
            if (key.getCode() == KeyCode.X) isX=true;
            if (key.getCode() == KeyCode.R) {
                if(!isRestart){restartTime=System.currentTimeMillis();}
                isRestart=true;
            }
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
            if (key.getCode() == KeyCode.X) isSpace=false;
            if (key.getCode() == KeyCode.R) isRestart=false;
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
                else if(isX) lastPressed=KeyCode.X;
                if (isRestart) lastPressed=KeyCode.R;
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
        else if(lastPressed==KeyCode.X ) Model.direction=Dir.pickup;
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
}
