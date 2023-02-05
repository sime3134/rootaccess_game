package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Game;
import org.example.keyboard.GameController;

import java.awt.*;

public class EmptyState extends State{
    public EmptyState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void draw(Graphics g) {

    }
}
