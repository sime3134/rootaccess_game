package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.keyboard.GameController;
import org.example.ui.UITextContainer;

import java.awt.*;

public class MenuState extends State {
    public MenuState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
    }

    @Override
    public void update() {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        handleInput();
    }

    protected void handleInput() {
        if(controller.requestedUp()) {
            System.out.println("up");
        }
        if(controller.requestedDown()) {
            System.out.println("down");
        }
        if(controller.requestedLeft()) {
            System.out.println("left");
        }
        if(controller.requestedRight()) {
            System.out.println("right");
        }
        if(controller.requestedConfirm()) {
            System.out.println("confirm");
        }
    }

    @Override
    public void draw(Graphics g) {
        for (UITextContainer container : textContainers) {
            container.draw(g);
        }
    }
}
