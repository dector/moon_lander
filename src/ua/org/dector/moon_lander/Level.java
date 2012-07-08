package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class Level {
    private int width;
    private int height;

    private int[] map;

    private int[] land;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMapLength() {
        return map.length;
    }

    public int getPointsCount() {
        return map.length / 2;
    }

    public static Level fromFile(String fileName) {
        FileHandle file = Gdx.files.internal(DATA_DIR + LEVELS_DIR + fileName);
        return new Json().fromJson(Level.class, file);
    }

    public int get(int i) {
        return map[i];
    }

    public int getLendingLeftX() {
        return land[0];
    }

    public int getLendingRightX() {
        return land[2];
    }

    public int getLendingBottomY() {
        return land[1];
    }

    public int getLendingTopY() {
        return land[3];
    }
}
