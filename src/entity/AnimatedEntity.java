package entity;

import animation.interfaces.Idleable;
import animation.interfaces.Movable;
import animation.interfaces.Punchable;
import animation.states.AnimationState;
import animation.states.IdleState;
import animation.states.MoveState;
import animation.states.PunchState;
import main.GamePanel;
import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class AnimatedEntity extends Entity implements Idleable, Movable, Punchable {
    private Direction direction;
    private Map<Direction, BufferedImage[]> movingSprites;
    private Map<Direction, BufferedImage[]> idleSprites;
    private Map<Direction, Map<PunchSide, BufferedImage[]>> punchSprites;
    private boolean isMoving;
    private boolean isPunching;
    private int speed;
    private AnimationState currentState;
    private final IdleState idleState;
    private final MoveState moveState;
    private final PunchState punchState;
    private PunchSide currentPunchSide;

    public AnimatedEntity(GamePanel gp, Vector2D position, int speed) {
        super(gp, position);
        direction = Direction.DOWN;
        this.movingSprites = new HashMap<>();
        this.idleSprites = new HashMap<>();
        this.punchSprites = new HashMap<>();
        isMoving = false;
        this.speed = speed;
        idleState = new IdleState(this);
        moveState = new MoveState(this);
        punchState = new PunchState(this);
        currentPunchSide = PunchSide.RIGHT;
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

    public Map<Direction, Map<PunchSide, BufferedImage[]>> getPunchSprites() {
        return punchSprites;
    }

    public void setPunchSprites(Map<Direction, Map<PunchSide, BufferedImage[]>> punchSprites) {
        this.punchSprites = punchSprites;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isPunching() {return isPunching;}

    public void setPunching(boolean punching){
        isPunching = punching;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public IdleState getIdleState() {
        return idleState;
    }

    public MoveState getMoveState() {
        return moveState;
    }

    public PunchState getPunchState() {
        return punchState;
    }

    public PunchSide getCurrentPunchSide() {
        return currentPunchSide;
    }

    public void togglePunchSide() {
        currentPunchSide = currentPunchSide == PunchSide.RIGHT ? PunchSide.LEFT : PunchSide.RIGHT;
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
