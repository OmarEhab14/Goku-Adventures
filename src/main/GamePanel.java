package main;

import entity.Player;
import util.Vector2D;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 32; // 32x32 tiles
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48 pixels
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    KeyHandler keyH = new KeyHandler();
    int FPS = 60;
    boolean showFPS = true;
    int currentFPS;
    Player player = new Player(this, this.keyH, new Vector2D(100, 100), 4);
    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true); // enables flicker-free rendering for better performance (like an invisible canvas in memory and when the drawing is complete, the entire image is copied to the screen at once, unlike the frame buffer where the user can see the drawing happening)
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
//    public void run() {
//        double drawInterval = 1000000000f / FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        long timer = 0;
//        int drawCount = 0;
//        long lastTime = System.nanoTime();
//        long currentTime;
//        while (gameThread != null) {
//            currentTime = System.nanoTime();
//            timer += currentTime - lastTime;
//            lastTime = System.nanoTime();
//            // 1.UPDATE
//            update();
//            // 2.REPAINT
//            repaint();
//            drawCount++;
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            nextDrawTime += drawInterval;
//            if(timer >= 1000000000) {
//                currentFPS = drawCount;
//                drawCount = 0;
//                timer = 0;
//            }
//        }
//    }

    //Using the delta method
    @Override
    public void run() {
        double drawInterval = 1000000000f / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1) {
                // 1.UPDATE
                update();
                // 2.REPAINT
                repaint(); // this is how you call the paintComponent method.
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        if(showFPS) {
            g2.setColor(Color.BLACK);
            g2.drawString("FPS: " + currentFPS, 10, 20);
        }
        g2.dispose();
    }
}
