package org.example.wordgen;

import org.example.ui.UIText;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class TextGenerator {

    private ArrayList<UIText> screenText;
    private final Map<String, ArrayList<String>> interests;
    private final Map<String, ArrayList<String>> occupations;

    public TextGenerator(Map<String, ArrayList<String>> interests, Map<String, ArrayList<String>> occupations) {
        screenText = new ArrayList<UIText>();
        this.interests = interests;
        this.occupations = occupations;
    };

    public void createText(float difficulty, int length) {
        
    }

    private String createFillerString() {
        Random rand = new Random();
        int len = 3;
        char[] fillerChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
                                '_', '+', '{', '}', '\"', '|', '?', '<', '>', '[',
                                ']', '\\', '.', '~', '1', '2', '3', '4', '5', '6',
                                '7', '8', '9', '0', '\''};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int charIndex = rand.nextInt(fillerChars.length);
            sb.append(fillerChars[charIndex]);
        }
        return sb.toString();
    }
}
