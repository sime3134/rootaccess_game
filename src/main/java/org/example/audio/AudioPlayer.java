package org.example.audio;


import org.example.base.Settings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Simon Jern
 * Handles the playing and pausing of music and soundeffects in the game.
 */
public class AudioPlayer {

    private final Map<String, AudioClip> audioClips;
    private String lastPlayedMusicFileName;

    public AudioPlayer() {
        audioClips = new HashMap<>();
    }

    public void update() {
        audioClips.forEach((fileName, audioClip) -> audioClip.update());

        Map.copyOf(audioClips).forEach((fileName, audioClip) -> {
            if(audioClip.hasFinishedPlaying() && audioClip.isSoundEffect()) {
                audioClip.cleanUp();
                audioClips.remove(fileName);
            }
        });
    }

    public void playMusic(String fileName, long startPoint) {
        if(Settings.getAudioOn()) {
            if (!audioClips.containsKey(fileName)) {
                clear();
                final Clip clip = getClip(fileName, startPoint);
                final AudioClip audioClip = new AudioClip(clip, false);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.setVolume(Settings.getVolume());
                audioClips.put(fileName, audioClip);
                lastPlayedMusicFileName = fileName;
            }
        }
    }

    public void playLastMusic() {
        if(Settings.getAudioOn()) {
            if (!audioClips.containsKey(lastPlayedMusicFileName)) {
                clear();
                final Clip clip = getClip(lastPlayedMusicFileName, 0);
                final AudioClip audioClip = new AudioClip(clip, false);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                audioClip.setVolume(Settings.getVolume());
                audioClips.put(lastPlayedMusicFileName, audioClip);
            }
        }
    }

    public void playSound(String fileName, long startPoint) {
        final Clip clip = getClip(fileName, startPoint);
        if(clip == null) return;
        final AudioClip audioClip = new AudioClip(clip, true);
        audioClip.setVolume(Settings.getVolume());
        audioClips.put(fileName, audioClip);
    }

    private Clip getClip(String fileName, long startMicro) {
        final URL soundFile = AudioPlayer.class.getResource("/audio/" + fileName);
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {
            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(startMicro);
            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void playKeySound() {
        String fileName = "Words-Movement-";
        Random rand = new Random();
        fileName = fileName + rand.nextInt(1, 5) + ".wav";
        this.playSound(fileName, 0);
    }

    public void clear() {
        audioClips.forEach((fileName, audioClip) -> audioClip.cleanUp());
        audioClips.clear();
    }
}