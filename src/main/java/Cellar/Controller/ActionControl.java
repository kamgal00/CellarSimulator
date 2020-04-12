package Cellar.Controller;

import Cellar.Model.Model;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import Cellar.Model.Model.*;
public class ActionControl {
    Object lock;
    volatile boolean isW,isA,isD,isS;
    volatile KeyCode lastPressed;
    public ActionControl()
    {
        lock=new Object();
        isW=false;
        isA=false;
        isD=false;
        isS=false;
        lastPressed=null;
    }
    public void keyPressed(KeyEvent key)
    {
        synchronized (lock)
        {
            if (key.getCode() == KeyCode.W) isW=true;
            if (key.getCode() == KeyCode.A) isA=true;
            if (key.getCode() == KeyCode.S) isS=true;
            if (key.getCode() == KeyCode.D) isD=true;
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
            if(lastPressed==key.getCode())
            {
                if(isW) lastPressed=KeyCode.W;
                else if(isA) lastPressed=KeyCode.A;
                else if(isS) lastPressed=KeyCode.S;
                else if(isD) lastPressed=KeyCode.D;
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
    }
}
