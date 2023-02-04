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

    private Image sprite;

    private Color backgroundColor = Color.RED;
    private Color borderColor = Color.GREEN;

    private int currentLineLength;

    private final int maxLineLength = 30;

    public UITextContainer(int posX, int posY, int sizeX, int sizeY){
        textComponents = new ArrayList<>();
        this.position = new Vector2D(posX,posY);
        this.size = new Vector2D(sizeX,sizeY);
        this.currentLineLength = 0;
    }

    public void addText(UIText... text){
        for (UIText t : text) {
            if(currentLineLength + t.getText().length() > maxLineLength){
                t.setLineBreaker(currentLineLength + t.getText().length() - maxLineLength);
                currentLineLength = currentLineLength + t.getText().length() - maxLineLength;
            }
            t.splitWord();
            textComponents.add(t);
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
