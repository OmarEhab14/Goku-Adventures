package entity;

import main.GamePanel;
import main.KeyHandler;
import main.World;
import util.FootStepSoundManager;
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
    private final FootStepSoundManager footStepSoundManager;
    private long lastStepTime;
    private long stepInterval = 60000000; // 90ms (9e+7 nano seconds)
    private World world;


    public Player(GamePanel gp, KeyHandler keyH, Vector2D position, int speed, World world) {
        super(gp, position, speed);
        this.gp = gp;
        this.keyH = keyH;
        isBlinking = false;
        loadPlayerImages();
        lastBlinkTime = System.nanoTime();
        wasMoving = false;
        footStepSoundManager = new FootStepSoundManager();
        this.world = world;
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

    public Tile getTileUnderPlayer() {
        int tileX = (int)(getPosition().x + gp.tileSize / 2) / gp.tileSize;
        int tileY = (int)(getPosition().y + gp.tileSize) / gp.tileSize;
        return world.getTileAt(tileX, tileY);
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
            spriteNum = 1;
            wasMoving = true;
        }
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
        long currentTime = System.nanoTime();
        if ((spriteNum == 1 || spriteNum == 3) && (currentTime - lastStepTime > stepInterval)) {
            Tile tile = getTileUnderPlayer();
            if (tile != null) {
                footStepSoundManager.play(tile.getType());
            }
            lastStepTime = currentTime;
        }
    }
}
