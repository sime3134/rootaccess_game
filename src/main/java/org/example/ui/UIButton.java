package org.example.ui;

import org.example.base.Vector2D;
import org.example.utils.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIButton {
    private String text;

    private int width;
    private int height;
    private Vector2D position;

    private boolean selected;

    private int fontSize;
    private final int fontStyle;
    private final String fontFamily;
    private Color fontColor;

    private Color backgroundColor;

    private Font font;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public UIButton(String text, Vector2D position, int width, int height){
        this.text = text;
        this.fontSize = 16;
        this.fontStyle = Font.BOLD;
        this.fontFamily = "Joystix Monospace";
        this.fontColor = Color.decode("#78C475");
        this.backgroundColor = Color.GRAY;
        createFont();

        this.position = position;
        this.width = width;
        this.height = height;
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(fontColor);
        graphics.drawString(text, 0, fontSize);

        graphics.dispose();
        return image;
    }

    public void draw(Graphics g){
        g.drawImage(getSprite(),
                position.intX(),
                position.intY(),
                null);
    }

    public void update() {
        if(selected){
            backgroundColor = Color.decode("#78C475");
            fontColor = Color.GRAY;
        } else {
            backgroundColor = Color.GRAY;
            fontColor = Color.decode("#78C475");
        }
    }
}
