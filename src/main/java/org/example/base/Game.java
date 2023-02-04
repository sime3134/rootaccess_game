package org.example.base;

import org.example.ui.UIText;
import org.example.ui.UITextContainer;

import java.awt.*;
/**
 * @author Simon Jern
 * The base class for the game.
 */
public class Game {

    private final GameFrame gameFrame;
    private final ContentManager content;

    private UITextContainer pcContainer;
    private UITextContainer infoContainer;

    public Game(){
        gameFrame = new GameFrame(this);
        content = new ContentManager();
        content.loadContent();
        test();
    }

    private void test() {
        pcContainer = new UITextContainer(520, 60, 660, 545, 20, false);
        infoContainer = new UITextContainer(130, 180, 270, 310, 20, true);


        UIText text1 = new UIText("helicopter", 12, "Joystix Monospace");
        UIText text2 = new UIText("tiger", 12, "Joystix Monospace");
        UIText text3 = new UIText("computer", 12, "Joystix Monospace");
        UIText text4 = new UIText("terminal", 12, "Joystix Monospace");
        pcContainer.addTexts(text1, text2, text3, text4);

        UIText text5 = new UIText("John McJohnson", 40, "January Shine");
        UIText text6 = new UIText("Interests:", 36, "January Shine");
        UIText text7 = new UIText("- Computers", 36, "January Shine");
        UIText text8 = new UIText("- Cars", 36, "January Shine");
        infoContainer.addTexts(text5, text6, text7, text8);
    }

    public void update(){

    }

    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        pcContainer.draw(g);
        infoContainer.draw(g);
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
