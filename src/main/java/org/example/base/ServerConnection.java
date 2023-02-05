package org.example.base;

import org.example.state.GameState;
import org.example.state.MenuState;
import org.example.ui.UIText;
import org.example.utils.Buffer;
import org.example.utils.Request;
import org.example.utils.Timer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerConnection {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private final String ipAddress;

    private final Buffer<Request> requests;

    private final GameState gameState;

    private final MenuState menuState;

    public ServerConnection(String ipAddress, GameState gameState, MenuState menuState, Game game){
        this.gameState = gameState;
        this.menuState = menuState;
        this.ipAddress = ipAddress;
        requests = new Buffer<>();
        connectToServer(game);
    }

    private void connectToServer(Game game) {
        try {
            Socket socket = new Socket(ipAddress, 725);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new ReadFromServer(game).start();
            new WriteRequests().start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong connecting to the server.");
            System.exit(0);
        }

        requests.put(new Request("LOGIN"));
    }

    public void sendCorrect() {
        requests.put(new Request("CORRECT"));
    }

    public void sendIncorrect() {
        requests.put(new Request("INCORRECT", Timer.getSecondsLeft()));
    }

    public void sendStartGame() {
        requests.put(new Request("STARTGAME"));
    }

    private class WriteRequests extends Thread{
        @Override
        public void run(){
            try {
                while (!Thread.interrupted()) {
                    Request request = requests.get();
                    oos.writeUTF(request.getMessage());
                    oos.flush();
                    for(Object obj : request.getObjects()){
                        oos.writeObject(obj);
                        oos.flush();
                    }
                }
            }catch (IOException | InterruptedException e){
                this.interrupt();
            }
        }
    }

    private class ReadFromServer extends Thread {

        private Game game;
        public ReadFromServer(Game game) {
            this.game = game;
        }
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    handleIncoming(game);
                }
            } catch (IOException se) {
                System.out.println("Could not reach the server. The server might be down.");
                se.printStackTrace();
            }
        }

        /**
         * Delegates all incoming data from the server to the correct method or error.
         */
        private void handleIncoming(Game game) throws IOException {
            String message = ois.readUTF();

            switch (message) {
                case "FRIENDCORRECT" -> {
                    game.getAudioPlayer().playSound("Access_Granted.wav", 0);
                    oos.writeUTF("LOGIN");
                    oos.flush();
                }
                case "FRIENDINCORRECT" -> {
                    game.getAudioPlayer().playSound("Access_Denied.wav", 0);
                    int seconds = ois.readInt();
                    System.out.println("Received: " + seconds);
                    Timer.setSecondsLeft(seconds);
                }
                case "STARTGAME" -> {
                    game.setCurrentState("game");
                    game.getTimer().resetTimer();
                }
                case "READY" -> menuState.playersReady();
                case "LIST" -> {
                    String name = ois.readUTF();
                    int id = ois.readInt();
                    String portraitName = ois.readUTF();
                    List<UIText> textList = new ArrayList<>();
                    List<UIText> interestList = new ArrayList<>();
                    int size = ois.readInt();
                    for (int i = 0; i < size; i++) {
                        try {
                            UIText text = (UIText) ois.readObject();
                            textList.add(text);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    int interestSize = ois.readInt();
                    for (int i = 0; i < interestSize; i++) {
                        try {
                            UIText text = (UIText) ois.readObject();
                            interestList.add(text);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    gameState.setTexts(textList);
                    gameState.setName(name);
                    gameState.setListId(id);
                    gameState.setPortraitName(portraitName);
                    System.out.println(id);
                    gameState.setInterests(interestList);
                    gameState.setThereIsNewData(true);
                }
                default -> System.out.println("Unknown message: " + message);
            }
        }
    }
}
