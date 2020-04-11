package Cellar.Controller;

import Cellar.Model.LevelGenerator;
import Cellar.Model.RoomGenerator;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static Cellar.Controller.Move.move;
import static Cellar.Model.Model.*;
import static Cellar.Model.UberBrain.*;


public class Controller {

    public static void start(Stage primaryStage){
        try{

            VBox root = new VBox();
            Canvas c = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 100000000) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * cornerSize, height * cornerSize);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Cellar Simulator");
            primaryStage.show();

            RoomGenerator.loadRoomGenerator();
            levelGenerator=new LevelGenerator();
            levels.add(levelGenerator.levelGenerate());
            currentLevel=levels.get(currentLevelIndex);
            currentLevel.addMob(player, currentLevel.entranceY, currentLevel.entranceX);
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W) {
                    direction= Dir.up;
                }
                if (key.getCode() == KeyCode.A) {
                    direction= Dir.left;
                }
                if (key.getCode() == KeyCode.S) {
                    direction= Dir.down;
                }
                if (key.getCode() == KeyCode.D) {
                    direction= Dir.right;
                }
            });
            scene.addEventFilter(KeyEvent.KEY_RELEASED, key->
            {
                direction = Dir.none;
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void tick(GraphicsContext gc){
        brainTick(gc);
        //show(gc); //todo: wyświetlanie poziomu (w klasie View)
        move(); //
    }
}
