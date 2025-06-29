package animation.states;

import entity.AnimatedEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PunchState implements AnimationState {
    private AnimatedEntity entity;
    private int frameLimit;
    public PunchState(AnimatedEntity entity) {
        this.entity = entity;
        this.frameLimit = 4;
    }
    @Override
    public void update() {
        entity.animatePunch();
        if (entity.getSpriteNum() >= frameLimit) {
            entity.setSpriteNum(0);
            entity.setHasPlayedPunchSound(false);
            if (entity.isMoving()) {
                entity.changeState(entity.getMoveState());
            } else {
                entity.changeState(entity.getIdleState());
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] sprites = entity.getPunchSprites().get(entity.getDirection()).get(entity.getCurrentPunchSide());
        int frameIndex = Math.max(0, Math.min(entity.getSpriteNum() - 1, sprites.length - 1));
        g2.drawImage(sprites[frameIndex], (int) entity.getPosition().x, (int) entity.getPosition().y,
                entity.getTileSize(), entity.getTileSize(), null);
    }
}
