package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.openal.Wav;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

import java.lang.reflect.Array;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class ResourceLoader {
    public static Texture loadTexture(String image) {
        FileHandle file = Gdx.files.internal(DATA_DIR + IMG_DIR + image);
        return new Texture(file);
    }

    public static Sound loadSound(String sound) {
        FileHandle file = Gdx.files.internal(DATA_DIR + SOUND_DIR + sound);
        return Gdx.audio.newSound(file);
    }

    public static Music loadMusic(String music) {
        FileHandle file = Gdx.files.internal(DATA_DIR + SOUND_DIR + music);
        return Gdx.audio.newMusic(file);
    }

    public static Level loadLevel(String level) {
        return Level.fromFile(DATA_DIR + LEVELS_DIR + level);
    }

    public static TextureRegion loadLevelTexture(String image) {
        FileHandle file = Gdx.files.internal(DATA_DIR + LEVELS_DIR + image);
        Pixmap pixmap = new Pixmap(file);

        Pixmap bigPixmap = new Pixmap(
                Utils.toPowerOfTwo(pixmap.getWidth()),
                Utils.toPowerOfTwo(pixmap.getHeight()),
                Pixmap.Format.RGBA8888
        );
        bigPixmap.drawPixmap(pixmap, 0, 0);

        return new TextureRegion(
                new Texture(bigPixmap),
                pixmap.getWidth(),
                pixmap.getHeight()
        );
    }


    public static BitmapFont loadFont(String font, String fontImg) {
        FileHandle fontFile = Gdx.files.internal(DATA_DIR + FONTS_DIR + font);
        FileHandle imageFile = Gdx.files.internal(DATA_DIR + FONTS_DIR + fontImg);
        return new BitmapFont(fontFile, imageFile, false);
    }

    public static Level[] loadLevelSet(String levelSet) {
        FileHandle file = Gdx.files.internal(DATA_DIR + LEVELS_DIR + levelSet);
        String[] levelNames = new Json().fromJson(String[].class, file);

        Level[] levels = new Level[levelNames.length];

        for (int i = 0; i < levelNames.length; i++) {
            levels[i] = loadLevel(levelNames[i]);
        }

        return levels;
    }
}
