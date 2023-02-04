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

    private Font font;

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public Font getFont() {
        return font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public UIText(String text, int fontSize, String fontFamily, WordType wordType) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.fontFamily = fontFamily;
        this.fontColor = Color.decode("#78C475");
        this.wordType = wordType;
        createFont();

        this.position = new Vector2D(0, 0);
        this.wordSplits = new Stack<>();
        wordSplits.add(new WordSplit(this, text, font, fontColor, position));
    }

    public void update() {
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public int splitWord(int index, int containerXPos) {
        wordSplits.clear();
        wordSplits.add(new WordSplit(this, text.substring(0, Math.min(text.length(), index)), font, fontColor,
                position));
        wordSplits.add(new WordSplit(this, text.substring(index), font, fontColor,
                new Vector2D(containerXPos + 20, position.getY() + 15)));
        return wordSplits.peek().getWidth();
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

    public int getHeight() {
        return wordSplits.firstElement().getHeight();
    }
}
