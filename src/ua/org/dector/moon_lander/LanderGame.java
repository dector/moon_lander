package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private Screen gameScreen;

    private int rocketX;
    private int rocketY;
    private float rocketVelocityX;
    private float rocketVelocityY;

    public void create() {
        rocketX = (Gdx.graphics.getWidth() - ROCKET_WIDTH) / 2;
        rocketY = Gdx.graphics.getHeight() / 2;

        gameScreen = new GameScreen(this);

        setScreen(gameScreen);
    }

    public float getRocketVelocityY() {
        return rocketVelocityY;
    }

    public void setRocketVelocityY(float rocketVelocityY) {
        this.rocketVelocityY = rocketVelocityY;
    }

    public float getRocketVelocityX() {
        return rocketVelocityX;
    }

    public void setRocketVelocityX(float rocketVelocityX) {
        this.rocketVelocityX = rocketVelocityX;
    }

    public int getRocketY() {
        return rocketY;
    }

    public void setRocketY(int rocketY) {
        this.rocketY = rocketY;
    }

    public int getRocketX() {
        return rocketX;
    }

    public void setRocketX(int rocketX) {
        this.rocketX = rocketX;
    }
}
