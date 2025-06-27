package assets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TileManager {
    private final List<BufferedImage> grassTiles = new ArrayList<>();
    private final List<BufferedImage> stoneTiles = new ArrayList<>();

    public TileManager() {
        AssetManager assetManager = AssetManager.getInstance();

        for (int i = 1; i <= 7; i++) {
            grassTiles.add(assetManager.getImage("/tiles/Grass" + i + ".png"));
        }

        for (int i = 1; i <= 9; i++) {
            stoneTiles.add(assetManager.getImage("/tiles/Stones" + i + ".png"));
        }
    }

    public BufferedImage getRandomGrassTile() {
        return grassTiles.get((int) (Math.random() * grassTiles.size()));
    }

    public BufferedImage getRandomStoneTile() {
        return stoneTiles.get((int) (Math.random() * stoneTiles.size()));
    }
}
