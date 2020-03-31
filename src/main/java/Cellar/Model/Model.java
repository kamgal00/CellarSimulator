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
    static public ArrayList<Level> levelList=new ArrayList<>();
    static public int currentLevel=0;
    public enum Dir {
        left, right, up, down, none
    }
}
