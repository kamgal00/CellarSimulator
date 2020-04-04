package Cellar.Model;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    public static int width = 20;
    public static int height = 20;
    public static int cornerSize = 25;
    public static int levelSize=7;
    public static int roomSize=11;
    public static Dir direction= Dir.none;
    public enum Dir {
        left, right, up, down, none
    }

    public static ArrayList<Level> levels=new ArrayList<>();
    public static int currentLevelIndex=0;
    public static Level currentLevel;
    public static LevelGenerator levelGenerator;
}
