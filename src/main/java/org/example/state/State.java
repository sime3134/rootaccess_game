package org.example.state;

import org.example.base.ContentManager;
import org.example.keyboard.GameController;

import java.awt.*;

public abstract class State {
    protected final GameController controller;

    protected final ContentManager content;

    public State(GameController controller, ContentManager content) {
        this.controller = controller;
        this.content = content;
    }

    public abstract void update();

    public abstract void draw(Graphics g);
}
