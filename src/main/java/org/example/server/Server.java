package org.example.server;

import org.example.base.ContentManager;
import org.example.wordgen.TextGenerator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int numberOfConnections = 0;

    private Client player1;
    private Client player2;

    private final TextGenerator textGen;

    public Server(ContentManager content){
        new ConnectionListener(725).start();
        textGen = new TextGenerator(content.getInterests(), content.getOccupations());
        textGen.createText(15, 1100);
    }

    /**
     * Listens for incoming connections starting a new thread for every connecting client.
     */
    private class ConnectionListener extends Thread{
        private final int port;

        public ConnectionListener(int port){
            this.port = port;
        }

        @Override
        public void run(){
            Socket socket;
            try(ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("ChatServer started and listening on port " + serverSocket.getLocalPort());
                while(!Thread.interrupted()) {
                    socket = serverSocket.accept();
                    new ClientHandler(socket).start();
                    numberOfConnections++;
                }
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("An error occurred when a client tried to connect.");
            }
        }

    }

    /**
     * Handles a client connected to the server.
     */
    private class ClientHandler extends Thread {
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;
        private final Client client;

        public ClientHandler(Socket socket) {
            client = new Client(socket);
            oos = client.getOutputStream();
            ois = client.getInputStream();
        }

        @Override
        public void run() {
            try {

                while (!Thread.interrupted()) {
                    handleRequests();
                }

            } catch (IOException se) {
                se.printStackTrace();
            }
        }

        private void handleRequests() throws IOException {
            String request = ois.readUTF();

            switch (request) {
                case "LOGIN" -> sendLists();
                default -> System.out.println("Server: " + "Received unknown request: " + request);
            }
        }
    }

    private void sendLists() {

    }
}
