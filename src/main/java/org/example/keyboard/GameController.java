package org.example.keyboard;

import java.awt.event.KeyEvent;

public class GameController {

    private static GameController gameController;

    private final Input input = Input.getInstance();

    public static GameController getInstance() {
        if(gameController == null){
            gameController = new GameController();
        }
        return gameController;
    }

    public boolean requestedUp() {
        return input.isKeyPressed(KeyEvent.VK_W) || input.isKeyPressed(KeyEvent.VK_UP);
    }

    public boolean requestedDown() {
        return input.isKeyPressed(KeyEvent.VK_S) || input.isKeyPressed(KeyEvent.VK_DOWN);
    }

    public boolean requestedLeft() {
        return input.isKeyPressed(KeyEvent.VK_A) || input.isKeyPressed(KeyEvent.VK_LEFT);
    }

    public boolean requestedRight() {
        return input.isKeyPressed(KeyEvent.VK_D) || input.isKeyPressed(KeyEvent.VK_RIGHT);
    }

    public boolean requestedConfirm() {
        return input.isKeyPressed(KeyEvent.VK_ENTER);
    }
}
