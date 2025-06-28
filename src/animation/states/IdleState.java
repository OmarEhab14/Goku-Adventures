package animation.states;

import entity.AnimatedEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IdleState implements AnimationState {
    private final AnimatedEntity entity;

    public IdleState(AnimatedEntity entity) {
        this.entity = entity;
    }

    @Override
    public void update() {
        entity.animateIdle();

        if (entity.isPunching()) {
            entity.setSpriteNum(0);
            entity.togglePunchSide();
            entity.changeState(entity.getPunchState());
        }

        if (entity.isMoving()) {
            entity.setSpriteNum(0);
            entity.changeState(entity.getMoveState());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] sprites = entity.getIdleSprites().get(entity.getDirection());
        int frameIndex = Math.max(0, Math.min(entity.getSpriteNum() - 1, sprites.length - 1));
        g2.drawImage(sprites[frameIndex], (int) entity.getPosition().x, (int) entity.getPosition().y,
                entity.getTileSize(), entity.getTileSize(), null);
    }
}
