package org.example.base;

import org.example.utils.ImgUtils;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * Used to load different content to the game. For example spritesheets, tiles and music.
 */
public class ContentManager {
    private final Map<String, Image> images;
    private final Map<String, ArrayList<String>> interests;
    private final Map<String, ArrayList<String>> occupations;
    private Font font;

    //region getters and setters (click to view)

    public Image getImage(String name) {
        return images.get(name);
    }

    public Map<String, ArrayList<String>> getInterests() {
        return interests;
    }
    public Map<String, ArrayList<String>> getOccupations() {
        return occupations;
    }
    //endregion

    public ContentManager() {
        images = new HashMap<>();
        interests = new HashMap<>();
        occupations = new HashMap<>();
    }

    public void loadContent() {
        loadImages("/images");
        loadFonts("/font", "joystix.ttf", "january.ttf");
        loadWords("/words/interests", interests);
        loadWords("/words/occupations", occupations);
    }

    private void loadFonts(String filePath, String... fontNames) {
        for(String fontName : fontNames) {
            InputStream stream = ContentManager.class.getResourceAsStream(filePath + "/" + fontName);
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, stream);
                stream.close();
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
    }

    private void loadImages(String filePath) {
        String fileName = "";
        for(int i = 0; i < 7; i++) {
            fileName = "portrait" + i + ".png";
            Image originalImage = ImgUtils.loadImage(filePath + "/" + fileName);
            String fileNameWithoutExt = fileName.substring(0, fileName.length() - 4);
            images.put(fileNameWithoutExt + "-1080", originalImage);
            images.put(fileNameWithoutExt + "-720", ImgUtils.scaleDownImage(originalImage, 1.5f));
        }
        fileName = "bg.png";
        Image originalImage = ImgUtils.loadImage(filePath + "/" + fileName);
        String fileNameWithoutExt = fileName.substring(0, fileName.length() - 4);
        images.put(fileNameWithoutExt + "-1080", originalImage);
        images.put(fileNameWithoutExt + "-720", ImgUtils.scaleDownImage(originalImage, 1.5f));
    }

    private void loadWords(String folderPath, Map<String, ArrayList<String>> wordDict) {
        String[] txtInFolder = {};

        if(folderPath.contains("interests")) {
            txtInFolder = new String[]{"Animals.txt", "Art.txt", "Astronomy.txt", "Computers.txt", "Racing.txt",
                    "Sports.txt", "Sweets.txt"};
        } else if(folderPath.contains("occupations")) {
            txtInFolder = new String[]{"Dentist.txt", "Lawyer.txt", "Military.txt", "Scientist.txt"};
        }

        for(String fileName : txtInFolder) {
            String categoryName =  fileName.substring(0, fileName.length() - 4);
            ArrayList<String> wordList = new ArrayList<String>();
            try {
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(ContentManager.class.getResourceAsStream(folderPath + "/" + fileName)));
                String st;
                while ((st = br.readLine()) != null) {
                    wordList.add(st);
                }
                wordDict.put(categoryName, wordList);
            } catch (IOException except) {
                except.printStackTrace();
                System.out.println("Failed to read text file: " + fileName);
            }
        }
    }

}
