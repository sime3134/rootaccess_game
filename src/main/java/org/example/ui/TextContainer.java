package org.example.ui;

import org.example.base.Vector2D;
import org.example.utils.ImgUtils;
import org.example.words.Word;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextContainer {
    private List<Word> words;

    private Vector2D position;

    private Vector2D size;

    private Image sprite;

    private Color backgroundColor = Color.RED;
    private Color borderColor = Color.GREEN;

    public TextContainer(int posX, int posY, int sizeX, int sizeY){
        words = new ArrayList<>();
        this.position = new Vector2D(posX,posY);
        this.size = new Vector2D(sizeX,sizeY);
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

        for (Word word : words) {
            word.draw(g);
        }
    }
}
