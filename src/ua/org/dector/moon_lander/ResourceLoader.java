package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class ResourceLoader {
    public static Texture loadTexture(String image) {
        FileHandle file = Gdx.files.internal(DATA_DIR + IMG_DIR + image);
        return new Texture(file);
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
}
