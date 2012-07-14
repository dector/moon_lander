package ua.org.dector.gcore.managers;

import com.badlogic.gdx.audio.Music;

/**
 * @author dector (dector9@gmail.com)
 */
public class MusicManager extends AudioManager {
    private Music music;

    protected void init() {}

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music, boolean loop) {
        if (music == null) return;

        music.setLooping(loop);
        music.setVolume(getVolume());

        this.music = music;
    }

    public void play() {
        if (musicIsNull()) return;
        if (getMusic().isPlaying()) return;
        if (! isEnabled()) return;

        getMusic().play();
    }

    public void pause() {
        if (musicIsNull()) return;
        if (! getMusic().isPlaying()) return;

        getMusic().pause();
    }

    public void setVolume(float volume) {
        super.setVolume(volume);

        if (musicIsNull()) return;

        getMusic().setVolume(getVolume());
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (isEnabled())
            play();
        else
            pause();
    }

    public void disposeAll() {
        if (musicIsNull()) return;

        music.dispose();
        music = null;
    }

    private boolean musicIsNull() {
        return getMusic() == null;
    }
}
