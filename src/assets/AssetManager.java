package assets;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static final AssetManager instance = new AssetManager();

    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, Clip> soundCache = new HashMap<>();

    private AssetManager() {}

    public static AssetManager getInstance() {
        return instance;
    }

    public BufferedImage getImage(String path) {
        if (!imageCache.containsKey(path)) {
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
                imageCache.put(path, image);
            } catch (IOException e) {
                System.err.println("Error loading image: " + path);
                e.printStackTrace();
            }
        }
        return imageCache.get(path);
    }

    public Clip getSound(String path) {
        if (!soundCache.containsKey(path)) {
            try {
                URL soundURL = getClass().getResource(path);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                soundCache.put(path, clip);
            } catch (Exception e) {
                System.err.println("Error loading sound: " + path);
                e.printStackTrace();
            }
        }
        return soundCache.get(path);
    }

    public Clip[] getSoundPool(String path, int poolSize) {
        if (!soundCache.containsKey(path)) {
            try {
                URL soundURL = getClass().getResource(path);
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                Clip[] pool = new Clip[poolSize];
                for (int i = 0; i < poolSize; i++) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    pool[i] = clip;
                }
                soundCache.put(path, pool[0]);

                return pool;

            } catch (Exception e) {
                System.err.println("Error loading sound pool: " + path);
                e.printStackTrace();
            }
        } else {
            Clip existing = soundCache.get(path);
            Clip[] pool = new Clip[poolSize];
            for (int i = 0; i < poolSize; i++) {
                pool[i] = existing;
            }
            return pool;
        }

        return new Clip[0];
    }

}
