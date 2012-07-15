package ua.org.dector.moon_lander.models;

import com.badlogic.gdx.Gdx;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class Rocket {
    private float x;
    private float y;

    private float vx;
    private float vy;
    private float ax;
    private float ay;

    private float directionAngle;

    private boolean engineOn;
    private boolean rotateLeft;
    private boolean rotateRight;

    public Rocket(int x, int y, float directionAngle) {
        reset(x, y, directionAngle);
    }

    public Rocket() {
        this(
                (Gdx.graphics.getWidth() - ROCKET_WIDTH) / 2,
                Gdx.graphics.getHeight(),
                90
        );
    }

    public void reset(int x, int y, float angle) {
        this.x = x;
        this.y = y;

        vx = 0;
        vy = 0;
        ax = 0;
        ay = 0;

        directionAngle = angle;

        engineOn = false;
        rotateLeft = false;
        rotateRight = false;
    }

    public float getVy() {
        return vy;
    }

    public float getVx() {
        return vx;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getDirectionAngle() {
        return directionAngle;
    }

    public void updateRocket(float delta) {
        if (rotateLeft) {
            directionAngle += ROCKET_ROTATING * delta;
        }
        if (rotateRight) {
            directionAngle -= ROCKET_ROTATING * delta;
        }

        if (directionAngle >= 360) {
            directionAngle -= 360;
        } else if (directionAngle < 0) {
            directionAngle += 360;
        }

        if (engineOn) {
            ay = (float)(Math.sin(Math.toRadians(directionAngle)) * ROCKET_AY);
            ax = (float)(Math.cos(Math.toRadians(directionAngle)) * ROCKET_AY);
        } else {
            ay *= FRICTION;
            ax *= FRICTION;
        }

        vy += (ay - GRAVITY) * delta;
        vx += ax * delta;

        y += vy;
        x += vx;
    }

    public void setEngineOn(boolean engineOn) {
        this.engineOn = engineOn;
    }

    public void rotateLeft(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void rotateRight(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }

    public boolean isEngineOn() {
        return engineOn;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setDirectionAngle(float directionAngle) {
        this.directionAngle = directionAngle;
    }
}
