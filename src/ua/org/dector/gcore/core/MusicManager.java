package ua.org.dector.gcore.core;

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

        getMusic().play();
    }

    public void pause() {
        if (musicIsNull()) return;

        getMusic().pause();
    }

    public void setVolume(float volume) {
        super.setVolume(volume);

        if (musicIsNull()) return;

        getMusic().setVolume(getVolume());
    }

    public void setMuted(boolean muted) {
        super.setMuted(muted);

        if (isMuted())
            pause();
        else
            play();
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
