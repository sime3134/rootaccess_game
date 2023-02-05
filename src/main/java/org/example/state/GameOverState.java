package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Game;
import org.example.base.Settings;
import org.example.base.Vector2D;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.ui.UIButton;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;

import java.awt.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameOverState extends State {
    private List<UIButton> buttons;

    private int selectedButton = 0;
    private int lastSelectedButton = 0;

    public GameOverState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
        buttons = new ArrayList<>();
        prepareUI();
    }

    private void prepareUI() {
        UIButton playButton = new UIButton("Play Again", new Vector2D((Settings.getScreenWidth() / 2) - 50, 200), 100,
                50);
        UIButton quitButton = new UIButton("Quit", new Vector2D((Settings.getScreenWidth() / 2) - 50, 300), 100,
                50);

        buttons.add(playButton);
        buttons.add(quitButton);

    }

    @Override
    public void update(Game game) {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        for (UIButton button : buttons) {
            button.update();
        }
        handleInput(game);
    }

    protected void handleInput(Game game) {
        lastSelectedButton = selectedButton;
        if(controller.requestedUp()) {
            if(selectedButton > 0) {
                audioPlayer.playSound("UI-1.wav", 0);
                selectedButton--;
            }
        }
        if(controller.requestedDown()) {
            if(selectedButton < buttons.size() - 1) {
                audioPlayer.playSound("UI-2.wav", 0);
                selectedButton++;
            }
        }
        if(controller.requestedConfirm()) {
            if(selectedButton == 0) {
                // Play
                game.getConnection().sendStartGame();
                game.setCurrentState("game");
                game.getTimer().resetTimer();
            } else if(selectedButton == 1) {
                // Quit
                audioPlayer.playSound("UI-confirm_choice.wav", 0);
                System.exit(0);
            }
        }
        buttons.get(selectedButton).setSelected(true);
        if(lastSelectedButton != selectedButton) {
            buttons.get(lastSelectedButton).setSelected(false);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        for(UIButton button : buttons) {
            button.draw(g);
        }
        for (UITextContainer container : textContainers) {
            container.draw(g);
        }
    }

}
