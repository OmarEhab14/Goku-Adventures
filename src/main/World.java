package main;

import assets.TileManager;
import entity.Tile;
import entity.TileType;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final Tile[][] environmentTiles;
    private final GamePanel gp;
    private final TileManager tileManager;

    public World(GamePanel gp) {
        this.gp = gp;
        this.tileManager = new TileManager();
        environmentTiles = new Tile[gp.maxScreenCol][gp.maxScreenRow];
        generateEnvironment();
    }

    private void generateEnvironment() {
        int cols = gp.maxScreenCol;
        int rows = gp.maxScreenRow;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Vector2D pos = new Vector2D(x * gp.tileSize, y * gp.tileSize);
                BufferedImage tileImage;
                TileType type;
                if (Math.random() < 0.7) { // 70% chance of grass
                    tileImage = tileManager.getRandomGrassTile();
                    type = TileType.GRASS;
                } else {
                    tileImage = tileManager.getRandomStoneTile();
                    type = TileType.STONE;
                }

                Tile tile = new Tile(gp, tileImage, pos, type);
                environmentTiles[x][y] = tile;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int tileSize = gp.tileSize;
        int screenWidth = gp.getWidth();
        int screenHeight = gp.getHeight();

        for (int y = 0; y < environmentTiles[0].length; y++) {
            for (int x = 0; x < environmentTiles.length; x++) {
                Tile tile = environmentTiles[x][y];
                Vector2D pos = tile.getPosition();

                if (pos.x + tileSize >= 0 && pos.x <= screenWidth &&
                        pos.y + tileSize >= 0 && pos.y <= screenHeight) {
                    tile.draw(g2);
                }
            }
        }
    }

    public Tile getTileAt(int col, int row) {
        if (col >= 0 && col < gp.maxScreenCol && row >= 0 && row < gp.maxScreenRow) {
            return environmentTiles[col][row];
        }
        return null;
    }

}
