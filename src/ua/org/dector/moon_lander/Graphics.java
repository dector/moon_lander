package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author dector (dector9@gmail.com)
 */
public class Graphics {
    private static SpriteBatch sb = new SpriteBatch();

    public static void begin() {
        sb.begin();
    }

    public static void end() {
        sb.end();
    }

    public static void draw(TextureRegion tex, int x, int y, int width, int height) {
        sb.draw(tex, x, y, width, height);
    }
}
