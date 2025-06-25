package animation.states;

import entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IdleState implements AnimationState {
    private final Player player;

    public IdleState(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        if (player.wasMoving()) {
            player.setLastBlinkTime(System.nanoTime());
            player.setSpriteNum(1);
            player.setWasMoving(false);
        }
        player.animateIdle();
        if (player.isMoving()) {
            player.changeState(player.getWalkState());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] sprites = player.getIdleSprites().get(player.getDirection());
        int frameIndex = Math.max(0, Math.min(player.getSpriteNum() - 1, sprites.length - 1));
        g2.drawImage(sprites[frameIndex], (int) player.getPosition().x, (int) player.getPosition().y,
                player.getTileSize(), player.getTileSize(), null);
    }
}
