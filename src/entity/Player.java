package entity;

import assets.AssetManager;
import main.GamePanel;
import main.KeyHandler;
import util.SoundPlayer;
import util.SpriteLoader;
import util.Vector2D;

import java.awt.*;

public class Player extends AnimatedEntity  {
    private final KeyHandler keyH;
    private boolean wasMoving;
    private long lastBlinkTime;
    private long blinkDuration = 200000000; // Duration of blink (in nanoseconds)
    private long blinkInterval = 2000000000; // Interval between blinks (in nanoseconds)
    private boolean isBlinking;
    private final SoundPlayer footStepSound;
    private long lastStepTime;
    private long stepInterval = 200000000; // 200ms (2e+8 nano seconds)


    public Player(GamePanel gp, KeyHandler keyH, Vector2D position, int speed) {
        super(gp, position, speed);
        this.gp = gp;
        this.keyH = keyH;
        isBlinking = false;
        loadPlayerImages();
        lastBlinkTime = System.nanoTime();
        wasMoving = false;
        footStepSound = new SoundPlayer(AssetManager.getInstance().getSound("/sounds/walk.wav"));
    }

    public void loadPlayerImages() {
        // Moving sprites
        getMovingSprites().put(Direction.UP, SpriteLoader.loadPlayer("goku_up", 4));

        getMovingSprites().put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down", 4));

        getMovingSprites().put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right", 4));

        getMovingSprites().put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left", 4));

        // Idle sprites
        getIdleSprites().put(Direction.UP, SpriteLoader.loadPlayer("goku_up_idle", 2));

        getIdleSprites().put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down_idle", 2));

        getIdleSprites().put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right_idle", 2));

        getIdleSprites().put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left_idle", 2));
    }



    @Override
    public void update() {
        wasMoving = isMoving(); // Track the previous movement state

        Vector2D movement = new Vector2D();

        if (keyH.upPressed) {
            setDirection(Direction.UP);
            movement.y -= 1;
        }
        if (keyH.downPressed) {
            setDirection(Direction.DOWN);
            movement.y += 1;
        }
        if (keyH.rightPressed) {
            setDirection(Direction.RIGHT);
            movement.x += 1;
        }
        if (keyH.leftPressed) {
            setDirection(Direction.LEFT);
            movement.x -= 1;
        }

        setMoving(movement.length() != 0);
        if (isMoving()) {
            movement = movement.normalize().scale(getSpeed());
            position = position.add(movement);
        }
        getCurrentState().update();
    }

    @Override
    public void draw(Graphics2D g2) {
        getCurrentState().draw(g2);
    }

    @Override
    public void animateIdle() {
        if (wasMoving) {
            lastBlinkTime = System.nanoTime();
            spriteNum = 1;
            wasMoving = false;
        }
        long currentTime = System.nanoTime();
        if (!isBlinking && (currentTime - lastBlinkTime >= blinkInterval)) {
            isBlinking = true;
            spriteNum = 2;
            lastBlinkTime = currentTime;
        }
        if (isBlinking && (currentTime - lastBlinkTime > blinkDuration)) {
            isBlinking = false;
            spriteNum = 1;
            lastBlinkTime = currentTime;
        }
    }

    @Override
    public void animateMove() {
        if (!wasMoving) {
            lastBlinkTime = System.nanoTime();
            spriteNum = 1;
            wasMoving = true;
        }
        spriteCounter++;
        if (spriteCounter > 8) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;

            long currentTime = System.nanoTime();
            if (currentTime - lastStepTime > stepInterval) {
                footStepSound.play();
                lastStepTime = currentTime;
            }
        }
    }
}
