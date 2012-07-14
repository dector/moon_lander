package ua.org.dector.gcore.managers;

/**
 * @author dector (dector9@gmail.com)
 */
public abstract class AudioManager {
    public static final float MAX_VOLUME    = 1;
    public static final float NORMAL_VOLUME = 0.5f;
    public static final float MIN_VOLUME    = 0;

    private float volume;
    private boolean enabled;

    public AudioManager() {
        this(MAX_VOLUME);
    }

    public AudioManager(float volume) {
        this(volume, true);
    }

    public AudioManager(float volume, boolean enabled) {
        this.volume = volume;
        this.enabled = enabled;

        init();
    }

    protected abstract void init();

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggleMuted() {
        setEnabled(!isEnabled());
    }

    public abstract void disposeAll();
}
