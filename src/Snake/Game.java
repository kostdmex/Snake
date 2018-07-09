package Snake;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

class Game {
    private static final int blockSize = 20;
    private static final int gridWidth = 15;
    private static final int gridHeight = 20;

    private Stage game;
    private GraphicsContext gc;
    private double time = 0;
    private double cycle = 1.0;
    void initGameWindow()
    {
        game = new Stage();
        game.setTitle("Snake");
        game.initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene gameScene = new Scene(root, blockSize *  gridWidth, blockSize * gridHeight);
        gameScene.setOnKeyPressed(event -> keyPressEvent(event.getCode()));
        game.show();
    }
    private void keyPressEvent(KeyCode code)
    {
        if(code == KeyCode.DOWN)
        {
            //smth down
        }
        else if (code == KeyCode.UP)
        {
            //smth up
        }
        else if (code == KeyCode.LEFT)
        {
            //smth left
        }
        else if (code == KeyCode.RIGHT)
        {
            //smth right
        }
    }
}
