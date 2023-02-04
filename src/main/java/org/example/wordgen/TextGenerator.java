package org.example.wordgen;

import org.example.enums.WordType;
import org.example.ui.UIText;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class TextGenerator {

    private ArrayList<UIText> screenText1;
    private ArrayList<UIText> screenText2;
    private final Map<String, ArrayList<String>> interests;
    private final Map<String, ArrayList<String>> occupations;

    private ArrayList<UIText> interestsText;
    private UIText occupationText;

    public TextGenerator(Map<String, ArrayList<String>> interests, Map<String, ArrayList<String>> occupations) {
        screenText1 = new ArrayList<UIText>();
        screenText2 = new ArrayList<UIText>();
        this.interests = interests;
        this.occupations = occupations;
        interestsText = new ArrayList<>();
    };

    public void createText(float difficulty, int length) {
        Random rand = new Random();
        int text1Len = 0;
        int text2Len = 0;
        int noInterests = 1;

        // Words that are not candidates for "not correct" category.
        ArrayList<String> possibleWrongWords = new ArrayList<String>();
        ArrayList<String> possibleCorrectWords = new ArrayList<String>();

        // Randomize noInterest number of interests categories the UIText list
        for (int i = 0; i < noInterests; i++) {
            ArrayList<String> interestsArray = new ArrayList<String>(interests.keySet());
            String interestName = interestsArray.get(rand.nextInt(interestsArray.size()));
            UIText interestUIText = new UIText(interestName, WordType.SELECTABLE);
            interestsText.add(interestUIText);
        }

        // Randomize the occupation category
        ArrayList<String> occupationsArray = new ArrayList<String>(occupations.keySet());
        String occupationName = occupationsArray.get(rand.nextInt(occupationsArray.size()));
        occupationText = new UIText(occupationName, WordType.SELECTABLE);


        // Add all the words to the correct list (correct candidates or wrong candidates)
        // First for the interest categories
        for (String categoryName : interests.keySet()) {
            ArrayList<String> wrongOrCorrectWordList = possibleWrongWords;
            for (UIText uiText : interestsText) {
                if (uiText.getText() == categoryName) {wrongOrCorrectWordList = possibleCorrectWords;}
            }
            for (String word : interests.get(categoryName)) {
                wrongOrCorrectWordList.add(word);
            }
        }
        // Then for the occupation category
        for (String categoryName : occupations.keySet()) {
            ArrayList<String> wrongOrCorrectWordList = possibleWrongWords;
            if (categoryName.equals(occupationText.getText())) {wrongOrCorrectWordList = possibleCorrectWords;}
            for (String word : occupations.get(categoryName)) {
                wrongOrCorrectWordList.add(word);
            }
        }
        ArrayList<String> randWords = new ArrayList<>();
        while (Math.max(text1Len, text2Len) < length) {
            // Add a word
            int wordIndex = rand.nextInt(possibleWrongWords.size());
            String wordToAdd = possibleWrongWords.get(wordIndex);
            possibleWrongWords.remove(wordIndex);

            int splitIndex;
            //System.out.println(wordToAdd);
            if (wordToAdd.length() == 4) { splitIndex = 2;}
            else {splitIndex = rand.nextInt(2, wordToAdd.length() - 2);}
            text1Len += splitIndex;
            text2Len += wordToAdd.length() - splitIndex;
            screenText1.add(new UIText(wordToAdd.substring(0, splitIndex), WordType.SELECTABLE));
            screenText2.add(new UIText(wordToAdd.substring(splitIndex), WordType.SELECTABLE));

            // Add filler
            String fillString = createFillerString();
            text1Len += fillString.length();
            screenText1.add(new UIText(fillString, WordType.FILLER));
            fillString = createFillerString();
            text2Len += fillString.length();
            screenText2.add(new UIText(fillString, WordType.FILLER));
        }

        // Finally, add a random word from the one of the correct categories at a random location
        int wordIndex = rand.nextInt(possibleCorrectWords.size());
        String correctWord = possibleCorrectWords.get(wordIndex);
        int splitIndex;
        if (correctWord.length() == 4) { splitIndex = 2;}
        else {splitIndex = rand.nextInt(2, correctWord.length() - 2);}
        int addIndex1 = rand.nextInt(screenText1.size() / 2) * 2;
        screenText1.add(addIndex1, new UIText(correctWord.substring(0, splitIndex), WordType.CORRECT));
        screenText1.add(addIndex1 + 1, new UIText(createFillerString(), WordType.FILLER));
        int addIndex2 = rand.nextInt(screenText2.size() / 2) * 2;
        screenText2.add(addIndex2, new UIText(correctWord.substring(splitIndex), WordType.CORRECT));
        screenText2.add(addIndex1 + 1, new UIText(createFillerString(), WordType.FILLER));
    }

    private String createFillerString() {
        Random rand = new Random();
        int len = 3;
        char[] fillerChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
                                '_', '+', '{', '}', 34, '|', '?', '<', '>', '[',
                                ']', 97, '.', '~', '1', '2', '3', '4', '5', '6',
                                '7', '8', '9', '0', 47};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int charIndex = rand.nextInt(fillerChars.length);
            sb.append(fillerChars[charIndex]);
        }
        return sb.toString();
    }

    public ArrayList<UIText> getText1() {
        return screenText1;
    }

    public ArrayList<UIText> getText2() {
        return screenText2;
    }
}
