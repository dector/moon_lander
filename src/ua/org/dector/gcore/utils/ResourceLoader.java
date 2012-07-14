package ua.org.dector.gcore.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * @author dector (dector9@gmail.com)
 */
public class ResourceLoader {
    private String imagesDirPath;
    private String particlesDirPath;
    private String soundsDirPath;
    private String musicDirPath;
    private String fontsDirPath;

    public String getImagesDirPath() {
        return imagesDirPath;
    }

    public void setImagesDirPath(String imagesDirPath) {
        this.imagesDirPath = imagesDirPath;
    }

    public String getParticlesDirPath() {
        return particlesDirPath;
    }

    public void setParticlesDirPath(String particlesDirPath) {
        this.particlesDirPath = particlesDirPath;
    }

    public String getSoundsDirPath() {
        return soundsDirPath;
    }

    public void setSoundsDirPath(String soundsDirPath) {
        this.soundsDirPath = soundsDirPath;
    }

    public String getMusicDirPath() {
        return musicDirPath;
    }

    public void setMusicDirPath(String musicDirPath) {
        this.musicDirPath = musicDirPath;
    }

    public String getFontsDirPath() {
        return fontsDirPath;
    }

    public void setFontsDirPath(String fontsDirPath) {
        this.fontsDirPath = fontsDirPath;
    }

    public Texture loadTexture(String image) {
        FileHandle file = Gdx.files.internal(imagesDirPath + image);
        return new Texture(file);
    }

    public ParticleEffect loadParticleEffect(String effect) {
        FileHandle particlesDir = Gdx.files.internal(particlesDirPath);
        FileHandle file = Gdx.files.internal(particlesDir + effect);

        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(file, particlesDir);
        return particleEffect;
    }

    public Sound loadSound(String sound) {
        FileHandle file = Gdx.files.internal(soundsDirPath + sound);
        return Gdx.audio.newSound(file);
    }

    public Music loadMusic(String music) {
        FileHandle file = Gdx.files.internal(musicDirPath + music);
        return Gdx.audio.newMusic(file);
    }

    public BitmapFont loadFont(String font, String fontImg) {
        FileHandle fontFile = Gdx.files.internal(fontsDirPath + font);
        FileHandle imageFile = Gdx.files.internal(fontsDirPath + fontImg);
        return new BitmapFont(fontFile, imageFile, false);
    }
}
