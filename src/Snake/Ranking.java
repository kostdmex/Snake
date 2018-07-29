package Snake;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.Comparator;

class Ranking
{
    static void runRanking(ArrayList<User> users)
    {
        Stage rankingWindow = new Stage();
        rankingWindow.initModality(Modality.APPLICATION_MODAL);
        rankingWindow.setTitle("Ranking");

        ObservableList<User> usersList = FXCollections.observableArrayList(users);
        Comparator<User> comparator = Comparator.comparingInt(User::getScore);
        comparator = comparator.reversed();
        FXCollections.sort(usersList, comparator);

        TableView<User> rankingTable = new TableView<>();
        TableColumn<User, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> scoreColumn = new TableColumn<>("Record");
        scoreColumn.setMinWidth(150);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        rankingTable.setItems(usersList);
        rankingTable.getColumns().addAll(nameColumn, scoreColumn);

        GridPane rankingLayout = new GridPane();
        rankingLayout.getChildren().add(rankingTable);
        Scene rankingScene = new Scene(rankingLayout, 350, 380);
        rankingWindow.setScene(rankingScene);
        rankingWindow.setResizable(false);
        rankingWindow.showAndWait();
    }
}