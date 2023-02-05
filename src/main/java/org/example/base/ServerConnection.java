package org.example.base;

import org.example.utils.Buffer;
import org.example.utils.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private final String ipAddress;

    private final Buffer<Request> requests;

    public ServerConnection(String ipAddress){
        this.ipAddress = ipAddress;
        requests = new Buffer<>();
        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(ipAddress, 725);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new ReadFromServer().start();
            new WriteRequests().start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong connecting to the server.");
        }

        requests.put(new Request("LOGIN"));
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
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    handleIncoming();
                }
            } catch (IOException se) {
                System.out.println("Could not reach the server. The server might be down.");
                se.printStackTrace();
            }
        }

        /**
         * Delegates all incoming data from the server to the correct method or error.
         */
        private void handleIncoming() throws IOException {
            String message = ois.readUTF();

            switch (message) {
                default -> System.out.println("Unknown message: " + message);
            }
        }
    }
}
