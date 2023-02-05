package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Game;
import org.example.base.Settings;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameState extends State {
    private UITextContainer pcContainer;
    private UITextContainer infoContainer;
    private List<UIText> texts;

    private List<UIText> personaList;

    private Game game;

    private boolean thereIsNewData;

    private int listId;

    public GameState(GameController controller, ContentManager content, AudioPlayer audioPlayer, Game game) {
        super(controller, content, audioPlayer);
        this.game = game;
        audioPlayer.playMusic("Music-Stem-1.wav", 0);
        personaList = new ArrayList<>();
        thereIsNewData = false;
        listId = -1;
        prepareContainers();
    }


    @Override
    public void update(Game game) {
        for (UITextContainer container : textContainers) {
            container.update();
        }
        handleInput();
        if(thereIsNewData){
            pcContainer.clear();
            infoContainer.clear();
            pcContainer.addTexts(texts);
            infoContainer.addTexts(personaList);
            thereIsNewData = false;
        }
    }

    public void prepareContainers(){
        pcContainer = new UITextContainer(520, 60, 660, 545, 20, false, true);
        infoContainer = new UITextContainer(130, 180, 270, 310, 20, true, false);

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
            if(pcContainer.getSelectedText().getWordType() == WordType.CORRECT) {
                audioPlayer.playSound("Access_Granted.wav", 0);
                game.getConnection().sendCorrect();
            }else{
                audioPlayer.playSound("Access_Denied.wav", 0);
                game.getConnection().sendIncorrect();
            }
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

    public void setTexts(List<UIText> texts) {
        this.texts = texts;
        System.out.println("Received list");
    }

    public void setInterests(List<UIText> interestList) {
        personaList.addAll(interestList);
    }

    public void setName(String name) {
        personaList.clear();
        UIText nameText = new UIText(name, 40, "January Shine", WordType.NONE);
        UIText interests = new UIText("Interests:", 36, "January Shine", WordType.NONE);
        personaList.add(nameText);
        personaList.add(interests);
    }

    public void setThereIsNewData(boolean thereIsNewData) {
        this.thereIsNewData = thereIsNewData;
    }

    public void setListId(int id) {
        this.listId = id;
    }
}
