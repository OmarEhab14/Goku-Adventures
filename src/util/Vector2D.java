package util;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D scale(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        float len = this.length();
        if (len != 0) {
            return new Vector2D(x / len, y / len);
        }
        return new Vector2D(0, 0);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
