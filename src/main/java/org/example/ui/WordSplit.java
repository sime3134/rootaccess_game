package org.example.ui;

import org.example.base.Vector2D;
import org.example.utils.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class WordSplit implements Serializable {

    private String text;

    private int width;
    private int height;

    private Vector2D position;

    private UIText uiText;

    public WordSplit(String text, Vector2D position, UIText uiText) {
        this.text = text;
        this.position = position;
        this.uiText = uiText;
        calculateSize();
    }

    private void calculateSize() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        FontMetrics fm = g2d.getFontMetrics(uiText.getFont());
        width = fm.stringWidth(text);
        height = fm.getHeight();

        g2d.dispose();
    }

    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(uiText.getFont());

        graphics.setColor(uiText.getBackgroundColor());
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(uiText.getFontColor());
        graphics.drawString(text, 0, uiText.getFont().getSize());

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

    public String getText() {
        return text;
    }
}
