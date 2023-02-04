package org.example.base;

/**
 * @author Simon Jern
 * Stores settings for the game.
 */
public abstract class Settings {

    private Settings() {}

    private static int SCREEN_WIDTH = 1280;
    private static int SCREEN_HEIGHT = 720;

    public static void setScreenSize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
