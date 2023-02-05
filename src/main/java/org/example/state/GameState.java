package org.example.state;

import org.example.audio.AudioPlayer;
import org.example.base.ContentManager;
import org.example.base.Game;
import org.example.base.Settings;
import org.example.enums.WordType;
import org.example.keyboard.GameController;
import org.example.ui.UIText;
import org.example.ui.UITextContainer;
import org.example.utils.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GameState extends State {
    private UITextContainer pcContainer;
    private UITextContainer infoContainer;

    private UITextContainer timerContainer;
    private List<UIText> texts;

    private List<UIText> personaList;

    private final Game game;

    private int currentLevel = 1;
    private float timeMillis;

    private boolean thereIsNewData;

    private int listId;

    private String portraitName;

    private UIText timerText;

    private Timer timer;

    public GameState(GameController controller, ContentManager content, AudioPlayer audioPlayer, Game game) {
        super(controller, content, audioPlayer);
        this.game = game;
        timeMillis = System.currentTimeMillis();
        personaList = new ArrayList<>();
        thereIsNewData = false;
        listId = -1;
        timer = new Timer();
        prepareContainers();
    }


    @Override
    public void update(Game game) {
        timerText.setText(timer.getSecondsLeft());

        for (UITextContainer container : textContainers) {
            container.update();
        }
        handleInput();
        if(thereIsNewData){
            pcContainer.clear();
            infoContainer.clear();
            // Update font size and spacing
            pcContainer.updateFontSize();
            pcContainer.addTexts(texts);
            infoContainer.addTexts(personaList);
            thereIsNewData = false;
        }
    }

    public void prepareContainers(){
        pcContainer = new UITextContainer(520, 60, 660, 500, 20, false, true);
        infoContainer = new UITextContainer(130, 180, 270, 310, 20, true, false);
        timerContainer = new UITextContainer(770, 550, 270, 100, 20, false, false);

        UIText timerDescText = new UIText("Lockdown imminent:  ", WordType.NONE, 18);
        timerText = new UIText("40", WordType.NONE, 18);

        List<UIText> timerTexts = new ArrayList<>();
        timerTexts.add(timerDescText);
        timerTexts.add(timerText);
        timerContainer.addTexts(timerTexts);

        textContainers.add(timerContainer);
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
                //audioPlayer.playSound("Access_Granted.wav", 0);
                game.getConnection().sendCorrect();
                this.progressLevel();
            }else{
                //audioPlayer.playSound("Wrong_Answer.wav", 0);
                game.getConnection().sendIncorrect();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(content.getImage("bg-" + Settings.getScreenHeight()), 0, 0, null);
        g.drawImage(content.getImage(portraitName + "-" + Settings.getScreenHeight()), 82, 55, null);
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

    public void setPortraitName(String name){
        this.portraitName = name;
    }

    public void setName(String name) {
        personaList.clear();
        UIText nameText = new UIText(name, 24, "BAD", WordType.NONE);
        UIText interests = new UIText("Interests:", 24, "BAD", WordType.NONE);
        personaList.add(nameText);
        personaList.add(interests);
    }

    public void setThereIsNewData(boolean thereIsNewData) {
        this.thereIsNewData = thereIsNewData;
    }

    public void setListId(int id) {
        this.listId = id;
    }

    private void progressLevel() {
        currentLevel++;
        if (currentLevel < 6) {
            String musicName = "Music_Stem-" + currentLevel + ".wav";
            audioPlayer.playMusic(musicName, (long) (System.currentTimeMillis() - timeMillis));
        }
    }
}
