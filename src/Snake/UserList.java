package Snake;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

class UserList {
    private static Database read = new Database();
    static private ArrayList<User> list = new ArrayList<>(read.readData());
    static void showUser(StringBuilder user)
    {
        if(list.size()>0)
        {
            buildChooseUserWindow(user);
        }
        else
        {
            buildAddUserWindow(user);
        }
    }
    private static void buildAddUserWindow(StringBuilder userStr)
    {
        Stage addUser = new Stage();
        addUser.initModality(Modality.APPLICATION_MODAL);
        addUser.setTitle("Add new user");
        TextField name = new TextField();
        Label text = new Label();
        text.setText("Write a new user name: ");
        Button addUserButton = new Button("Add user");
        addUserButton.setOnAction((ActionEvent e) -> {
            userStr.delete(0,userStr.length());
            userStr.append(name.getText());
            User temp = new User(name.getText(),0);
            if(!checkName(temp))
            {
                    errorWindow();
            }
            else
            {
                list.add(temp);
            }
            addUser.close();
        });

        GridPane addUserLayout = new GridPane();
        addUserLayout.setPadding(new Insets(5,5,5,5));
        addUserLayout.setVgap(8);
        addUserLayout.setHgap(10);
        GridPane.setConstraints(text, 0, 1);
        GridPane.setConstraints(name, 1, 1);
        GridPane.setConstraints(addUserButton, 1, 2);
        addUserLayout.getChildren().addAll(text, name, addUserButton);

        Scene addUserScene = new Scene(addUserLayout, 300, 150);
        addUser.setScene(addUserScene);
        addUser.showAndWait();
    }
    private static boolean checkName(User temp)
    {
        if (temp.getName().equals("")) {
            return false;
        }
        for (User data:list)
        {
            if((temp.getName().toLowerCase().equals(data.getName().toLowerCase()))) {
                return false;
            }
        }
        return true;
    }
    private static void errorWindow()
    {
        Stage error = new Stage();
        error.initModality(Modality.APPLICATION_MODAL);
        error.setTitle("Error");
        Label textError = new Label();
        textError.setText("You made something wrong, check some rules:\n1. Username need to be new.\n2. Username can not be empty.\n3. Case size matters");
        GridPane errorLayout = new GridPane();
        errorLayout.setAlignment(Pos.CENTER);
        errorLayout.getChildren().addAll(textError);
        Scene errorScene = new Scene(errorLayout,400,100);
        error.setScene(errorScene);
        error.showAndWait();
    }
    static void buildChooseUserWindow(StringBuilder userStr)
    {
        Stage chooseUserWindow = new Stage();
        chooseUserWindow.initModality(Modality.APPLICATION_MODAL);
        chooseUserWindow.setTitle("Choose user");

        Label label = new Label();
        label.setText("Choose user:");
        ListView<String> userChooseList = new ListView<>();
        for(User node : list)
            userChooseList.getItems().add(node.getName());


        Button addAdditionalUserButton = new Button("Add user");
        addAdditionalUserButton.setOnAction(e -> {
            buildAddUserWindow(userStr);
            userChooseList.getItems().clear();
            for(User node : list)
                userChooseList.getItems().add(node.getName());
        });

        Button closeWindowButton = new Button("Go!");
        closeWindowButton.setOnAction(e -> {
            userStr.delete(0, userStr.length());
            if(userChooseList.getSelectionModel().getSelectedItem() == null)
                userStr.append("Guest");
            else
                userStr.append(userChooseList.getSelectionModel().getSelectedItem());
            chooseUserWindow.close();
        });

        GridPane chooseUserLayout = new GridPane();
        chooseUserLayout.setPadding(new Insets(5,5,5,5));
        chooseUserLayout.setVgap(8);
        chooseUserLayout.setHgap(10);
        GridPane.setConstraints(label, 0, 1);
        GridPane.setConstraints(userChooseList, 1, 1);
        GridPane.setConstraints(addAdditionalUserButton, 0, 2);
        GridPane.setConstraints(closeWindowButton, 1, 2);
        chooseUserLayout.getChildren().addAll(label, userChooseList, addAdditionalUserButton, closeWindowButton);

        Scene chooseUserScene = new Scene(chooseUserLayout, 400, 200);
        chooseUserWindow.setScene(chooseUserScene);
        chooseUserWindow.showAndWait();
    }
    static ArrayList<User> getUsersList() { return list; }
}
