package lk.playTech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane paneLogin;
    public TextField txtLogin;
    public Button btnLogin;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        if (!txtLogin.getText().isEmpty()) {
            ClientFormController.username = txtLogin.getText();
            Stage stage = (Stage) paneLogin.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
                stage.setMaximized(true);
                stage.setResizable(true);
                stage.setTitle(txtLogin.getText() + " | Play Tech Chat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter your Name !..").showAndWait();
        }
    }
}

