package lk.playTech.controller;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ServerFormController {
    public Text btnCloseServer;
    public TextField txtServerStatus;
    public TextField txtServerRun;
    public ImageView paneServer;

    public void initialize(){
    txtServerStatus.setText("SERVER IS STARTED");
    txtServerRun.setText("SERVER IS RUNNING");

    }

    public void btnCloseServerOnAction(MouseEvent mouseEvent) {

    }
}
