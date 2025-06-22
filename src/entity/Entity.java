package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y;
    public int speed;
    int spriteCounter = 0;
    int spriteNum = 1;
    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
