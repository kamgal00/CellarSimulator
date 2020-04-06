package Cellar.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static Cellar.Model.Model.*;


public class View {

    public static void show(GraphicsContext gc){
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, width * cornerSize, height * cornerSize);
        for(int i=0; i<=width; i++){
            for (int j=0; j<=height; j++){
                gc.setFill(Color.BLACK);
                gc.fillRect(i*cornerSize, j*cornerSize, cornerSize-1, cornerSize-1);
            }
        }
        Image jabuszko=new Image("file:resources/food1.png");
        gc.drawImage(jabuszko, cornerSize, cornerSize, 10*cornerSize, 5*cornerSize);

    }
}
