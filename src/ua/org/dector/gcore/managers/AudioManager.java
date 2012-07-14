package ua.org.dector.gcore.managers;

/**
 * @author dector (dector9@gmail.com)
 */
public abstract class AudioManager {
    public static final float MAX_VOLUME    = 1;
    public static final float NORMAL_VOLUME = 0.5f;
    public static final float MIN_VOLUME    = 0;

    private float volume;
    private boolean muted;

    public AudioManager() {
        this(MAX_VOLUME);
    }

    public AudioManager(float volume) {
        this(volume, false);
    }

    public AudioManager(float volume, boolean muted) {
        this.volume = volume;
        this.muted = muted;

        init();
    }

    protected abstract void init();

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void toggleMuted() {
        setMuted(! isMuted());
    }

    public abstract void disposeAll();
}
