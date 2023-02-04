package org.example.ui;

import org.example.base.Vector2D;
import org.example.utils.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WordSplit {

    private UIText uiText;

    private String text;

    private int width;
    private int height;

    private Vector2D position;

    private Font font;

    private Color fontColor;

    public WordSplit(UIText uiText, String text, Font font, Color fontColor, Vector2D position) {
        this.uiText = uiText;
        this.text = text;
        this.font = font;
        this.fontColor = fontColor;
        this.position = position;
        calculateSize();
    }

    private void calculateSize() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        FontMetrics fm = g2d.getFontMetrics(font);
        width = fm.stringWidth(text);
        height = fm.getHeight();
        g2d.dispose();
    }

    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        graphics.setColor(fontColor);
        graphics.drawString(text, 0, font.getSize());

        graphics.dispose();
        return image;
    }

    public int getWidth() {
        return width;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }
}
