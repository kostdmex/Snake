package Snake;

import javafx.scene.paint.Color;

class Block {
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
    void setX(int tempX)
    {
        x = tempX;
    }
    void setY(int tempY)
    {
        y = tempY;
    }
    void setColor(Color tempColor)
    {
        color = tempColor;
    }
}
