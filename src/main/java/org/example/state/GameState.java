package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Settings;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;
import org.example.wordgen.TextGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameState extends State {
    private UITextContainer pcContainer;
    private UITextContainer infoContainer;

    private List<UIText> texts;

    public GameState(GameController controller, ContentManager content, AudioPlayer audioPlayer) {
        super(controller, content, audioPlayer);
        audioPlayer.playMusic("music_stem_one.wav", 0);
        prepareContainers();
    }


    @Override
    public void update() {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        handleInput();
    }

    public void prepareContainers(){
        pcContainer = new UITextContainer(520, 60, 660, 545, 20, false, true);
        infoContainer = new UITextContainer(130, 180, 270, 310, 20, true, false);

        pcContainer.addTexts(texts);

        List<UIText> infos = new ArrayList<>();
        UIText text8 = new UIText("John McJohnson", 40, "January Shine", WordType.NONE);
        UIText text9 = new UIText("Interests:", 36, "January Shine", WordType.NONE);
        UIText text10 = new UIText("- Computers", 36, "January Shine", WordType.NONE);
        UIText text11 = new UIText("- Cars", 36, "January Shine", WordType.NONE);
        infos.add(text8);
        infos.add(text9);
        infos.add(text10);
        infos.add(text11);
        infoContainer.addTexts(infos);

        textContainers.add(pcContainer);
        textContainers.add(infoContainer);
    }

    protected void handleInput() {
        if(controller.requestedLeft()) {
            pcContainer.moveLeft();
            audioPlayer.playKeySound();
        }
        if(controller.requestedRight()) {
            pcContainer.moveRight();
            audioPlayer.playKeySound();
        }
        if(controller.requestedUp()) {
            pcContainer.moveUp();
            audioPlayer.playKeySound();
        }
        if(controller.requestedDown()) {
            pcContainer.moveDown();
            audioPlayer.playKeySound();
        }
        if(controller.requestedConfirm()) {
            //TODO: check if word is correct
            //audioPlayer.playSound();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        g.drawImage(content.getImage("portrait-" + Settings.getScreenHeight()), 82, 55, null);
        for (UITextContainer container : textContainers) {
            container.draw(g);
        }
    }
}
