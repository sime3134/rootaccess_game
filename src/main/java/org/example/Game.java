package org.example;

import java.awt.*;

public class Game {

    private GameFrame gameFrame;

    public Game(){
        gameFrame = new GameFrame(this);
    }

    public void update(){

    }

    public void draw(Graphics g) {

    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
