package Snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

class Food {
    private int x,y;
    Food()
    {
        x = ThreadLocalRandom.current().nextInt(2,Game.gridWidth-1);
        y = ThreadLocalRandom.current().nextInt(2,Game.gridHeight-1);
    }
    void generatenewFood()
    {
        x = ThreadLocalRandom.current().nextInt(2,Game.gridWidth-1);
        y = ThreadLocalRandom.current().nextInt(2,Game.gridHeight-1);
    }
    void drawFood(GraphicsContext gc)
    {
        gc.setFill(Color.RED);
        gc.fillRoundRect(x*Game.blockSize,y*Game.blockSize,Game.blockSize,Game.blockSize,20.0,20.0);
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
