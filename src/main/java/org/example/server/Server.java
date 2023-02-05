package org.example.server;

import com.github.javafaker.Faker;
import org.example.base.ContentManager;
import org.example.ui.UIText;
import org.example.wordgen.TextGenerator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

public class Server {

    private ClientHandler player1;
    private ClientHandler player2;

    private int numberOfConnections = 0;

    private final TextGenerator textGen;

    private int fontSize = 28;

    private Faker faker = new Faker();

    private int spacing;
    private int numberOfWords;

    private String portraitName;

    private

    String name;

    public Server(ContentManager content){
        new ConnectionListener(725).start();
        spacing = 6;
        numberOfWords = 100;
        textGen = new TextGenerator(content.getInterests(), content.getOccupations());
        textGen.createText(spacing, numberOfWords, fontSize);
        name = faker.name().fullName();
        while(name.length() > 15) {
            name = faker.name().fullName();
        }
        portraitName = getRandomPortraitName();
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
                System.out.println("GameServer started and listening on port " + serverSocket.getLocalPort());
                while(!Thread.interrupted()) {
                    socket = serverSocket.accept();
                    if (numberOfConnections == 0) {
                        player1 = new ClientHandler(socket, null, 0);
                        player1.start();
                    }else if (numberOfConnections == 1){
                        player2 = new ClientHandler(socket, player1, 1);
                        player1.friendOos = player2.oos;
                        player2.start();
                    }
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

        private ObjectOutputStream friendOos;

        private int id;

        public ClientHandler(Socket socket, ClientHandler friend, int id) {
            this.id = id;
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                if (friend != null) {
                    friendOos = friend.oos;
                    friendOos.writeUTF("READY");
                    friendOos.flush();
                    oos.writeUTF("READY");
                    oos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not create ClientHandler");
            }
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
                case "STARTGAME" -> {
                    friendOos.writeUTF("STARTGAME");
                    friendOos.flush();
                }
                case "CORRECT" -> {
                    createAndSendNewLists();
                    friendOos.writeUTF("FRIENDCORRECT");
                    friendOos.flush();
                }
                case "INCORRECT" -> {
                    Object temp = null;
                    int time = 0;
                    try {
                        temp= ois.readObject();
                        time = (int) temp;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    friendOos.writeUTF("FRIENDINCORRECT");
                    friendOos.flush();
                    friendOos.writeInt(time);
                    friendOos.flush();
                }
                default -> System.out.println("Server: " + "Received unknown request: " + request);
            }
        }

        private void createAndSendNewLists() {
            if(numberOfWords <= 1000){
                numberOfWords += 20;
            }
            // Do --spacing for lower spacing
            fontSize = fontSize - 1;
            textGen.createText(spacing, numberOfWords, fontSize);
            name = faker.name().fullName();
            while(name.length() > 15) {
                name = faker.name().fullName();
            }
            portraitName = getRandomPortraitName();
            sendLists();
        }

        private void sendLists() {
            try {
                oos.writeUTF("LIST");
                oos.flush();
                oos.writeUTF(name);
                oos.flush();
                oos.writeInt(textGen.getId());
                oos.flush();
                oos.writeUTF(portraitName);
                oos.flush();
                if(id == 0) {
                    oos.writeInt(textGen.getText1().size());
                    for(UIText text : textGen.getText1()) {
                        oos.writeObject(text);
                        oos.flush();
                    }
                }else{
                    oos.writeInt(textGen.getText2().size());
                    for(UIText text : textGen.getText2()) {
                        oos.writeObject(text);
                        oos.flush();
                    }
                }
                oos.writeInt(textGen.getInterestsText().size());
                for(UIText text : textGen.getInterestsText()) {
                    oos.writeObject(text);
                    oos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRandomPortraitName() {
        SecureRandom random = new SecureRandom();
        int randomInt = random.nextInt(1, 6);
        return switch(randomInt) {
            case 1 -> "portrait1";
            case 2 -> "portrait2";
            case 3 -> "portrait3";
            case 4 -> "portrait4";
            case 5 -> "portrait5";
            default -> "portrait6";
        };
    }
}
