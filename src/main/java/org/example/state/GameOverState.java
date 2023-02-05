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

    private UITextContainer infoContainer;

    private UITextContainer connectContainer;

    private String ipAddress;

    public GameOverState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
        buttons = new ArrayList<>();
        prepareUI();
        audioPlayer.playSound("Access_Denied.wav", 0);
    }

    private void prepareUI() {
        UIButton playButton = new UIButton("Play Again", new Vector2D((Settings.getScreenWidth() / 2) - 50, 200), 100,
                50);
        UIButton quitButton = new UIButton("Quit", new Vector2D((Settings.getScreenWidth() / 2) - 50, 300), 100,
                50);

        buttons.add(playButton);
        buttons.add(quitButton);

        infoContainer = new UITextContainer(680, 180, 270, 310, 20, true, false);

        List<UIText> infos = new ArrayList<>();
        UIText text8 = new UIText("GAME OVER", 32, "Joystix Monospace",
                WordType.NONE);
        UIText text9 = new UIText("Play again?", 24, "Joystix Monospace",
                WordType.NONE);
        infoContainer.addTexts(infos);

        textContainers.add(infoContainer);

    }

    @Override
    public void update(Game game) {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        for (UIButton button : buttons) {
            button.update();
        }
        if(!connectContainer.isVisible()) {
            handleInput(game);
        }
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
                audioPlayer.playSound("UI-confirm_choice.wav", 0);
                game.setCurrentState("game");
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

    public String findIP(){
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Couldn't find IP address. Open 'cmd' and type 'ipconfig'.";
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
