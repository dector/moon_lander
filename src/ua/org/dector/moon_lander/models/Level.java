package ua.org.dector.moon_lander.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import ua.org.dector.moon_lander.AppConfig;

import java.util.Arrays;

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
    private Background background;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Level() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMapLength() {
        if (map != null) {
            return map.length;
        } else {
            return 0;
        }
    }

    public int getPointsCount() {
        return getMapLength() / 2;
    }

    public static Level fromFile(String fileName) {
        FileHandle file = Gdx.files.internal(fileName);
        return new Json().fromJson(Level.class, file);
    }

    public int get(int i) {
        return map[i];
    }

    public boolean hasLanding() {
        return (land != null && land.length > 3);
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

    public Color getBackgroundColor() {
        if (background != null) {
            return new Color(
                    background.color[0],
                    background.color[1],
                    background.color[2],
                    background.color[3]
            );
        } else {
            return null;
        }
    }

    public String getBackgroundImage() {
        if (background != null) {
            return background.image;
        } else {
            return null;
        }
    }

    public boolean hasFlag() {
        return (flag != null && flag.length > 1);
    }

    public void addPoint(int x, int y) {
        if (map == null) {
            map = new int[2];
        } else {
            map = Arrays.copyOf(map, getMapLength() + 2);
        }

        map[getMapLength() - 2] = x;
        map[getMapLength() - 1] = y;
    }

    public void removeLastPoint() {
        if (map != null && getMapLength() > 0) {
            map = Arrays.copyOf(map, getMapLength() - 2);
        }
    }

    public void setFlagPosition(int x, int y) {
        if (flag == null) {
            flag = new int[2];
        }

        flag[0] = x;
        flag[1] = y;
    }

    public void setLand(int x, int y, int landWidth) {
        if (land == null)
            land = new int[4];
        land[0] = x;
        land[1] = y;
        land[2] = x + landWidth;
        land[3] = AppConfig.LANDING_PLATFORM_HEIGHT;
    }

    public static class Background {
        float[] color;
        String image;
    }
}
