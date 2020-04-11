package Cellar.Model.Mobs;
import Cellar.Model.Level;
import Cellar.Model.Model;
public class Player extends Mob {
    public Player(Level world)
    {
        super(world);
    }
    @Override
    public void setParams() {

    }
    @Override
    public void moveMob() {
        x=world.playerX;
        y=world.playerY;
    }
}
