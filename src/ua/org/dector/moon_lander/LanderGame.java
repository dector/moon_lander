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
    private EditorScreen editorScreen;

    private AbstractScreen currentScreen;

    private Graphics g;
    private Settings settings;

    // DEBUG MODE
    private static final boolean debug = true;

    public void create() {
        super.create();

        settings = new Settings(MOON_LANDER);

        getMusicManager().setEnabled(settings.isMusicEnabled());
        getMusicManager().setVolume(settings.getMusicVolume());

        getSoundManager().setEnabled(settings.isSfxEnabled());
        getSoundManager().setVolume(settings.getSfxVolume());

        ResourceLoader resLoader = getResourceLoader();
        resLoader.setImagesDirPath(DATA_DIR + IMG_DIR);
        resLoader.setFontsDirPath(DATA_DIR + FONTS_DIR);
        resLoader.setMusicDirPath(DATA_DIR + SOUND_DIR);
        resLoader.setParticlesDirPath(DATA_DIR + PARTICLES_DIR);
        resLoader.setSoundsDirPath(DATA_DIR + SOUND_DIR);

        g = new Graphics(this);

        restoreScreenSize();

        if (isDebug()) {
            play();
        } else {
            SplashScreen splashScreen = new SplashScreen(this);
            switchScreen(splashScreen);
        }
    }

    private void restoreScreenSize() {
        Settings settings = getSettings();
        int screenWidth = settings.getScreenWidth();
        int screenHeight = settings.getScreenHeight();
        boolean fullscreen = settings.isFullscreen();
        Gdx.graphics.setDisplayMode(screenWidth, screenHeight, fullscreen);
    }

    public void play() {
        if (gameScreen == null) {
            Level[] levels = LevelLoader.loadLevelSet("levelset.json");
            gameScreen = new GameScreen(this, levels);
        }

        switchScreen(gameScreen);
    }

    public void play(Level level) {
        Level[] levels = { level };

        if (gameScreen == null)
            gameScreen = new GameScreen(this, levels);

        gameScreen.setLevelSet(levels);
        gameScreen.playLevel(0);
        switchScreen(gameScreen, false);
    }

    public void switchScreen(AbstractScreen screen) {
        switchScreen(screen, true);
    }

    public void switchScreen(AbstractScreen screen, boolean dispose) {
        if (screen == null) return;

        if (currentScreen != null && dispose) {
            currentScreen.dispose();
        }

        setScreen(screen);
        currentScreen = screen;
        Gdx.input.setInputProcessor(screen);
    }

    public void openEditor(Level level) {
        if (editorScreen == null)
            editorScreen = new EditorScreen(level, this);

        if (level != editorScreen.getLevel())
            editorScreen.editLevel(level, null);
        
        switchScreen(editorScreen, false);
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
