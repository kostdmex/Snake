package Snake;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

class Game {
    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    static final int gridWidth = 16;
    static final int gridHeight = 16;
    static final int blockSize = ((int)primaryScreenBounds.getHeight()/gridHeight);
    private static final int centerWidth = ((int)primaryScreenBounds.getWidth()/4);
    private Stage game;
    private GraphicsContext gc;
    private ArrayList<Block> snake = new ArrayList<>();
    private enum directions
    {
        DOWN, LEFT, RIGHT, UP
    }
    private directions direction=directions.LEFT;
    private directions lastDirection = directions.LEFT;
    private double time = 0;
    private double incTime = 0.05;
    private double incScore = 5;
    private double cycle = 1.0;
    private Food fd = new Food();
    private final int WIDTH_POINTS_FIELD = 200;
    int thisGameScore = 0;
    private String difficult;
    Game(String tempDifficult)
    {
        difficult = tempDifficult;
    }
    private String username;
    void initGameWindow(StringBuilder user)
    {
        username = user.toString();
        game = new Stage();
        game.setTitle("Snake");
        game.initModality(Modality.APPLICATION_MODAL);
        game.centerOnScreen();
        game.setFullScreen(true);
        Group root = new Group();
        Scene gameScene = new Scene(root, blockSize *  gridWidth + WIDTH_POINTS_FIELD+centerWidth, blockSize * gridHeight);
        gameScene.setOnKeyPressed(event -> keyPressEvent(event.getCode()));
        Canvas gameField = new Canvas(blockSize * gridWidth + WIDTH_POINTS_FIELD+centerWidth, blockSize * gridHeight);
        gc = gameField.getGraphicsContext2D();
        gc.fillText("User: " + username,blockSize *  gridWidth+50+centerWidth,50);
        gc.fillText("Points: " + Integer.toString(thisGameScore),blockSize *  gridWidth+50+centerWidth,70);
        gc.fillText("Use arrows to move.\nUse escape to pause",blockSize *  gridWidth+50+centerWidth,90);
        initiateGame(gc);
        game.setScene(gameScene);
        root.getChildren().add(gameField);
        game.showAndWait();
    }
    private void keyPressEvent(KeyCode code)
    {
        if(code == KeyCode.DOWN && lastDirection != directions.UP)
        {
            direction = directions.DOWN;
            move(gc,direction);
            time = 0.0;
        }
        else if (code == KeyCode.UP && lastDirection != directions.DOWN)
        {
            direction = directions.UP;
            move(gc,direction);
            time = 0.0;
        }
        else if (code == KeyCode.LEFT && lastDirection != directions.RIGHT)
        {
            direction = directions.LEFT;
            move(gc,direction);
            time = 0.0;
        }
        else if (code == KeyCode.RIGHT && lastDirection != directions.LEFT)
        {
            direction = directions.RIGHT;
            move(gc,direction);
            time = 0.0;
        }
        else if (code == KeyCode.ESCAPE)
        {
            pauseGame();
        }
    }
    private static void background(GraphicsContext gc)
    {
        gc.setFill(Color.gray(0.5));
        for(int i=0;i<=blockSize*gridWidth-1;i+=blockSize)
        {
            gc.fillRect(i+centerWidth, 0, blockSize, blockSize);
            gc.fillRect(i+centerWidth, blockSize*(gridHeight-1), blockSize, blockSize);
        }
        for(int i=blockSize;i<=blockSize*gridHeight;i+=blockSize)
        {
            gc.fillRect(centerWidth, i, blockSize, blockSize);
            gc.fillRect(blockSize*(gridWidth-1)+centerWidth, i, blockSize, blockSize);
        }
        gc.setStroke(Color.BLACK);
        for(int i=blockSize;i<=blockSize*gridWidth-1;i+=blockSize)
        {
            gc.strokeLine(i+centerWidth,0,i+centerWidth,gridWidth*blockSize);
        }
        for(int i=blockSize;i<=blockSize*gridHeight;i+=blockSize)
        {
            gc.strokeLine(centerWidth,i,gridWidth*blockSize+centerWidth,i);
        }
    }
    private void drawSnake(GraphicsContext gc)
    {
        background(gc);
        fd.drawFood(gc);
        gc.setFill(snake.get(0).getColor());
        gc.fillRoundRect(snake.get(0).getX(), snake.get(0).getY(), blockSize, blockSize, blockSize, blockSize);
        for(int i = 1; i<snake.size();i++)
        {
            gc.setFill(snake.get(i).getColor());
            gc.fillRoundRect(snake.get(i).getX()+3, snake.get(i).getY()+3, blockSize-(blockSize/4), blockSize-(blockSize/4), blockSize, blockSize);
        }
    }
    private void initiateGame(GraphicsContext gc)
    {
        changeDifficult();
        snake.add(new Block((gridWidth-5)*blockSize+centerWidth,2*blockSize,Color.GREEN.darker()));
        for(int i=gridWidth-4;i<gridWidth-2;i++)
        {
            snake.add(new Block(i*blockSize+centerWidth,2*blockSize,Color.GREEN));
        }
        background(gc);
        fd.generatenewFood();
        while(!checkFoodCoordinates())
        {
            checkFoodCoordinates();
        }
        drawSnake(gc);
        timer.start();
    }
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            time+=incTime;
            if(time>=cycle)
            {
                time = 0.0;
                move(gc,direction);
            }
        }
    };
    private void move(GraphicsContext gc, directions direction)
    {
        if(!validPlace())
        {
            game.close();
        }
        for(Block tmp: snake)
        {
            gc.clearRect(tmp.getX(),tmp.getY(),blockSize,blockSize);
        }
        canEat();
        for(int i=snake.size()-1;i>0;i--)
        {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }
        switch(direction)
        {
            case UP:
                snake.get(0).setY(snake.get(0).getY()-blockSize);
                lastDirection = direction;
                break;
            case DOWN:
                snake.get(0).setY(snake.get(0).getY()+blockSize);
                lastDirection = direction;
                break;
            case LEFT:
                snake.get(0).setX(snake.get(0).getX()-blockSize);
                lastDirection = direction;
                break;
            case RIGHT:
                snake.get(0).setX(snake.get(0).getX()+blockSize);
                lastDirection = direction;
                break;
        }
        drawSnake(gc);
    }
    private void canEat()
    {
        if((fd.getX()==(snake.get(0).getX()-centerWidth)/blockSize)&&(fd.getY()==snake.get(0).getY()/blockSize))
        {
            int x = snake.get(snake.size()-1).getX();
            int y = snake.get(snake.size()-1).getY();
            snake.add(new Block(x, y, Color.GREEN));
            fd.generatenewFood();
            while(!checkFoodCoordinates())
            {
                checkFoodCoordinates();
            }
            thisGameScore += incScore;
            gc.setFill(Color.BLACK);
            gc.clearRect(blockSize *  gridWidth+50+centerWidth,40,WIDTH_POINTS_FIELD,40);
            gc.fillText("User: " + username,blockSize *  gridWidth+50+centerWidth,50);
            gc.fillText("Points: " + Integer.toString(thisGameScore),blockSize *  gridWidth+50+centerWidth,70);
        }
    }
    private boolean validPlace()
    {
        boolean flag=true;
        for(int i=centerWidth;i<=blockSize*gridWidth+centerWidth;i+=blockSize)
        {
            if(snake.get(0).getX()==i&&snake.get(0).getY()==0||snake.get(0).getX()==i&&snake.get(0).getY()==blockSize*(gridHeight-1))
            {
                flag = false;
                return flag;
            }
        }
        for(int i=blockSize;i<=blockSize*gridHeight;i+=blockSize)
        {
            if(snake.get(0).getX()==centerWidth&&snake.get(0).getY()==i||snake.get(0).getX()==blockSize*(gridWidth-1)+centerWidth&&snake.get(0).getY()==i)
            {
                flag = false;
                return flag;
            }
        }
        for(int i=1;i<snake.size();i++)
        {
            if((snake.get(0).getX()==snake.get(i).getX()) && (snake.get(0).getY()==snake.get(i).getY()))
            {
                flag = false;
                return flag;
            }
        }
        return flag;
    }
    private boolean checkFoodCoordinates()
    {
        for(int i=0;i<snake.size();i++)
        {
            if((fd.getX()==(snake.get(i).getX()-gridWidth)/blockSize) && (fd.getY()==snake.get(i).getY()/blockSize))
            {
                fd.generatenewFood();
                return false;
            }
        }
        return true;
    }
    private void pauseGame()
    {
        timer.stop();
        Stage pause = new Stage();
        pause.setOnCloseRequest(event -> {pause.close();timer.start();});
        pause.initModality(Modality.APPLICATION_MODAL);
        pause.setTitle("Pause");
        Button resume = new Button("Resume game");
        resume.setOnAction(event -> {pause.close();timer.start();});
        Button exit = new Button("Close game");
        exit.setOnAction(event -> {pause.close();game.close();});
        Label textPause = new Label();
        textPause.setText("Game is paused!");
        GridPane pauseLayout = new GridPane();
        pauseLayout.setAlignment(Pos.CENTER);
        pauseLayout.setStyle("-fx-background-color: #696969;");
        pauseLayout.setPadding(new Insets(5,5,5,5));
        pauseLayout.setVgap(12.0);
        GridPane.setConstraints(textPause,0,0);
        GridPane.setConstraints(resume,0,1);
        GridPane.setConstraints(exit,0,2);
        pauseLayout.getChildren().addAll(textPause,resume,exit);
        Scene errorScene = new Scene(pauseLayout, gridWidth*blockSize+WIDTH_POINTS_FIELD, gridHeight*blockSize);
        pause.setScene(errorScene);
        pause.showAndWait();
    }
    private void changeDifficult()
    {
        if(difficult.equals("Easy mode"))
        {
            incTime = 0.06;
            incScore = 10;
        }
        else if (difficult.equals("Normal mode"))
        {
            incTime = 0.09;
            incScore = 15;
        }
        else if (difficult.equals("Hard mode"))
        {
            incTime = 0.15;
            incScore = 25;
        }
        else if (difficult.equals("Fucked up mode"))
        {
            incTime = 0.2;
            incScore = 40;
        }
    }
}
