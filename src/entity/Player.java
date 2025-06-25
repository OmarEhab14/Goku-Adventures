package entity;

import animation.interfaces.Idleable;
import animation.interfaces.Movable;
import animation.states.AnimationState;
import animation.states.IdleState;
import animation.states.MoveState;
import main.GamePanel;
import main.KeyHandler;
import util.SpriteLoader;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity implements Idleable, Movable {
    private final GamePanel gp;
    private final KeyHandler keyH;
    private Direction direction;
    private final Map<Direction, BufferedImage[]> movingSprites;
    private final Map<Direction, BufferedImage[]> idleSprites;
    private boolean isMoving;
    private boolean wasMoving;
    private long lastBlinkTime;
    private long blinkDuration = 200000000; // Duration of blink (in nanoseconds)
    private long blinkInterval = 2000000000; // Interval between blinks (in nanoseconds)
    private boolean isBlinking;

    private AnimationState currentState;
    private final IdleState idleState;
    private final MoveState moveState;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.direction = Direction.DOWN;
        this.movingSprites = new HashMap<>();
        this.idleSprites = new HashMap<>();
        position = new Vector2D(100, 100);
        speed = 4;
        isMoving = false;
        isBlinking = false;
        loadPlayerImages();
        idleState = new IdleState(this);
        moveState = new MoveState(this);
        currentState = idleState;
        lastBlinkTime = System.nanoTime();
        isMoving = false;
        wasMoving = false;
    }

    // setters and getters
    public Direction getDirection() {
        return direction;
    }

    public Map<Direction, BufferedImage[]> getIdleSprites() {
        return idleSprites;
    }

    public Map<Direction, BufferedImage[]> getMovingSprites() {
        return movingSprites;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getTileSize() {
        return gp.tileSize;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int num) {
        this.spriteNum = num;
    }

    public AnimationState getIdleState() {
        return idleState;
    }

    public AnimationState getWalkState() {
        return moveState;
    }

    public void setWasMoving(boolean wasMoving) {
        this.wasMoving = wasMoving;
    }

    public boolean wasMoving() {
        return wasMoving;
    }

    public void setLastBlinkTime(long lastBlinkTime) {
        this.lastBlinkTime = lastBlinkTime;
    }

    public void loadPlayerImages() {
        // Moving sprites
        movingSprites.put(Direction.UP, SpriteLoader.loadPlayer("goku_up", 4));

        movingSprites.put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down", 4));

        movingSprites.put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right", 4));

        movingSprites.put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left", 4));

        // Idle sprites
        idleSprites.put(Direction.UP, SpriteLoader.loadPlayer("goku_up_idle", 2));

        idleSprites.put(Direction.DOWN, SpriteLoader.loadPlayer("goku_down_idle", 2));

        idleSprites.put(Direction.RIGHT, SpriteLoader.loadPlayer("goku_right_idle", 2));

        idleSprites.put(Direction.LEFT, SpriteLoader.loadPlayer("goku_left_idle", 2));
    }

    // change state for the state design pattern
    public void changeState(AnimationState state) {
        this.currentState = state;
    }

    @Override
    public void update() {
        wasMoving = isMoving; // Track the previous movement state

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

        isMoving = movement.length() != 0;
        if (isMoving) {
            movement = movement.normalize().scale(speed);
            position = position.add(movement);
        }
        currentState.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        currentState.draw(g2);
    }

    @Override
    public void animateIdle() {
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
        spriteCounter++;
        if (spriteCounter > 8) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }
}
