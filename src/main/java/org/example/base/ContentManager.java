package org.example.base;

import org.example.utils.ImgUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * Used to load different content to the game. For example spritesheets, tiles and music.
 */
public class ContentManager {
    private final Map<String, Image> images;
    private Font font;

    //region getters and setters (click to view)
    private String[] getImagesInFolder(String basePath) {
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
    //endregion

    public ContentManager() {
        images = new HashMap<>();
    }

    public void loadContent() {
        loadImages("/images");
        //loadFont("/font/joystix.ttf");
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
        String[] imagesInFolder = getImagesInFolder(filePath);

        for(String fileName : imagesInFolder) {
            Image originalImage = ImgUtils.loadImage(filePath + "/" + fileName);
            String fileNameWithoutExt = fileName.substring(0, fileName.length() - 4);
            images.put(fileNameWithoutExt + "-1080", originalImage);
            images.put(fileNameWithoutExt + "-720", ImgUtils.scaleDownImage(originalImage, 1280, 720));
        }
    }

}
