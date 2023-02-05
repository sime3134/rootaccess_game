package org.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A client connected to a server. Saves the connection for later use.
 */
public class Client {
    private final Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ObjectOutputStream getOutputStream() {
        return oos;
    }
    public ObjectInputStream getInputStream() {
        return ois;
    }

    public Client(Socket socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        if(socket != null) {
            try {
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
