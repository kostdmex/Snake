package Snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage)
    {
        Game test = new Game();
        test.initGameWindow();
    }
}
