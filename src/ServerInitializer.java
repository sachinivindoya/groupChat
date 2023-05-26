import javafx.application.Application;
import javafx.stage.Stage;
import lk.playTech.server.Server;


import java.io.IOException;
import java.net.ServerSocket;

public class ServerInitializer {

    public static void main(String[] args) {

        try {
            System.out.println("Server is start");
            ServerSocket serverSocket = new ServerSocket(10000);
            System.out.println("Server is Running");
            Server server = new Server(serverSocket);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}