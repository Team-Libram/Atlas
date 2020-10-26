package controllers;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.awt.event.*;
import java.util.*;

public class CreateUserController implements Initializable {

    @FXML
    private ComboBox<String> comb;

    @FXML
    void Select(ActionEvent event) {
        String s = comb.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Operator", "Reader");
        comb.setItems(list);
    }
}

