package ua.org.dector.gcore.managers;

import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dector (dector9@gmail.com)
 */
public class SoundManager extends AudioManager {
    private Map<String, Sound> sounds;

    public SoundManager(float volume) {
        super(volume);
    }

    public SoundManager(float volume, boolean enabled) {
        super(volume, enabled);
    }

    public SoundManager() {
        super();
    }

    protected void init() {
        sounds = new HashMap<String, Sound>();
    }

    public void addSound(String id, Sound sound) {
        if (sound == null) return;
        if (sounds.containsKey(id)) return;

        sounds.put(id, sound);
    }

    public void play(String id) {
        play(id, false);
    }

    public void loop(String id) {
        play(id, true);
    }

    public void play(String id, boolean loop) {
        if (! isEnabled()) return;
        if (! sounds.containsKey(id)) return;

        Sound sound = sounds.get(id);

        if (loop)
            sound.loop(getVolume());
        else
            sound.play(getVolume());
    }

    public void stop(String id) {
        if (! sounds.containsKey(id)) return;

        sounds.get(id).stop();
    }

    public void disposeAll() {
        for (String id : sounds.keySet())
            sounds.get(id).dispose();
    }
}
