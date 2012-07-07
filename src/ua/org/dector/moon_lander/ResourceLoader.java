package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class ResourceLoader {
    public static Texture loadTexture(String image) {
        FileHandle file = Gdx.files.internal(DATA_DIR + IMG_DIR + image);
        return new Texture(file);
    }
}
