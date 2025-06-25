package animation.states;

import animation.interfaces.Movable;
import entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MoveState implements AnimationState{
    private final Player player;
    public MoveState(Player player) {
        this.player = player;
    }
    @Override
    public void update() {
        if (!player.wasMoving()) {
            player.setLastBlinkTime(System.nanoTime());
            player.setSpriteNum(1);
            player.setWasMoving(true);
        }
        player.animateMove();
        if (!player.isMoving()) {
            player.changeState(player.getIdleState());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage[] sprites = player.getMovingSprites().get(player.getDirection());
        int frameIndex = Math.max(0, Math.min(player.getSpriteNum() - 1, sprites.length - 1));
        g2.drawImage(sprites[frameIndex], (int) player.getPosition().x, (int) player.getPosition().y,
                player.getTileSize(), player.getTileSize(), null);
    }
}
