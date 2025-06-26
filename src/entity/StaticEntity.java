package entity;

import main.GamePanel;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticEntity extends Entity{
    private BufferedImage image;
    public StaticEntity(GamePanel gp, BufferedImage image, Vector2D position) {
        super(gp, position);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
