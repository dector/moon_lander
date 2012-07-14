package ua.org.dector.moon_lander.managers;

import com.badlogic.gdx.Game;

/**
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
