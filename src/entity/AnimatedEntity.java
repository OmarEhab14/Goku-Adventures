package entity;

import animation.interfaces.Idleable;
import animation.interfaces.Movable;
import animation.states.AnimationState;
import animation.states.IdleState;
import animation.states.MoveState;
import main.GamePanel;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class AnimatedEntity extends Entity implements Idleable, Movable {
    private Direction direction;
    private Map<Direction, BufferedImage[]> movingSprites;
    private Map<Direction, BufferedImage[]> idleSprites;
    private boolean isMoving;
    private int speed;
    private AnimationState currentState;
    private final IdleState idleState;
    private final MoveState moveState;

    public AnimatedEntity(GamePanel gp, Vector2D position, int speed) {
        super(gp, position);
        direction = Direction.DOWN;
        this.movingSprites = new HashMap<>();
        this.idleSprites = new HashMap<>();
        isMoving = false;
        this.speed = speed;
        idleState = new IdleState(this);
        moveState = new MoveState(this);
        currentState = idleState;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public AnimationState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AnimationState currentState) {
        this.currentState = currentState;
    }

    public Map<Direction, BufferedImage[]> getMovingSprites() {
        return movingSprites;
    }

    public void setMovingSprites(Map<Direction, BufferedImage[]> movingSprites) {
        this.movingSprites = movingSprites;
    }

    public Map<Direction, BufferedImage[]> getIdleSprites() {
        return idleSprites;
    }

    public void setIdleSprites(Map<Direction, BufferedImage[]> idleSprites) {
        this.idleSprites = idleSprites;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public IdleState getIdleState() {
        return idleState;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    // change state for the state design pattern
    public void changeState(AnimationState state) {
        this.currentState = state;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void draw(Graphics2D g2);
}
