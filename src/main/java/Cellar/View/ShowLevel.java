package Cellar.View;

import Cellar.Model.Level;
import Cellar.Model.Room;
import javafx.scene.canvas.GraphicsContext;

import static Cellar.View.ShowRoom.showRoom;

public class ShowLevel {
    public static void showLevel(GraphicsContext gc, Level level){
        for (Room room: level.rooms){
            showRoom(gc, room);
        }
    }
}
