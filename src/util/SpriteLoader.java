package util;

import assets.AssetManager;

import java.awt.image.BufferedImage;

public class SpriteLoader {
    public static BufferedImage[] loadPlayer(String prefix, int count) {
        BufferedImage[] sprites = new BufferedImage[count];

        if (count == 1) {
            String path = String.format("/player/%s.png", prefix);
            BufferedImage frame = AssetManager.getInstance().getImage(path);
            sprites[0] = frame;
            return sprites;
        }

        for (int i = 0; i < count; i++) {
            String path = String.format("/player/%s_%d.png", prefix, i + 1);
            sprites[i] = AssetManager.getInstance().getImage(path);
        }
        return sprites;
    }
}
