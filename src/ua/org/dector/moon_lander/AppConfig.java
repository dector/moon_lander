package ua.org.dector.moon_lander;

/**
 * @author dector (dector9@gmail.com)
 */
public interface AppConfig {
    public static final String TITLE        = "Moon Lander";
    public static final int SCREEN_WIDTH    = 800;
    public static final int SCREEN_HEIGHT   = 600;

    public static final String DATA_DIR     = "data/";
    public static final String IMG_DIR      = "img/";
    public static final String GRAPHICS_FILE= "graphics.png";

    public static final int ROCKET_TEXTURE_WIDTH    = 8;
    public static final int ROCKET_TEXTURE_HEIGHT   = 8;

    public static final int ROCKET_WIDTH    = 32;
    public static final int ROCKET_HEIGHT   = 32;

    public static final int ROCKET_AY       = 3;
    public static final int ROCKET_ROTATING = 90;
    public static final int GRAVITY         = 1;
    public static final float FRICTION      = 0.7f;
}
