package util;

import javax.sound.sampled.Clip;

public class PooledSoundPlayer {
    private final Clip[] clips;
    private int currentIndex = 0;

    public PooledSoundPlayer(Clip[] clips) {
        this.clips = clips;
    }

    public void play() {
        if (clips.length == 0) return;

        Clip clip = clips[currentIndex];
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();

        currentIndex = (currentIndex + 1) % clips.length;
    }

    public void stopAll() {
        for (Clip clip : clips) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}
