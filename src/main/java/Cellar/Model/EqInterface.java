package Cellar.Model;

import Cellar.Model.Items.*;

import java.util.ArrayList;
import java.util.Optional;

import static Cellar.Model.Model.*;
import static Cellar.View.RightInterface.eqSelected;
import static Cellar.View.RightInterface.eqSlot;

public class EqInterface {
    static int counter;
    Equipment eq;
    class Slot {
        public int id,x,y,width,height;
        public Slot(int x, int y) {
            this.id=counter++;
            this.x=x;
            this.y=y;
            this.width=cornerSize;
            this.height=cornerSize;
        }
    }
    ArrayList<Slot> slots;
    Slot last;
    public EqInterface(Equipment eq) {
        this.eq=eq;
        EqInterface.counter=0;
        last=null;
        slots=new ArrayList<>();
        slots.add(new Slot((width+1)*cornerSize+24, 425));
        slots.add(new Slot((width+2)*cornerSize+24, 425));
        slots.add(new Slot((width+3)*cornerSize+24, 425));
        for(int j=0; j<=2; j++)
            for(int i=0; i<=4; i++){
                slots.add(new Slot((width+i)*cornerSize+24, 500+j*cornerSize));
            }
    }
    public boolean onClick(int x, int y) {
        Optional<Slot> found;
        found=slots.stream().filter(a -> a.x<=x && a.x+a.width>x && a.y<=y && a.y+a.height>y).findAny();
        if (!found.isPresent()) {
            last=null;
            return false;
        }
        if(last==null) {
            if(eq.items[found.get().id]==null) {
                return false;
            }
            last=found.get();
        }
        else{
            if(last.id==found.get().id) {
                Item dropped = eq.drop(last.id);
                currentLevel.field[player.y][player.x].items.add(dropped);
            }
            else {
                eq.swapSlots(last.id,found.get().id);
            }
            last=null;
        }
        return true;
    }
    public void draw() {
        slots.stream().forEach(a -> {
            if(last!=null && last.id==a.id) gc.drawImage(eqSelected,a.x, a.y, a.width, a.height);
            else gc.drawImage(eqSlot,a.x, a.y, a.width, a.height);
            if(eq.items[a.id]!=null) gc.drawImage(eq.items[a.id].texture, a.x+2, a.y+2, cornerSize-6, cornerSize-6);
        });
    }
}
