package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author dector (dector9@gmail.com)
 */
public class Graphics {
    private static SpriteBatch sb = new SpriteBatch();

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

    public static void draw(TextureRegion tex, int x, int y, int width, int height) {
        sb.draw(tex, x, y, width, height);
    }

    public static void clear() {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
