import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.playTech.controller.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerInitializer {

    public static void main(String[] args) {


        try {
            System.out.println("lk.playTech.controller.Server is start");
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("lk.playTech.controller.Server is Running");
            Server server = new Server(serverSocket);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

