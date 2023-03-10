package org.example.audio;

import org.example.base.Settings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * @author Simon Jern
 * Implements music and soundeffects for the game
 */
public class AudioClip {

    private final Clip clip;
    private final boolean soundEffect;

    public boolean hasFinishedPlaying() {
        return !clip.isRunning();
    }

    public boolean isSoundEffect() {
        return soundEffect;
    }

    protected AudioClip(Clip clip, boolean soundEffect) {
        this.clip = clip;
        this.soundEffect = soundEffect;
        clip.start();
    }

    public void update() {
        setVolume(Settings.getVolume());
    }

    void setVolume(float volume) {
        final FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * volume) + control.getMinimum();

        control.setValue(gain);
    }



    public void cleanUp() {
        clip.close();
    }
}