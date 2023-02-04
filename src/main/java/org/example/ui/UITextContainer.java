package org.example.ui;

import org.example.base.Vector2D;
import org.example.utils.ImgUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UITextContainer {
    private List<UIText> textComponents;

    private Vector2D position;

    private Vector2D size;

    private int padding;

    private Image sprite;

    private Color backgroundColor = new Color(0,0,0,0);
    private Color borderColor = new Color(0,0,0,0);

    private int currentLineLength;

    private final int maxLineLength = 60;

    private int nextWordXPos;

    private int nextWordYPos;

    private final boolean vertical;

    public UITextContainer(int posX, int posY, int sizeX, int sizeY, int padding, boolean vertical){
        textComponents = new ArrayList<>();
        this.position = new Vector2D(posX,posY);
        this.size = new Vector2D(sizeX,sizeY);
        this.currentLineLength = 0;
        this.nextWordXPos = 0;
        this.nextWordYPos = 0;
        this.padding = padding;
        this.vertical = vertical;
    }

    public void addTexts(UIText... text){
        if(!vertical) {
            for (UIText t : text) {
                t.setPosition(position.intX() + nextWordXPos + padding, position.intY() + nextWordYPos + padding);

                if (currentLineLength + t.getText().length() > maxLineLength) {
                    int newLineWidth = t.splitWord(currentLineLength + t.getText().length() - maxLineLength, position.intX());
                    currentLineLength = currentLineLength + t.getText().length() - maxLineLength;
                    nextWordXPos = position.intX() + 20 + newLineWidth;
                    nextWordYPos += t.getHeight() + 10;
                } else {
                    nextWordXPos += t.getWidthOfFirstSplit();
                    currentLineLength += t.getText().length();
                }

                textComponents.add(t);
            }
        } else {
            for (UIText t : text) {
                t.setPosition(position.intX() + nextWordXPos + padding, position.intY() + nextWordYPos + padding);

                nextWordYPos += t.getHeight() + 10;

                textComponents.add(t);
            }
        }
    }

    public Image getSprite() {
        sprite = ImgUtils.createCompatibleImage(size.intX(), size.intY(), ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.intX(), size.intY());
        graphics.setColor(borderColor);
        graphics.setStroke(new BasicStroke(3));
        graphics.drawRect(0, 0, size.intX(), size.intY());

        graphics.dispose();

        return sprite;
    }

    public void draw(Graphics g){
        g.drawImage(
                getSprite(),
                position.intX(),
                position.intY(),
                null
        );

        for (UIText text : textComponents) {
            text.draw(g);
        }
    }
}
