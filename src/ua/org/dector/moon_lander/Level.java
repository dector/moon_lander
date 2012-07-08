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
    private int[] rocket;
    private int[] flag;

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

    public int getLandingLeftX() {
        return land[0];
    }

    public int getLandingRightX() {
        return land[2];
    }

    public int getLandingBottomY() {
        return land[1];
    }

    public int getLandingTopY() {
        return land[3];
    }

    public int getRocketX() {
        return rocket[0];
    }

    public int getRocketY() {
        return rocket[1];
    }

    public float getRocketAngle() {
        return rocket[2];
    }

    public int getFlagX() {
        return flag[0];
    }

    public int getFlagY() {
        return flag[1];
    }
}
