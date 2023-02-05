package org.example.base;

import org.example.audio.AudioPlayer;
import org.example.keyboard.GameController;
import org.example.state.GameState;
import org.example.state.State;

import java.awt.*;
/**
 * @author Simon Jern
 * The base class for the game.
 */
public class Game {
    private final GameFrame gameFrame;

    private State currentState;

    private final GameController controller;

    private AudioPlayer audioPlayer;

    private ServerConnection connection;

    public Game(ContentManager content, String ipAddress){
        connection = new ServerConnection(ipAddress);
        gameFrame = new GameFrame(this);
        audioPlayer = new AudioPlayer();
        controller = new GameController();
        currentState = new GameState(controller, content, audioPlayer);
    }

    public void update(){
        currentState.update();
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
