package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import ua.org.dector.gcore.common.Settings;
import ua.org.dector.gcore.game.AbstractGame;
import ua.org.dector.gcore.game.AbstractScreen;
import ua.org.dector.gcore.utils.ResourceLoader;
import ua.org.dector.moon_lander.graphics.Graphics;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.screens.EditorScreen;
import ua.org.dector.moon_lander.screens.GameScreen;
import ua.org.dector.moon_lander.screens.SplashScreen;
import ua.org.dector.moon_lander.utils.LevelLoader;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends AbstractGame {
    private GameScreen gameScreen;
    private SplashScreen splashScreen;
    private EditorScreen editorScreen;

    private AbstractScreen currentScreen;

    private Graphics g;
    private Settings settings;

    private Rocket rocket;
    private Level[] levels;

    // DEBUG MODE
    private static final boolean debug = true;

    public void create() {
        super.create();

        settings = new Settings(MOON_LANDER);

        getSettings().setScreenWidth(Gdx.graphics.getWidth());
        getSettings().setScreenHeight(Gdx.graphics.getHeight());
        getSettings().setFullscreen(Gdx.graphics.isFullscreen());

        getMusicManager().setEnabled(settings.getMusicEnabled());
        getMusicManager().setVolume(settings.getMusicVolume());

        getSoundManager().setEnabled(settings.getSfxEnabled());
        getSoundManager().setVolume(settings.getSfxVolume());

        ResourceLoader resLoader = getResourceLoader();
        resLoader.setImagesDirPath(DATA_DIR + IMG_DIR);
        resLoader.setFontsDirPath(DATA_DIR + FONTS_DIR);
        resLoader.setMusicDirPath(DATA_DIR + SOUND_DIR);
        resLoader.setParticlesDirPath(DATA_DIR + PARTICLES_DIR);
        resLoader.setSoundsDirPath(DATA_DIR + SOUND_DIR);

        g = new Graphics(this);

        //////////////////////////////

        rocket = new Rocket();
        levels = LevelLoader.loadLevelSet("levelset.json");

        if (isDebug()) {
            play();
        } else {
            splashScreen = new SplashScreen(this);
            switchScreen(splashScreen);
        }
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

    public boolean isDebug() {
        return debug;
    }

    public Graphics getGraphics() {
        return g;
    }

    public Settings getSettings() {
        return settings;
    }
}
