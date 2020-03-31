package Cellar.Model;

import java.util.Random;

public class Model {
    public static int width = 20;
    public static int height = 20;
    public static int cornerSize = 25;
    public static int levelSize=7;
    public static int roomSize=10;
    public static Dir direction= Dir.none;
    static public Random rand = new Random();
    static public Level level= new Level();
    public enum Dir {
        left, right, up, down, none
    }
}
