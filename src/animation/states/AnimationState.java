package animation.states;

import java.awt.*;

public interface AnimationState {
    public void update();

    public void draw(Graphics2D g2);
}
