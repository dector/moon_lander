package ua.org.dector.moon_lander.managers;

/**
 * @author dector (dector9@gmail.com)
 */
public class GameManagers {
    private SoundManager soundManager;

    public GameManagers() {
        soundManager = new SoundManager();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}
