package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.screen.GameScreen;
import ua.org.dector.moon_lander.screen.SplashScreen;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private GameScreen gameScreen;
    private SplashScreen splashScreen;

    private Rocket rocket;
    private Level[] levels;

    public void create() {
        rocket = new Rocket();
        levels = ResourceLoader.loadLevelSet("levelset.json");

        splashScreen = new SplashScreen(this);
        setScreen(splashScreen);
        Gdx.input.setInputProcessor(splashScreen);
    }

    public void play() {
        splashScreen.dispose();
        splashScreen = null;

        gameScreen = new GameScreen(rocket, levels);

        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }
}
