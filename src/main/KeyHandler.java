package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class KeyHandler implements KeyListener {
    private final Set<Integer> justPressedKeys = new HashSet<>();
    private final Set<Integer> currentlyPressedKeys = new HashSet<>();

    public boolean upPressed, downPressed, leftPressed, rightPressed, punchPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!currentlyPressedKeys.contains(code)) {
            justPressedKeys.add(code);  // Only trigger on new press
        }
        currentlyPressedKeys.add(code);

        updateBooleans();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        currentlyPressedKeys.remove(code);
        updateBooleans();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void updateBooleans() {
        upPressed = currentlyPressedKeys.contains(KeyEvent.VK_W);
        downPressed = currentlyPressedKeys.contains(KeyEvent.VK_S);
        leftPressed = currentlyPressedKeys.contains(KeyEvent.VK_A);
        rightPressed = currentlyPressedKeys.contains(KeyEvent.VK_D);
        punchPressed = currentlyPressedKeys.contains(KeyEvent.VK_J);
    }

    public boolean isJustPressed(int keyCode) {
        return justPressedKeys.contains(keyCode);
    }

    public void clearJustPressed() {
        justPressedKeys.clear();
    }
}
