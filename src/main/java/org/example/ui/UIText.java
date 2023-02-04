package org.example.ui;

import org.example.utils.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * Implements a UI component that displays text.
 */
public class UIText {
    private String text;

    private WordSplit[] wordSplits = new WordSplit[2];

    private boolean selectable;

    private boolean selected;

    private boolean correct;

    private int lineBreaker;

    private int fontSize;
    private final int fontStyle;
    private final String fontFamily;
    private Color fontColor;

    private Font font;

    public void setLineBreaker(int lineBreaker) {
        this.lineBreaker = lineBreaker;
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

    public UIText(String text, int fontSize, String fontFamily) {
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.fontFamily = fontFamily;
        this.fontColor = Color.decode("#78C475");
        this.selectable = false;
        this.lineBreaker = 0;

        createFont();
    }

    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(10, 10,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        String[] lines = text.split("\n");
        for(int i = 0; i < lines.length; i++){

            int yPos = (lines.length > 1 && i != 0 ?
                    10 : 0) * i;

            graphics.setColor(fontColor);
            graphics.drawString(lines[i], 5,
                    (i + 1) * fontSize );
        }

        graphics.dispose();
        return image;
    }

    public void update() {
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void splitWord() {
        if(lineBreaker != 0) {
            wordSplits[0] = text.substring(0, Math.min(text.length(), lineBreaker));
            wordSplits[1] = text.substring(lineBreaker));
        }else{
            line1 = text;
            line2 = null;
        }
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    public void draw(Graphics g) {
        g.drawImage(getSprite(),
                5,
                5,
                null);
    }
}
