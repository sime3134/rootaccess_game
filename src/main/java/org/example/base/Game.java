package org.example.base;

import org.example.ui.UITextContainer;

import java.awt.*;
/**
 * @author Simon Jern
 * The base class for the game.
 */
public class Game {

    private final GameFrame gameFrame;
    private final ContentManager content;

    private UITextContainer container;

    public Game(){
        gameFrame = new GameFrame(this);
        content = new ContentManager();
        content.loadContent();
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
