package org.example.ui;

import org.example.base.Vector2D;
import org.example.enums.WordType;
import org.example.utils.ImgUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UITextContainer {
    private List<UIText> textComponents;

    private int currentSelection;

    private Vector2D position;

    private Vector2D size;

    private int padding;

    private Image sprite;

    private Color backgroundColor = new Color(0,0,0,0);
    private Color borderColor = new Color(0,0,0,0);

    private int currentLineLength;

    private final int maxLineLength = 58;

    private int nextWordXPos;

    private int nextWordYPos;

    private final boolean vertical;

    private boolean selectable;

    public UITextContainer(int posX, int posY, int sizeX, int sizeY, int padding, boolean vertical, boolean selectable){
        textComponents = new ArrayList<>();
        this.position = new Vector2D(posX,posY);
        this.size = new Vector2D(sizeX,sizeY);
        this.currentLineLength = 0;
        this.nextWordXPos = position.intX() + padding;
        this.nextWordYPos = position.intY() + padding;
        this.padding = padding;
        this.vertical = vertical;
        this.currentSelection = 0;
        this.selectable = selectable;
    }

    public void addTexts(List<UIText> texts){
        if(!vertical) {
            for (UIText t : texts) {

                if(currentLineLength + t.getText().length() > maxLineLength){
                    t.setPosition(nextWordXPos, nextWordYPos);
                    Vector2D newPos = t.splitWord(currentLineLength + t.getText().length() - maxLineLength,
                            position.intX() + padding, nextWordYPos);
                    nextWordXPos = newPos.intX();
                    nextWordYPos = newPos.intY();
                    currentLineLength = t.getLengthOfLastSplit();
                }else if(currentLineLength + t.getText().length() == maxLineLength) {
                    t.setPosition(nextWordXPos, nextWordYPos);
                    nextWordXPos = position.intX() + padding;
                    nextWordYPos += t.getHeight() + 10;
                    currentLineLength = 0;
                }else{
                    t.setPosition(nextWordXPos, nextWordYPos);
                    nextWordXPos += t.getWidthOfFirstSplit();
                    currentLineLength += t.getText().length();
                }

                textComponents.add(t);
            }
        } else {
            for (UIText t : texts) {
                t.setPosition(position.intX() + nextWordXPos + padding, position.intY() + nextWordYPos + padding);

                nextWordYPos += t.getHeight() + 10;

                textComponents.add(t);
            }
        }

        if(selectable) {
            for(int i = 0; i < textComponents.size(); i++) {
                if(textComponents.get(i).getWordType() == WordType.SELECTABLE || textComponents.get(i).getWordType() == WordType.CORRECT) {
                    currentSelection = i;
                    break;
                }
            }
            textComponents.get(currentSelection).setSelected(true);
        }
    }

    public void update(){
        for (UIText text : textComponents) {
            text.update();
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

    public void moveLeft() {
        if(currentSelection > 0) {
            int lastSelection = currentSelection;
            for(int i = currentSelection - 1; i >= 0; i--) {
                if(textComponents.get(i).getWordType() == WordType.SELECTABLE || textComponents.get(i).getWordType() == WordType.CORRECT) {
                    currentSelection = i;
                    break;
                }
            }
            textComponents.get(lastSelection).setSelected(false);
            textComponents.get(currentSelection).setSelected(true);
        }
    }

    public void moveRight() {
        if(currentSelection < textComponents.size() - 1) {
            int lastSelection = currentSelection;
            for(int i = currentSelection + 1; i < textComponents.size(); i++) {
                if(textComponents.get(i).getWordType() == WordType.SELECTABLE || textComponents.get(i).getWordType() == WordType.CORRECT) {
                    currentSelection = i;
                    break;
                }
            }
            textComponents.get(lastSelection).setSelected(false);
            textComponents.get(currentSelection).setSelected(true);
        }
    }

    // UGLY CODE, DELETE
    public void moveVertical(int moveStep) {
        int idx = currentSelection + moveStep;
        if (idx < 0) {idx = textComponents.size()-1;}
        int currentDistance = Integer.MAX_VALUE;
        UIText current = textComponents.get(currentSelection);
        System.out.println(idx);
        while (true) {
            UIText bestMatch = textComponents.get(idx);
            idx = (idx + moveStep) % textComponents.size();
            if (idx < 0) {idx = textComponents.size()-1;}
            if (current.getY() == bestMatch.getY()) {continue;}
            if (Math.abs(current.getX()-bestMatch.getX()) < Math.abs(currentDistance)) {
                currentDistance = current.getX()-bestMatch.getX();
            } else {
                idx = idx-moveStep;
                if (textComponents.get(idx).getWordType().equals(WordType.FILLER)) {
                    int val1 = textComponents.get(idx-1).getX() - textComponents.get(currentSelection).getX();
                    int val2 = textComponents.get(idx+1).getX() - textComponents.get(currentSelection).getX();
                    idx = Math.abs(val1) < Math.abs(val2) ? idx-1 : idx + 1;
                }
                textComponents.get(currentSelection).setSelected(false);
                textComponents.get(idx).setSelected(true);
                currentSelection = idx;
                break;
            }
        }

    }

    public void moveUp() {
        moveVertical(-1);
    }

    public void moveDown() {
        moveVertical(1);
    }
}
