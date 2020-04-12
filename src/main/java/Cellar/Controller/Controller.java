package Cellar.Controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static Cellar.Model.Model.*;
import static Cellar.Model.Preparations.prepareLevels;
import static Cellar.Model.UberBrain.*;


public class Controller {
    public static ActionControl action;
    public static void start(Stage primaryStage){
        try{

            VBox root = new VBox();
            Canvas c = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);
            action=new ActionControl();
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

            prepareLevels();

            scene.addEventFilter(KeyEvent.KEY_PRESSED, action::keyPressed);
            scene.addEventFilter(KeyEvent.KEY_RELEASED, action::keyReleased);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void tick(GraphicsContext gc){
        brainTick(gc);
        //show(gc); //todo: wyświetlanie poziomu (w klasie View)
        //move(); //
    }
}
