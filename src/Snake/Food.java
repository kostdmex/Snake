package Snake;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.util.concurrent.ThreadLocalRandom;

class Food {
    private int x,y;
    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    private static final int centerWidth = ((int)primaryScreenBounds.getWidth()/4);
    void generatenewFood()
    {
        x = ThreadLocalRandom.current().nextInt(2,Game.gridWidth-1);
        y = ThreadLocalRandom.current().nextInt(2,Game.gridHeight-1);
    }
    void drawFood(GraphicsContext gc)
    {
        gc.setFill(Color.RED);
        gc.fillRoundRect(x*Game.blockSize+centerWidth,y*Game.blockSize,Game.blockSize,Game.blockSize,Game.blockSize,Game.blockSize);
    }
    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }
}
