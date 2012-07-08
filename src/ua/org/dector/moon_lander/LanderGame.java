package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private GameScreen gameScreen;

    private Rocket rocket;
    private Level level;

    public void create() {
        rocket = new Rocket();
        level = Level.fromFile("level0.json");

        gameScreen = new GameScreen(rocket, level);
        Graphics.init();

        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }
}
