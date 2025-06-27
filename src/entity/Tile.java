package entity;

import main.GamePanel;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends StaticEntity{
    private final TileType type;
    public Tile(GamePanel gp, BufferedImage image, Vector2D position, TileType type) {
        super(gp, image, position);
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(getImage(), (int) getPosition().x, (int) getPosition().y,
                gp.tileSize, gp.tileSize, null);
    }
}
