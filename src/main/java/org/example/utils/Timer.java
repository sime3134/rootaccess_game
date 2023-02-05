package org.example.utils;

public class Timer {
    static long startTime;
    final static int levelTime = 40;

    public Timer() {
        this.startTime = System.currentTimeMillis();
    }

    public static int getSecondsSinceStart() {
        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    public static int getSecondsLeft() {
        return levelTime - getSecondsSinceStart();
    }

    public static void setSecondsLeft(int seconds) {
        startTime = System.currentTimeMillis() - (levelTime - seconds)*1000;
    }

    public static void reduceSeconds(int timeToReduce) {
        startTime -= timeToReduce*1000;
    }
}
