package Snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    private Stage stage;
    public static void main(String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Snake");
        Game test = new Game();
        test.initGameWindow();
    }
}
