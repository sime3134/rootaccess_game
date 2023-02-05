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

public class MenuState extends State {
    private List<UIButton> buttons;

    private int selectedButton = 0;
    private int lastSelectedButton = 0;

    private UITextContainer infoContainer;

    private UITextContainer connectContainer;

    private String ipAddress;

    public MenuState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
        buttons = new ArrayList<>();
        ipAddress = findIP();
        prepareUI();
    }

    private void prepareUI() {
        UIButton playButton = new UIButton("Play", new Vector2D((Settings.getScreenWidth() / 2) - 50, 200), 100,
                50);
        UIButton quitButton = new UIButton("Quit", new Vector2D((Settings.getScreenWidth() / 2) - 50, 300), 100,
                50);

        buttons.add(playButton);
        buttons.add(quitButton);

        infoContainer = new UITextContainer(680, 180, 270, 310, 20, true, false);

        List<UIText> infos = new ArrayList<>();
        UIText text8 = new UIText("Help each other to find the root password!", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text9 = new UIText("Find the correct word by matching letter", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text10 = new UIText("combinations on each other's screens.", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text11 = new UIText("Look at the individuals interests to the left!", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text12 = new UIText("Move between words with WASD or arrow keys", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text13 = new UIText("and confirm your choice with enter.", 12, "Joystix Monospace",
                WordType.NONE);
        UIText text14 = new UIText("You lose time with every mistake!", 12, "Joystix Monospace",
                WordType.NONE);
        infos.add(text8);
        infos.add(text9);
        infos.add(text10);
        infos.add(text11);
        infos.add(text12);
        infos.add(text13);
        infos.add(text14);
        infoContainer.addTexts(infos);

        textContainers.add(infoContainer);

        connectContainer = new UITextContainer((Settings.getScreenWidth() / 2) - 500, 180, 1000, 310, 20, true,
                false);

        connectContainer.setBackgroundColor(Color.GRAY);

        List<UIText> connectInfo = new ArrayList<>();
            UIText text15 = new UIText("Waiting for other player to connect...", 24, "Joystix Monospace",
                WordType.NONE);
        UIText text16 = new UIText("Your fellow hacker can join locally with this IP address: ", 18, "Joystix " +
                "Monospace",
                WordType.NONE);
        UIText text17 = new UIText(ipAddress, 18, "Joystix " +
                "Monospace",
                WordType.NONE);
        connectInfo.add(text15);
        connectInfo.add(text16);
        connectInfo.add(text17);
        connectContainer.addTexts(connectInfo);

        textContainers.add(connectContainer);
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
                audioPlayer.playSound("Find_the_Root_Pass.wav", 0);
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

    public void playersReady() {
        connectContainer.setVisible(false);
    }
}
