package util;

import javax.sound.sampled.Clip;

public class SoundPlayer {
    private final Clip clip;

    public SoundPlayer(Clip clip) {
        this.clip = clip;
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0); // rewind
            clip.start();
        }
    }

    public void loop(int count) {
        if (clip != null) {
            clip.loop(count);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}

