package ua.org.dector.moon_lander.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ua.org.dector.moon_lander.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

import static ua.org.dector.moon_lander.AppConfig.MUSIC_FILE;
import static ua.org.dector.moon_lander.AppConfig.MUSIC_VOLUME;
import static ua.org.dector.moon_lander.AppConfig.SFX_VOLUME;

/**
 * @author dector (dector9@gmail.com)
 */
public class SoundManager {
    private boolean muted;

    private Map<SoundEvent, Sound> sounds;

    private Music music;

    public SoundManager() {
        sounds = new HashMap<SoundEvent, Sound>();

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

    public void addSound(SoundEvent event, Sound sound) {
        if (! sounds.containsKey(event)) {
            sounds.put(event, sound);
        }
    }

    public void playEvent(SoundEvent event) {
        playEvent(event, false);
    }

    public void playEvent(SoundEvent event, boolean loop) {
        if (! isMuted() && sounds.containsKey(event)) {
            Sound sound = sounds.get(event);

            if (loop) {
                sound.loop(SFX_VOLUME);
            } else {
                sound.play(SFX_VOLUME);

            }
        }
    }

    public void stopEvent(SoundEvent event) {
        if (! isMuted() && sounds.containsKey(event)) {
            sounds.get(event).stop();
        }
    }

    public static enum SoundEvent {
        FADE_IN, BURN, LAND, BURNBURN, CRASH
    }
}
