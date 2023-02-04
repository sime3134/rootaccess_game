package org.example.ui;

import org.example.base.Vector2D;
import org.example.enums.WordType;

import java.awt.*;
import java.util.Stack;

/**
 * @author Simon Jern
 * Implements a UI component that displays text.
 */
public class UIText {
    private String text;

    private Stack<WordSplit> wordSplits;

    private WordType wordType;

    private Vector2D position;

    private boolean selected;

    private int fontSize;
    private final int fontStyle;
    private final String fontFamily;
    private Color fontColor;

    private Color backgroundColor;

    private Font font;

    private int timer;

    private boolean blink;

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public int getX() {return position.intX();}
    public int getY() {return position.intY();}

    public WordType getWordType() {
        return wordType;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public UIText(String text, WordType wordType){
        this(text, 12, "Joystix Monospace", wordType);
    }

    public UIText(String text, int fontSize, String fontFamily, WordType wordType) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.fontFamily = fontFamily;
        this.fontColor = Color.decode("#78C475");
        this.backgroundColor = new Color(255, 255, 255, 0);
        this.wordType = wordType;
        createFont();

        this.position = new Vector2D(0, 0);
        this.wordSplits = new Stack<>();
        wordSplits.add(new WordSplit(text, position, this));
        this.timer = 0;
        this.blink = true;
    }

    public void update() {
        timer++;

        if(timer > 35){
            timer = 0;
            blink = !blink;
        }

        if(selected && blink) {
            fontColor = Color.decode("#000000");
            backgroundColor = Color.decode("#54DB4F");
        } else {
            fontColor = Color.decode("#78C475");
            backgroundColor = new Color(255, 255, 255, 0);
        }
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Vector2D splitWord(int index, int startPosX, int lastPosY) {
        Vector2D positionOfWordOnNextRow = new Vector2D(startPosX, lastPosY + getHeight() + 10);
        wordSplits.clear();
        wordSplits.add(new WordSplit(text.substring(0, index), position, this));
        wordSplits.add(new WordSplit(text.substring(index), positionOfWordOnNextRow, this));
        return new Vector2D(positionOfWordOnNextRow.intX() + wordSplits.peek().getWidth(),
                positionOfWordOnNextRow.intY());
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    public void draw(Graphics g) {
        for(WordSplit wordSplit : wordSplits) {
            g.drawImage(wordSplit.getSprite(),
                    wordSplit.getPosition().intX(),
                    wordSplit.getPosition().intY(),
                    null);
        }
    }

    public int getWidthOfFirstSplit() {
        return wordSplits.firstElement().getWidth();
    }

    public int getLengthOfLastSplit(){
        return wordSplits.lastElement().getText().length();
    }

    public int getHeight() {
        return wordSplits.firstElement().getHeight();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected){
            blink = true;
        }
        timer = 0;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getFontColor() {
        return fontColor;
    }
}
