package org.example.base;

import org.example.audio.AudioPlayer;
import org.example.keyboard.GameController;
import org.example.state.GameOverState;
import org.example.state.GameState;
import org.example.state.MenuState;
import org.example.state.State;
import org.example.utils.Timer;

import java.awt.*;
/**
 * @author Simon Jern
 * The base class for the game.
 */
public class Game {
    private final GameFrame gameFrame;

    private State currentState;

    private final GameController controller;

    private AudioPlayer audioPlayer;

    private ServerConnection connection;

    private MenuState menuState;

    private GameState gameState;

    private ContentManager content;

    private Timer timer;

    public Game(ContentManager content, String ipAddress){
        gameFrame = new GameFrame(this);
        this.content = content;
        audioPlayer = new AudioPlayer();
        controller = new GameController();
        gameState = new GameState(controller, content, audioPlayer, this);
        menuState = new MenuState(controller, content, audioPlayer);
        currentState = menuState;
        connection = new ServerConnection(ipAddress, gameState, menuState, this);
        timer = new Timer();
    }

    public void update(){
        currentState.update(this);
        if(Timer.getSecondsLeft() < 0){
            setCurrentState("gameover");
        }
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public void setCurrentState(String state) {
        switch (state) {
            case "game" -> {
                currentState = gameState;
                audioPlayer.playMusic("Music_Stem-1.wav", 0);
                audioPlayer.playSound("Find_the_Root_Pass.wav", 0);
            }
            case "menu" -> {
                currentState = menuState;
                audioPlayer.playMusic("Menu-Music.wav", 0);
            }
            case "gameover" -> {
                currentState = new GameOverState(controller, content, audioPlayer);
                audioPlayer.playSound("Access_denied.wav", 0);
            }
        }
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Timer getTimer() {
        return timer;
    }
}
