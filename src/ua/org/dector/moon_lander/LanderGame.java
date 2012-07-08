package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private GameScreen gameScreen;

    private Rocket rocket;
    private Level[] levels;

    public void create() {
        rocket = new Rocket();
        levels = ResourceLoader.loadLevelSet("levelset.json");

        gameScreen = new GameScreen(rocket, levels);

        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }
}
