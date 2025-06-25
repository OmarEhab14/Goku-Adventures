package assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static final AssetManager instance = new AssetManager();

    private final Map<String, BufferedImage> imageCache = new HashMap<>();

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
}
