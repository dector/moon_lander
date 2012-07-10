package ua.org.dector.moon_lander.managers;

import com.badlogic.gdx.audio.Music;
import ua.org.dector.moon_lander.ResourceLoader;

import static ua.org.dector.moon_lander.AppConfig.MUSIC_FILE;
import static ua.org.dector.moon_lander.AppConfig.MUSIC_VOLUME;

/**
 * @author dector (dector9@gmail.com)
 */
public class SoundManager {
    private boolean muted;

    private Music music;

    public SoundManager() {
        loadMusic();
    }

    private void loadMusic() {
        music = ResourceLoader.loadMusic(MUSIC_FILE);

        music.setLooping(true);
        music.setVolume(MUSIC_VOLUME);
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        if (muted != isMuted()) {
            if (muted) {
                music.pause();
            } else {
                music.play();
            }
        }

        this.muted = muted;
    }

    public void toggleMuted() {
        setMuted(! isMuted());
    }

    public void playMusic() {
        music.play();
    }
}
