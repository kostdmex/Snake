package Snake;

import javafx.scene.paint.Color;

public class Block {
    private int x, y;
    private Color color;
    Block(int tempX, int tempY, Color tempColor)
    {
        x = tempX;
        y = tempY;
        color = tempColor;
    }
    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }
    Color getColor()
    {
        return color;
    }
    int setX()
    {
        return x;
    }
    int setY()
    {
        return y;
    }
    void setColor(Color tempColor)
    {
        color = tempColor;
    }
}
