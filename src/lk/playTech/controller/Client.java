package lk.playTech.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket,String username){
        try{
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedWriter, bufferedReader);
        }


    }

    public void clientSendMessage(String message) {
        try {
            bufferedWriter.write(username + " : " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void listenForMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String messageFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        String message = bufferedReader.readLine();
                        ClientFormController.receiveMessage(message, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if (bufferedReader != null ){
                bufferedReader.close();
            }

            if (bufferedWriter != null ){
                bufferedWriter.close();
            }

            if (socket != null ){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
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




