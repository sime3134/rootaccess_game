package org.example.utils;

public class Timer {
    long startTime;
    final int levelTime = 40;

    public Timer() {
        this.startTime = System.currentTimeMillis();
    }

    public int getSecondsSinceStart() {
        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    public int getSecondsLeft() {
        return levelTime - getSecondsSinceStart();
    }
}
