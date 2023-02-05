package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implements a request in the client. Is used to store a message together with any objects to send.
 */
public class Request {
    String message;
    ArrayList<Object> objectsToSend;

    public Request(String message){
        this.message = message;
        objectsToSend = new ArrayList<>();
    }

    public Request(String message, Object... objects){
        this.message = message;
        objectsToSend = new ArrayList<>();

        objectsToSend.addAll(Arrays.asList(objects));
    }

    public void addObjectToSend(Object object){
        objectsToSend.add(object);
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Object> getObjects() {
        return objectsToSend;
    }
}
