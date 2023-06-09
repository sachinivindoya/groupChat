package lk.playTech.controller;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.playTech.client.Client;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public AnchorPane paneClient;
    public TextField txtMgType;
    public ImageView send;
    public ImageView btnEmoji;
    public ImageView btnImage;
    public ImageView btnFiles;
    public Label lblUsername;
    public static String username;
    public ScrollPane sp_main;
    public VBox vb_main;
    public FlowPane as;
    public ScrollPane sp_emoji;
    public GridPane gp_emoji;
    private Client client;
    BufferedReader bufferedReader;
    PrintWriter writer;
    Socket socket;
    private FileChooser fileChooser;
    private File filePath;

    int[] emojis = {
            0x1F606,
            0x1F601,
            0x1F602,
            0x1F609,
            0x1F618,
            0x1F610,
            0x1F914,
            0x1F642,
            0x1F635,
            0x1F696,
            0x1F636,
            0x1F980,
            0x1F625,
            0x1F634,
            0x1F641,
            0x1F643,
    };


    public static void receiveMessage(String message, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #6b3c8f;-fx-background-radius:15;-fx-font-size: 14;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

    public void sendMessage(String message) {

        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:  #d6c94f;-fx-background-radius:15;-fx-font-size: 14;-fx-font-weight: normal;-fx-text-fill: black;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vb_main.getChildren().add(hBox);

    }


    public void sendOnAction(MouseEvent mouseEvent) {
        sp_emoji.setVisible(false);
        String message = txtMgType.getText();

        if (!message.isEmpty()) {
            sendMessage(message);
            txtMgType.clear();
            client.clientSendMessage(message);

        }
    }

    public void btnEmojiOnAction(MouseEvent mouseEvent) {
        if (sp_emoji.isVisible()) {
            sp_emoji.setVisible(false);
        } else {
            sp_emoji.setVisible(true);
            Text text = new Text(new String(Character.toChars(emojis[4])));
            text.setStyle("-fx-font-size: 25px; -fx-font-family: 'Noto Emoji';");
            gp_emoji.add(text, 0, 0);
        }
    }

    private void setEmojisToPane() {
        int EMOJI_INDEX = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                Label text = new Label(new String(Character.toChars(emojis[EMOJI_INDEX++])));
                text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        txtMgType.appendText(text.getText());
                    }
                });
                text.setStyle("-fx-font-size: 25px;" +
                        " -fx-font-family: 'Noto Emoji';" +
                        "-fx-text-alignment: center;");
                gp_emoji.add(text, i, j);
            }
        }
    }

    public void btnImageOnAction(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image to send.");
        System.out.println("choose file");
        File file = fileChooser.showOpenDialog(new Stage());


    }


    public void btnFilesOnAction(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image to send.");
        File file = fileChooser.showOpenDialog(new Stage());


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         setEmojisToPane();

        sp_emoji.setVisible(false);
        sp_emoji.setStyle("-fx-background-color: coral;");

        if (username != null){
            lblUsername.setText(username);
        }else {
            System.err.println("username is null");
        }
//        usernameLabel.setText(username);
        try {
            client = new Client(new Socket("localhost", 10000), username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        paneClient.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });
        client.listenForMessage(vb_main);
        client.clientSendMessage("");
    }

}
