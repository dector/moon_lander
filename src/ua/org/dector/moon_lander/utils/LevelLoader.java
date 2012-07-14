package ua.org.dector.moon_lander.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import ua.org.dector.moon_lander.models.Level;

import static ua.org.dector.moon_lander.AppConfig.DATA_DIR;
import static ua.org.dector.moon_lander.AppConfig.LEVELS_DIR;

/**
 * @author dector (dector9@gmail.com)
 */
public class LevelLoader {
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
