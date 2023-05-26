package lk.playTech.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer(){
        while (!(serverSocket.isClosed())) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("new lk.playTech.controller.Client Connected");
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                closeServer();
            }
        }
    }

    private void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("lk.playTech.controller.Server Closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
