package controllers;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class LibraryController implements Initializable {

    public Button btnNewWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void handleBtnNewWindow() {
        try {
            URL url = new File("src/main/resources/CreateUser.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
//            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/main/resources/CreateUser.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Atlas library");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
