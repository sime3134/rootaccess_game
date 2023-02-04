package org.example.state;

import org.example.base.ContentManager;
import org.example.base.Settings;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;

import java.awt.*;

public class GameState extends State {

    private UITextContainer pcContainer;
    private UITextContainer infoContainer;

    public GameState(GameController controller, ContentManager content) {
        super(controller, content);
        test();
    }

    @Override
    public void update() {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        handleInput();
    }

    public void test(){
        pcContainer = new UITextContainer(520, 60, 660, 545, 20, false, true);
        infoContainer = new UITextContainer(130, 180, 270, 310, 20, true, false);

        UIText text1 = new UIText("helicopter", 12, "Joystix Monospace", WordType.SELECTABLE);
        UIText text2 = new UIText("?¤22)&=#)%=¤", 12, "Joystix Monospace", WordType.FILLER);
        UIText text3 = new UIText("tiger", 12, "Joystix Monospace", WordType.SELECTABLE);
        UIText text4 = new UIText("%/%3)&95#¤%7", 12, "Joystix Monospace", WordType.FILLER);
        UIText text5 = new UIText("computer", 12, "Joystix Monospace", WordType.CORRECT);
        UIText text6 = new UIText("!3¤)4&=", 12, "Joystix Monospace", WordType.FILLER);
        UIText text7 = new UIText("terminal", 12, "Joystix Monospace", WordType.SELECTABLE);
        pcContainer.addTexts(text1, text2, text3, text4, text5, text6, text7);

        UIText text8 = new UIText("John McJohnson", 40, "January Shine", WordType.NONE);
        UIText text9 = new UIText("Interests:", 36, "January Shine", WordType.NONE);
        UIText text10 = new UIText("- Computers", 36, "January Shine", WordType.NONE);
        UIText text11 = new UIText("- Cars", 36, "January Shine", WordType.NONE);
        infoContainer.addTexts(text8, text9, text10, text11);

        textContainers.add(pcContainer);
        textContainers.add(infoContainer);
    }

    protected void handleInput() {
        if(controller.requestedLeft()) {
            pcContainer.moveLeft();
        }
        if(controller.requestedRight()) {
            pcContainer.moveRight();
        }
        if(controller.requestedConfirm()) {
            //TODO: check if word is correct
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        g.drawImage(content.getImage("portrait-" + Settings.getScreenHeight()), 82, 55, null);
        for (UITextContainer container : textContainers) {
            container.draw(g);
        }
    }
}
