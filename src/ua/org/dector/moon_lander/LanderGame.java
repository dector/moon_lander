package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import ua.org.dector.moon_lander.managers.AbstractGame;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.screens.AbstractScreen;
import ua.org.dector.moon_lander.screens.EditorScreen;
import ua.org.dector.moon_lander.screens.GameScreen;
import ua.org.dector.moon_lander.screens.SplashScreen;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends AbstractGame {
    private GameScreen gameScreen;
    private SplashScreen splashScreen;
    private EditorScreen editorScreen;

    private AbstractScreen currentScreen;

    private Rocket rocket;
    private Level[] levels;

    public void create() {
        super.create();
        rocket = new Rocket();
        levels = ResourceLoader.loadLevelSet("levelset.json");

        splashScreen = new SplashScreen(this);
        switchScreen(splashScreen);
    }

    public void play() {
        if (gameScreen == null)
            gameScreen = new GameScreen(rocket, levels, this);
        switchScreen(gameScreen);
    }

    public void play(Level level) {
        if (gameScreen == null)
            gameScreen = new GameScreen(rocket, new Level[] {level}, this);

        gameScreen.setLevelSet(new Level[] {level});

        gameScreen.playLevel(0);
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

    public void openEditor(Level level, Rocket rocket) {
        if (editorScreen == null)
            editorScreen = new EditorScreen(level, rocket, this);

        if (level != editorScreen.getLevel())
            editorScreen.editLevel(level, null);
        
        switchScreen(editorScreen);
    }
}
