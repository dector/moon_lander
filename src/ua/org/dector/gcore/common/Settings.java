package ua.org.dector.gcore.common;

import ua.org.dector.gcore.managers.PreferencesManager;

/**
 * @author dector (dector9@gmail.com)
 */
public class Settings {
    private static final String MUSIC_VOLUME    = "music.volume";
    private static final String MUSIC_ENABLED   = "music.enabled";
    private static final String SFX_VOLUME      = "sfx.volume";
    private static final String SFX_ENABLED     = "sfx.enabled";

    private static final float DEFAULT_MUSIC_VOLUME     = 1;
    private static final boolean DEFAULT_MUSIC_ENABLED  = true;
    private static final float DEFAULT_SFX_VOLUME       = 1;
    private static final boolean DEFAULT_SFX_ENABLED    = true;

    private PreferencesManager prefs;

    public Settings(String filename) {
        prefs = new PreferencesManager(filename);
    }

    public float getMusicVolume() {
        return prefs.getFloat(MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
    }

    public void setMusicVolume(float volume) {
        prefs.putFloat(MUSIC_VOLUME, volume);
    }

    public float getSfxVolume() {
        return prefs.getFloat(SFX_VOLUME, DEFAULT_SFX_VOLUME);
    }

    public void setSfxVolume(float volume) {
        prefs.putFloat(SFX_VOLUME, volume);
    }

    public boolean getMusicEnabled() {
        return prefs.getBoolean(MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED);
    }

    public void setMusicEnabled(boolean enabled) {
        prefs.putBoolean(MUSIC_ENABLED, enabled);
    }

    public boolean getSfxEnabled() {
        return prefs.getBoolean(SFX_ENABLED, DEFAULT_SFX_ENABLED);
    }

    public void setSfxEnabled(boolean enabled) {
        prefs.putBoolean(SFX_ENABLED, enabled);
    }
}
