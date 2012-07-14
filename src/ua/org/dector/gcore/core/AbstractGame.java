package ua.org.dector.gcore.core;

import com.badlogic.gdx.Game;

/**
 * Base game application
 *
 * @author dector (dector9@gmail.com)
 */
public abstract class AbstractGame extends Game {
    private SoundManager soundManager;
    private MusicManager musicManager;

    public void create() {
        soundManager = new SoundManager();
        musicManager = new MusicManager();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }
}
