package Snake;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application
{
    private StringBuilder user = new StringBuilder("Guest");
    private Label message;
    private Stage stage;
    public static void main(String[] args)
    {
        launch();
        UserList.data.writeData(UserList.getUsersList());
    }
        public void start(Stage primaryStage)
        {
            stage = primaryStage;
            primaryStage.setTitle("Snake");
            UserList.showUser(user);
            Scene mainScene = new Scene(layoutFunction(primaryStage),500,300);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        }
        private GridPane layoutFunction(Stage primaryStage)
        {
            message = new Label();
            message.setText("Welcome to Snake");
            ChoiceBox<String> cb =new ChoiceBox<>(FXCollections.observableArrayList("Easy mode","Normal mode","Hard mode","Fucked up mode"));
            cb.getSelectionModel().selectFirst();
            StringBuilder str = new StringBuilder("Current user: " + user);
            Label userInfo = new Label();
            userInfo.setText(str.toString());
            Button start = new Button("Start game");
            start.setOnAction(event -> startGame(cb.getSelectionModel().getSelectedItem()));
            Button chUser = new Button("Change User");
            chUser.setOnAction(e -> {
                UserList.buildChooseUserWindow(user);
                str.delete(0, str.length());
                str.append("Current user: ");
                str.append(user);
                userInfo.setText(str.toString());
            });
            Button shRanking = new Button("Ranking");
            shRanking.setOnAction(e -> Ranking.runRanking(UserList.getUsersList()));
            Button exit = new Button("Exit game");
            exit.setOnAction(e -> primaryStage.close());
            GridPane layout = new GridPane();
            layout.setStyle("-fx-background-color: #696969;");
            layout.setPadding(new Insets(5,5,5,5));
            layout.setVgap(12.0);
            layout.setAlignment(Pos.CENTER);
            GridPane.setConstraints(message,0,0);
            GridPane.setConstraints(userInfo,0,1);
            GridPane.setConstraints(start,0,2);
            GridPane.setConstraints(cb,0,3);
            GridPane.setConstraints(chUser,0,4);
            GridPane.setConstraints(shRanking,0,5);
            GridPane.setConstraints(exit,0,6);
            layout.getChildren().addAll(message,userInfo,start,cb,chUser,shRanking,exit);
            return layout;
        }
        private void startGame(String difficult)
        {
            Game game = new Game(difficult);
            stage.hide();
            game.initGameWindow(user);
            for(User usr : UserList.getUsersList())
            {
                if(usr.getName().equals(user.toString()))
                {
                    if(usr.getScore() < game.thisGameScore)
                    {
                        usr.setScore(game.thisGameScore);
                        newRecord(game.thisGameScore);
                    }
                    else
                    {
                        gameOver(game.thisGameScore);
                    }
                    break;
                }
            }
            stage.show();
        }
        private void newRecord(int score)
        {
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("New record!");
            Label textRecord = new Label();
            textRecord.setText("Congratulation " + user + "!!!" + "\nYour new record is: " + score);
            GridPane recordLayout = new GridPane();
            recordLayout.setAlignment(Pos.CENTER);
            recordLayout.getChildren().addAll(textRecord);
            Scene recordScene = new Scene(recordLayout, 400, 100);
            record.setScene(recordScene);
            record.showAndWait();
        }
        private void gameOver(int score)
        {
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("You are bad!");
            Label textEndGame = new Label();
            textEndGame.setText("Game over\nYour score is " + score + "\nGood luck next time " + user + "!");
            GridPane recordLayout = new GridPane();
            Button exit = new Button("Exit game");
            exit.setOnAction(e -> record.close());
            recordLayout.setAlignment(Pos.CENTER);
            GridPane.setConstraints(textEndGame,0,0);
            GridPane.setConstraints(exit,0,1);
            recordLayout.getChildren().addAll(textEndGame,exit);
            Scene recordScene = new Scene(recordLayout, 400, 100);
            record.setScene(recordScene);
            record.showAndWait();
        }
}
