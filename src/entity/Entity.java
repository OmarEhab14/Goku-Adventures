package entity;

import main.GamePanel;
import util.Vector2D;

import java.awt.*;

public abstract class Entity {
    protected GamePanel gp;
    protected Vector2D position;
    protected int speed;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;


    public Entity(GamePanel gp, Vector2D position) {
        this.gp = gp;
        this.position = position;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public int getTileSize() {
        return gp.tileSize;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);

}
