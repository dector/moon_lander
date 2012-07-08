package ua.org.dector.moon_lander;

/**
 * @author dector (dector9@gmail.com)
 */
public interface AppConfig {
    public static final String TITLE        = "Moon Lander";
    public static final int SCREEN_WIDTH    = 800;
    public static final int SCREEN_HEIGHT   = 600;

    // Files

    public static final String DATA_DIR     = "data/";
    public static final String IMG_DIR      = "img/";
    public static final String LEVELS_DIR   = "levels/";
    public static final String GRAPHICS_FILE= "graphics.png";

    // Graphics

    public static final int ROCKET_TEXTURE_WIDTH    = 8;
    public static final int ROCKET_TEXTURE_HEIGHT   = 8;

    public static final int FIRE_TEXTURE_WIDTH      = 8;
    public static final int FIRE_TEXTURE_HEIGHT     = 8;

    public static final int POINTER_TEXTURE_WIDTH   = 8;
    public static final int POINTER_TEXTURE_HEIGHT  = 8;

    public static final int FLAG_TEXTURE_WIDTH      = 8;
    public static final int FLAG_TEXTURE_HEIGHT     = 8;


    public static final int ROCKET_WIDTH    = 32;
    public static final int ROCKET_HEIGHT   = 32;

    public static final int FIRE_PADDING    = 8;
    public static final int FIRE_WIDTH      = 16;
    public static final int FIRE_HEIGHT     = 24;

    public static final int POINTER_WIDTH   = 8;
    public static final int POINTER_HEIGHT  = 16;

    public static final int FLAG_WIDTH      = 32;
    public static final int FLAG_HEIGHT     = 32;


    public static final int LANDING_PLATFORM_BORDER = 3;
    public static final int LANDING_PLATFORM_HEIGHT = 6;

    // Physics

    public static final int ROCKET_AY       = 3;
    public static final int ROCKET_ROTATING = 90;
    public static final int GRAVITY         = 1;
    public static final float FRICTION      = .7f;

    public static final float LANDING_VX_BOUND  = .5f;
    public static final float LANDING_VY_BOUND  = .5f;
    public static final int LANDING_DIFF_ANGLE  = 5;
}
