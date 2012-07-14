package ua.org.dector.gcore.core;

import com.badlogic.gdx.Game;

/**
 * Base game application
 *
 * @author dector (dector9@gmail.com)
 */
public abstract class AbstractGame extends Game {
    private SoundManager soundManager;

    public void create() {
        soundManager = new SoundManager();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}
