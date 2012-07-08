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
        level = ResourceLoader.loadLevel("level0.json");

        gameScreen = new GameScreen(rocket, level);

        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }
}
