package org.example.base;

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
    private final TextGenerator textGen;

    private State currentState;

    private final GameController controller;

    public Game(){
        gameFrame = new GameFrame(this);
        content = new ContentManager();
        textGen = new TextGenerator();
        content.loadContent();
        textGen.createText(content.getIntestests());
        controller = new GameController();
        currentState = new GameState(controller, content);
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
