package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author dector (dector9@gmail.com)
 */
public class Graphics {
    private static SpriteBatch sb = new SpriteBatch();
    private static BitmapFont font = new BitmapFont();

    private static GL10 gl10 = Gdx.graphics.getGL10();

    public static void init() {
        gl10.glClearColor(0, 0, 0, 1);
    }

    public static void begin() {
        sb.begin();
    }

    public static void end() {
        sb.end();
    }

    public static void draw(TextureRegion tex, float x, float y) {
        sb.draw(tex, x, y);
    }

    public static void draw(TextureRegion tex, float x, float y,
                            int width, int height) {
        sb.draw(tex, x, y, width, height);
    }

    public static void draw(TextureRegion tex, float x, float y,
                            int width, int height, float rotation) {
        draw(tex, x, y, width / 2, height / 2, width, height, rotation);
    }

    public static void draw(TextureRegion tex, float x, float y, int originX, int originY,
                            int width, int height, float rotation) {
        sb.draw(tex, x, y, originX, originY, width, height, 1, 1, rotation - 90);
    }

    public static void draw(String string, int x, int y) {
        font.draw(sb, string, x, y);
    }

    public static void draw(int x, int y, int stepY, String... strings) {
        for (String s : strings) {
            draw(s, x, y);
            y -= stepY;
        }
    }

    public static void clear() {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
