package entity;

import assets.AssetManager;
import main.GamePanel;
import main.KeyHandler;
import util.SpriteLoader;
import util.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {
    enum Direction {UP, DOWN, LEFT, RIGHT}

    private final GamePanel gp;
    private final KeyHandler keyH;
    private Direction direction;
    private final Map<Direction, BufferedImage[]> movingSprites;
    private final Map<Direction, BufferedImage[]> idleSprites;
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
        position = new Vector2D(100, 100);
        speed = 4;
        isMoving = false;
        lastBlinkTime = System.nanoTime();
        isBlinking = false;
    }

    public void loadPlayerImages() {
        // Moving sprites
        movingSprites.put(Direction.UP, SpriteLoader.loadPlayer("goku_up", 4));

        movingSprites.put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down", 4));

        movingSprites.put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right", 4));

        movingSprites.put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left", 4));

        // Idle sprites
        idleSprites.put(Direction.UP, SpriteLoader.loadPlayer("goku_up_idle", 1));

        idleSprites.put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down_idle", 2));

        idleSprites.put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right_idle", 2));

        idleSprites.put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left_idle", 2));
    }

    @Override
    public void update() {
        boolean wasMoving = isMoving; // Track the previous movement state
        isMoving = false;

        Vector2D movement = new Vector2D();

        if (keyH.upPressed) {
            direction = Direction.UP;
            movement.y -= 1;
        }
        if (keyH.downPressed) {
            direction = Direction.DOWN;
            movement.y += 1;
        }
        if (keyH.rightPressed) {
            direction = Direction.RIGHT;
            movement.x += 1;
        }
        if (keyH.leftPressed) {
            direction = Direction.LEFT;
            movement.x -= 1;
        }

        if (movement.length() != 0) {
            movement = movement.normalize().scale(speed);
            position = position.add(movement);
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

        g2.drawImage(image, (int) position.x, (int) position.y, gp.tileSize, gp.tileSize, null);
    }
}
