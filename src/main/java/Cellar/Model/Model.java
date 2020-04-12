package Cellar.Model;

import Cellar.Model.Mobs.Player;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Model {
    public static int width = 15;
    public static int height = 15;
    public static int cornerSize = 48;
    public static int levelSize=7;
    public static int roomSize=11;
    public static int maxLevel=10;
    public static Dir direction= Dir.none;
    public enum Dir {
        left, right, up, down, none,leftDown,leftUp,rightDown,rightUp
    }

    public static ArrayList<Level> levels=new ArrayList<>();
    public static int currentLevelIndex=0;
    public static Level currentLevel;
    public static Player player=new Player(null);
    public static GraphicsContext gc;
}
