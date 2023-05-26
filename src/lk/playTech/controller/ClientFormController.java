package lk.playTech.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public static String username;
    public Label lblUsername;
    public TextField txtMgType;
    public Button btnSend;
    public Button btnLogOut;
    public VBox vb_main;
    public AnchorPane paneClient;
    public TextArea allMgArea;
    private Client client;

    public void btnSendOnAction(ActionEvent actionEvent) {

           // sp_emoji.setVisible(false);
            String message = txtMgType.getText();

            if (!message.isEmpty()) {
                sendMessage(message);
                txtMgType.clear();
                client.clientSendMessage(message);
//            textField.clear();
            }
        }

    public void sendMessage(String message) {

        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #27ae60;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vb_main.getChildren().add(hBox);

    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // setEmojisToPane();

       // sp_emoji.setVisible(false);
       // sp_emoji.setStyle("-fx-background-color: coral;");

        if (username != null){
            lblUsername.setText(username);
        }else {
            System.err.println("username is null");
        }
//        usernameLabel.setText(username);
        try {
            client = new lk.playTech.controller.Client(new Socket("localhost", 9000), username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        paneClient.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             //   sp_main.setVvalue((Double) newValue);
            }
        });
        client.listenForMessage(vb_main);
        client.clientSendMessage("");
    }
    public static void receiveMessage(String message, VBox vBox){
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }



}

