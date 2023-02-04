package org.example.base;

import org.example.audio.AudioPlayer;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.state.GameState;
import org.example.state.MenuState;
import org.example.state.State;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;
import org.example.wordgen.TextGenerator;

import java.awt.*;
/**
 * @author Simon Jern
 * The base class for the game.
 */
public class Game {

    private final GameFrame gameFrame;
    private final ContentManager content;

    private State currentState;

    private final GameController controller;

    private AudioPlayer audioPlayer;

    public Game(){
        gameFrame = new GameFrame(this);
        content = new ContentManager();
        content.loadContent();
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
