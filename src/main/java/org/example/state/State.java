package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Game;
import org.example.keyboard.GameController;
import org.example.ui.UITextContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class State {
    protected final GameController controller;
    protected final ContentManager content;

    protected  final AudioPlayer audioPlayer;

    protected List<UITextContainer> textContainers;

    protected State(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        this.controller = controller;
        this.content = content;
        this.textContainers = new ArrayList<>();
        this.audioPlayer = audioPlayer;
    }

    public abstract void update(Game game);

    public abstract void draw(Graphics g);
}
