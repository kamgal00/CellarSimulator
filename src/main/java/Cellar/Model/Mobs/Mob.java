package Cellar.Model.Mobs;

import Cellar.Model.Field;
import Cellar.Model.Level;
import Cellar.Model.Mobs.Actions.ActionType;
import Cellar.Model.Model;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.Random;
import java.util.stream.Stream;

import static Cellar.Model.Mobs.Mob.actionType.*;

public abstract class Mob {
    public int expForKill;
    public int hp;
    public int maxHp;
    public int attack;
    public int defense;
    public int blockChance;
    public int x;
    public int y;
    public boolean hasAuto=false;
    public MoveAutomation auto;
    public Level world;
    public Mob attackedMob;
    public enum actionType{none, move,pickup,attack,wait}
    public enum Directions {
        none(0,0,'n'),
        up(-1,0,'n'),
        down(1,0,'n'),
        left(0,-1,'l'),
        right(0,1,'r'),
        leftUp(-1,-1,'l' ),
        leftDown(1,-1,'l'),
        rightUp(-1,1,'r'),
        rightDown(1,1,'r');
        public char facing;
        int dX,dY;
        Directions( int y, int x, char f)
        {
            dY=y;
            dX=x;
            facing=f;
        }
        public Field getNeighborField(Mob s)
        {
            return s.world.field[s.y+dY][s.x+dX];
        }
        public boolean pushMob(Mob s)
        {
            if(dX==0&&dY==0) return false;
            Field x = getNeighborField(s);
            if(x.getType()== Field.TypeOfField.wall) return false;
            if(x.mob!=null) return false;
            if(facing=='l') s.currIm=s.leftIm;
            if(facing=='r') s.currIm=s.rightIm;
            s.world.field[s.y][s.x].mob=null;
            s.y+=dY;
            s.x+=dX;
            s.world.field[s.y][s.x].mob=s;
            return true;
        }
        public static Directions getDirection(Mob s,Pair<Integer,Integer> p)
        {
            Pair<Integer,Integer> x = new Pair<>(s.y,s.x);
            switch (p.getKey()-x.getKey())
            {
                case -1:
                    switch (p.getValue()-x.getValue())
                    {
                        case -1:
                            return leftUp;
                        case 0:
                            return up;
                        case 1:
                            return rightUp;
                    }
                case 0:
                    switch (p.getValue()-x.getValue())
                    {
                        case -1:
                            return left;
                        case 0:
                            return none;
                        case 1:
                            return right;
                    }
                case 1:
                    switch (p.getValue()-x.getValue())
                    {
                        case -1:
                            return leftDown;
                        case 0:
                            return down;
                        case 1:
                            return rightDown;
                    }
            }
            return none;
        }
    };
    public actionType currentAction;
    public Image leftIm,rightIm,currIm;
    public Mob(Level world) {
        this.world=world;
        setParams();
        currentAction=actionType.none;
        attackedMob=null;
        currIm=rightIm;
    }
    public abstract void setParams();
    void getView() {};
    public boolean moveMob() {
        getView();
        if(!hasAuto)
        {
            ActionType action = getAction();
            return action.trigger(this);
        }
        else
        {
            if(auto.isActive())
            {
                auto.step();
                if(auto.currentState== MoveAutomation.Status.interrupted) return false;
                return true;
            }
            ActionType action = getAction();
            return action.trigger(this);
        }
    }
    public Stream<Field> nearestFields() {
        return Stream.of(world.field[y-1][x],world.field[y+1][x],world.field[y][x-1],world.field[y][x+1],world.field[y-1][x-1],world.field[y-1][x+1],world.field[y+1][x-1],world.field[y+1][x+1]);
    }
    public int getAttack() { return attack;}
    public int getDefense() { return defense;}
    public int getBlockChance() { return blockChance;}
    public void attack(Mob enemy) {
        Random rand = new Random();
        int dmg_min = 8 * this.getAttack() / 10;
        int dmg_max = (125 * this.getAttack() + 99) / 100;
        int dmg = (rand.nextInt((dmg_max - dmg_min) + 1) + dmg_min) - enemy.getDefense();
        if(rand.nextInt(101) <= enemy.getBlockChance()) System.out.println(this.getClass().getSimpleName()+"'s attack blocked by a "+enemy.getClass().getSimpleName()+"!");
        else
        {
            if(dmg <= 0)
            {
                System.out.println(this.getClass().getSimpleName()+" dealt 1 damage to a "+enemy.getClass().getSimpleName()+"!");
                enemy.takeDamage(1);
            }
            else
            {
                System.out.println(this.getClass().getSimpleName()+" dealt "+dmg+" damage to a "+enemy.getClass().getSimpleName()+"!");
                enemy.takeDamage(dmg);
            }
        }
        currentAction=actionType.attack;
    }
    public boolean isVisible() {
        return( Model.player.x-Model.width/2<=x&&Model.player.x+Model.width/2>=x&&Model.player.y-Model.height/2<=y&&Model.player.y+Model.height/2>=y);
    }
    public String toString()
    {
        return this.getClass().toString()+" "+y+" "+x;
    }
    /*public Mob move(Model.Dir direct) {
        switch (direct)
        {
            case none:
                currentAction=actionType.none;
                return this;
            case pickup:
            case wait:
                currentAction=actionType.wait;
                return this;
            case up:
                if(world.field[y-1][x].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x].mob==null)
                    {
                        world.field[y][x].mob=null;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.up;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case down:
                if(world.field[y+1][x].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x].mob==null)
                    {
                        world.field[y][x].mob=null;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.down;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case left:
                currIm=leftIm;
                if(world.field[y][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.left;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case right:
                currIm=rightIm;
                if(world.field[y][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.right;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case rightUp:
                currIm=rightIm;
                if(world.field[y-1][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.rightUp;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case rightDown:
                currIm=rightIm;
                if(world.field[y+1][x+1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x+1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x++;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.rightDown;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x+1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case leftUp:
                currIm=leftIm;
                if(world.field[y-1][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y-1][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        y--;
                        world.field[y][x].mob=this;
                        currentAction=actionType.leftUp;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y-1][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
            case leftDown:
                currIm=leftIm;
                if(world.field[y+1][x-1].getType()!= Field.TypeOfField.wall)
                {
                    if(world.field[y+1][x-1].mob==null)
                    {
                        world.field[y][x].mob=null;
                        x--;
                        y++;
                        world.field[y][x].mob=this;
                        currentAction=actionType.leftDown;
                        return this;
                    }
                    else
                    {
                        currentAction=actionType.none;
                        return world.field[y+1][x-1].mob;
                    }
                }
                else
                {
                    currentAction= actionType.none;
                    return null;
                }
        }
        return null;
    }*/
    void takeDamage(int d) {
        hp-=d;
        if(hp<=0)
        {
            hp=0;
            die();
        }
    }
    void onDeath() {

    }
    void die() {
        world.field[y][x].mob=null;
        world.mobs.remove(this);
        System.out.println(this.getClass().getSimpleName()+" died!");
        onDeath();
    }
    abstract ActionType getAction();
}
