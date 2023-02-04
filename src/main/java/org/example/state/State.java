package org.example.state;

import org.example.base.ContentManager;
import org.example.keyboard.GameController;
import org.example.ui.UITextContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class State {
    protected final GameController controller;
    protected final ContentManager content;

    protected List<UITextContainer> textContainers;

    protected State(GameController controller, ContentManager content) {
        this.controller = controller;
        this.content = content;
        this.textContainers = new ArrayList<>();
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    protected abstract void handleInput();
}
