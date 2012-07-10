package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import ua.org.dector.moon_lander.managers.GameManagers;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.screen.AbstractScreen;
import ua.org.dector.moon_lander.screen.GameScreen;
import ua.org.dector.moon_lander.screen.SplashScreen;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private GameScreen gameScreen;
    private SplashScreen splashScreen;

    private AbstractScreen currentScreen;

    private GameManagers gameManagers;

    private Rocket rocket;
    private Level[] levels;

    public void create() {
        rocket = new Rocket();
        levels = ResourceLoader.loadLevelSet("levelset.json");

        gameManagers = new GameManagers();

        splashScreen = new SplashScreen(gameManagers, this);
        switchScreen(splashScreen);
    }

    public void play() {
        gameScreen = new GameScreen(gameManagers, rocket, levels);
        switchScreen(gameScreen);
    }

    public void switchScreen(AbstractScreen screen) {
        if (screen != null) {
            if (currentScreen != null) {
                currentScreen.dispose();
            }

            setScreen(screen);
            Gdx.input.setInputProcessor(screen);
        }
    }
}
