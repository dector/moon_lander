package ua.org.dector.gcore.core;

import com.badlogic.gdx.Game;
import ua.org.dector.moon_lander.Graphics;

/**
 * Base game application
 *
 * @author dector (dector9@gmail.com)
 */
public abstract class AbstractGame extends Game {
    private SoundManager soundManager;
    private MusicManager musicManager;
    private ResourceLoader resourceLoader;

    public void create() {
        soundManager = new SoundManager();
        musicManager = new MusicManager();
        resourceLoader = new ResourceLoader();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public abstract Graphics getGraphics();
}
