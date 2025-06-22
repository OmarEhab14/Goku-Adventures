package entity;

import assets.AssetManager;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {
    enum Direction {UP, DOWN, LEFT, RIGHT}

    private GamePanel gp;
    private KeyHandler keyH;
    private Direction direction;
    private Map<Direction, BufferedImage[]> movingSprites;
    private Map<Direction, BufferedImage[]> idleSprites;
    private boolean isMoving;
    private long lastBlinkTime;
    private long blinkDuration = 200000000; // Duration of blink (in nanoseconds)
    private long blinkInterval = 2000000000; // Interval between blinks (in nanoseconds)
    private boolean isBlinking;
    private long currentTime;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.direction = Direction.DOWN;
        this.movingSprites = new HashMap<>();
        this.idleSprites = new HashMap<>();
        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        isMoving = false;
        lastBlinkTime = System.nanoTime();
        isBlinking = false;
    }

    public void loadPlayerImages() {
        // Moving sprites
        movingSprites.put(Direction.UP, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_up_1.png"),
                AssetManager.getInstance().getImage("/player/goku_up_2.png"),
                AssetManager.getInstance().getImage("/player/goku_up_3.png"),
                AssetManager.getInstance().getImage("/player/goku_up_4.png"),
        });

        movingSprites.put(Direction.DOWN, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_down_1.png"),
                AssetManager.getInstance().getImage("/player/goku_down_2.png"),
                AssetManager.getInstance().getImage("/player/goku_down_3.png"),
                AssetManager.getInstance().getImage("/player/goku_down_4.png"),
        });

        movingSprites.put(Direction.RIGHT, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_right_1.png"),
                AssetManager.getInstance().getImage("/player/goku_right_2.png"),
                AssetManager.getInstance().getImage("/player/goku_right_3.png"),
                AssetManager.getInstance().getImage("/player/goku_right_4.png"),
        });

        movingSprites.put(Direction.LEFT, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_left_1.png"),
                AssetManager.getInstance().getImage("/player/goku_left_2.png"),
                AssetManager.getInstance().getImage("/player/goku_left_3.png"),
                AssetManager.getInstance().getImage("/player/goku_left_4.png"),
        });

        // Idle sprites
        idleSprites.put(Direction.UP, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_up_idle.png"),
                AssetManager.getInstance().getImage("/player/goku_up_idle.png"),
        });

        idleSprites.put(Direction.DOWN, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_down_idle_1.png"),
                AssetManager.getInstance().getImage("/player/goku_down_idle_2.png"),
        });

        idleSprites.put(Direction.RIGHT, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_right_idle_1.png"),
                AssetManager.getInstance().getImage("/player/goku_right_idle_2.png"),
        });

        idleSprites.put(Direction.LEFT, new BufferedImage[]{
                AssetManager.getInstance().getImage("/player/goku_left_idle_1.png"),
                AssetManager.getInstance().getImage("/player/goku_left_idle_2.png"),
        });
    }

    @Override
    public void update() {
        boolean wasMoving = isMoving; // Track the previous movement state
        isMoving = false;

        if (keyH.upPressed) {
            direction = Direction.UP;
            y -= speed;
            isMoving = true;
        } else if (keyH.downPressed) {
            direction = Direction.DOWN;
            y += speed;
            isMoving = true;
        } else if (keyH.rightPressed) {
            direction = Direction.RIGHT;
            x += speed;
            isMoving = true;
        } else if (keyH.leftPressed) {
            direction = Direction.LEFT;
            x -= speed;
            isMoving = true;
        }

        if (isMoving) {
            if (!wasMoving) {
                // Reset the blink timer when transitioning from idle to moving
                lastBlinkTime = System.nanoTime();
                spriteNum = 1; // Reset to default sprite for moving
            }
            spriteCounter++;
            if (spriteCounter > 8) {
                spriteNum = (spriteNum % 4) + 1;
                spriteCounter = 0;
            }
        } else {
            if (wasMoving) {
                lastBlinkTime = System.nanoTime();
                // Reset spriteNum when transitioning to idle
                spriteNum = 1;
            }
            currentTime = System.nanoTime();
            System.out.println("Current Time: " + currentTime);
            System.out.println("Last Blink Time: " + lastBlinkTime);
            System.out.println("Time since last blink: " + (currentTime - lastBlinkTime));
            // Check if it's time to start blinking
            if (!isBlinking && (currentTime - lastBlinkTime >= blinkInterval)) {
                isBlinking = true;
                spriteNum = 2; // Switch to blinking sprite
                // Only update lastBlinkTime when starting a blink
                lastBlinkTime = currentTime;
                System.out.println("Blinking started!");
            }

            // Check if it's time to stop blinking
            if (isBlinking && (currentTime - lastBlinkTime > blinkDuration)) {
                isBlinking = false;
                spriteNum = 1; // Switch back to default idle sprite
                lastBlinkTime = currentTime; // Reset timer after blink ends
                System.out.println("Blinking ended!");
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image;

        if (isMoving) {
            image = movingSprites.get(direction)[spriteNum - 1];
        } else {
            image = idleSprites.get(direction)[spriteNum - 1];
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
