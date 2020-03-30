package Cellar;

import Cellar.Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller.start(stage);
    }
}
