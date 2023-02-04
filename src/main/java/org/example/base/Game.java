package org.example.base;

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

    private UITextContainer container;

    public Game(){
        gameFrame = new GameFrame(this);
        content = new ContentManager();
        textGen = new TextGenerator();
        content.loadContent();
        textGen.createText(content.getIntestests());
        test();
    }

    private void test() {
        container = new UITextContainer(520, 60, 660, 545);
    }

    public void update(){

    }

    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        container.draw(g);
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
