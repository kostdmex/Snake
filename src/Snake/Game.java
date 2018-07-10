package Snake;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

class Game {
    static final int blockSize = 20;
    static final int gridWidth = 20;
    static final int gridHeight = 20;

    private Stage game;
    private GraphicsContext gc;
    private ArrayList<Block> snake = new ArrayList<>();
    private enum directions
    {
        DOWN, LEFT, RIGHT, UP
    }
    private directions direction=directions.LEFT;
    private double time = 0;
    private double cycle = 1.0;
    private Food fd = new Food();
    void initGameWindow()
    {
        game = new Stage();
        game.setTitle("Snake");
        game.initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene gameScene = new Scene(root, blockSize *  gridWidth, blockSize * gridHeight);
        gameScene.setOnKeyPressed(event -> keyPressEvent(event.getCode()));
        Canvas gameField = new Canvas(blockSize * gridWidth, blockSize * gridHeight);
        gc = gameField.getGraphicsContext2D();
        startGame(gc);
        game.setScene(gameScene);
        root.getChildren().add(gameField);
        game.showAndWait();
    }
    private void keyPressEvent(KeyCode code)
    {
        if(code == KeyCode.DOWN)
        {
            direction = directions.DOWN;
        }
        else if (code == KeyCode.UP)
        {
            direction = directions.UP;
        }
        else if (code == KeyCode.LEFT)
        {
            direction = directions.LEFT;
        }
        else if (code == KeyCode.RIGHT)
        {
            direction = directions.RIGHT;
        }
    }
    private static void background(GraphicsContext gc)
    {
        gc.setFill(Color.gray(0.5));
        for(int i=0;i<=blockSize*gridWidth;i+=20)
        {
            gc.fillRect(i, 0, blockSize, blockSize);
            gc.fillRect(i, blockSize*(gridHeight-1), blockSize, blockSize);
        }
        for(int i=20;i<=blockSize*gridHeight;i+=20)
        {
            gc.fillRect(0, i, blockSize, blockSize);
            gc.fillRect(blockSize*(gridWidth-1), i, blockSize, blockSize);
        }
        gc.setStroke(Color.BLACK);
        for(int i=20;i<=blockSize*gridWidth;i+=20)
        {
            gc.strokeLine(i,0,i,400);
        }
        for(int i=20;i<=blockSize*gridHeight;i+=20)
        {
            gc.strokeLine(0,i,400,i);
        }
    }
    private void drawSnake(GraphicsContext gc)
    {
        background(gc);
        fd.drawFood(gc);
        gc.setFill(Color.GREEN);
        for(Block bk: snake)
        {
            gc.fillRoundRect(bk.getX(),bk.getY(),blockSize,blockSize,20,20);
        }
    }
    private void startGame(GraphicsContext gc)
    {
        for(int i=4;i<8;i++)
        {
            snake.add(new Block(i*20,80,Color.GREEN));
        }
        background(gc);
        drawSnake(gc);
        timer.start();
    }
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            time+=0.05;
            if(time>=cycle)
            {
                time = 0.0;
                move(gc,direction);
            }
        }
    };
    private void move(GraphicsContext gc, directions direction)
    {
        for(Block tmp: snake)
        {
            gc.clearRect(tmp.getX(),tmp.getY(),blockSize,blockSize);
        }
        if((fd.getX()==snake.get(0).getX()/blockSize)&&(fd.getY()==snake.get(0).getY()/blockSize))
        {
            int x = snake.get(snake.size()-1).getX();
            int y = snake.get(snake.size()-1).getY();
            snake.add(new Block(x, y, Color.GREEN));
            fd.generatenewFood();
        }
        for(int i=snake.size()-1;i>0;i--)
        {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }
        switch(direction)
        {
            case UP:
                snake.get(0).setY(snake.get(0).getY()-20);
                break;
            case DOWN:
                snake.get(0).setY(snake.get(0).getY()+20);
                break;
            case LEFT:
                snake.get(0).setX(snake.get(0).getX()-20);
                break;
            case RIGHT:
                snake.get(0).setX(snake.get(0).getX()+20);
                break;
        }
        drawSnake(gc);
    }
}
