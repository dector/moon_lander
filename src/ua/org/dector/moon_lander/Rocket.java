package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class Rocket {
    private int x;
    private int y;

    private float vx;
    private float vy;
    private float ax;
    private float ay;

    private boolean moveUp;
    private boolean rotateLeft;
    private boolean rotateRight;

    public Rocket() {
        reset();
    }

    public void reset() {
        x = (Gdx.graphics.getWidth() - ROCKET_WIDTH) / 2;
        y = Gdx.graphics.getHeight() / 2;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAy() {
        return ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public void updateRocket(float delta) {
        if (moveUp) {
            ay = ROCKET_AY;
        } else {
            ay *= FRICTION;
        }

        vy += (ay - GRAVITY) * delta;

        y += vy;
    }

    public void moveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void rotateLeft(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void rotateRight(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }
}
