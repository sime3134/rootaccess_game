package org.example.base;

import org.example.utils.ImgUtils;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private String[] getFilesInFolder(String basePath) {
        URL resource = ContentManager.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames(String basePath) {
        URL resource = ContentManager.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

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
        loadFont("/font/joystix.ttf");
        loadFont("/font/january.ttf");
        loadWords("/words/interests", interests);
        loadWords("/words/occupations", occupations);
    }

    private void loadFont(String filePath) {
        System.out.println(ContentManager.class.getResource(filePath));
        URL resource = ContentManager.class.getResource(filePath);
        File fontFile = new File(resource.getFile());
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
    }

    private void loadImages(String filePath) {
        String[] imagesInFolder = getFilesInFolder(filePath);

        for(String fileName : imagesInFolder) {
            Image originalImage = ImgUtils.loadImage(filePath + "/" + fileName);
            String fileNameWithoutExt = fileName.substring(0, fileName.length() - 4);
            images.put(fileNameWithoutExt + "-1080", originalImage);
            images.put(fileNameWithoutExt + "-720", ImgUtils.scaleDownImage(originalImage, 1.5f));
        }
    }

    private void loadWords(String folderPath, Map<String, ArrayList<String>> wordDict) {
        String[] txtInFolder = getFilesInFolder(folderPath);

        for(String fileName : txtInFolder) {
            String filePath = ContentManager.class.getResource(folderPath + "/" + fileName).getPath();
            File file = new File(filePath);
            String categoryName = FilenameUtils.removeExtension(file.getName());
            ArrayList<String> wordList = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null) {
                    wordList.add(st);
                }
                wordDict.put(categoryName, wordList);
            } catch (IOException except) {
                System.out.println("Failed to read text file: " + filePath);
            }
        }
    }

}
