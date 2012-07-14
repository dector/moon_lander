package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.gcore.core.ResourceLoader;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class Graphics {
    private SpriteBatch sb;
    private BitmapFont defaultFont;
    private BitmapFont smallFont;
    private BitmapFont bigFont;

    private GL10 gl10;

    public Graphics(LanderGame game) {
        sb = new SpriteBatch();

        defaultFont = new BitmapFont();
        smallFont = game.getResourceLoader().loadFont(SMALL_FONT_FILE, SMALL_FONT_IMG);
        bigFont = game.getResourceLoader().loadFont(BIG_FONT_FILE, BIG_FONT_IMG);

        gl10 = Gdx.graphics.getGL10();
        setClearColor(0, 0, 0, 1);
    }

    public void setClearColor(Color c) {
        setClearColor(c.r, c.g, c.b, c.a);
    }

    public void setClearColor(float r, float g, float b, float a) {
        gl10.glClearColor(r, g, b, a);
    }

    public void begin() {
        sb.begin();
    }

    public void end() {
        sb.end();
    }

    public void draw(TextureRegion tex, float x, float y) {
        sb.draw(tex, x, y);
    }

    public void draw(TextureRegion tex, float x, float y,
                            int width, int height) {
        sb.draw(tex, x, y, width, height);
    }

    public void draw(TextureRegion tex, float x, float y,
                            int width, int height, float rotation) {
        draw(tex, x, y, width / 2, height / 2, width, height, rotation);
    }

    public void draw(TextureRegion tex, float x, float y, int originX, int originY,
                            int width, int height, float rotation) {
        sb.draw(tex, x, y, originX, originY, width, height, 1, 1, rotation - 90);
    }

    public void draw(String string, int x, int y) {
        draw(string, x, y, FontSize.DEFAULT);
    }

    public void drawCentered(String string, int x, int y, FontSize size) {
        BitmapFont font = getFont(size);
        BitmapFont.TextBounds bounds = font.getBounds(string);

        x -= bounds.width / 2;
        y += bounds.height / 2;

        draw(string, x, y, size);
    }

    private BitmapFont getFont(FontSize size) {
        BitmapFont font;

        switch (size) {
            case SMALL:
                font = smallFont;
                break;
            case BIG:
                font = bigFont;
                break;
            case DEFAULT:
            default:
                font = defaultFont;
                break;
        }

        return font;
    }

    public void draw(String string, int x, int y, FontSize size) {
        getFont(size).draw(sb, string, x, y);
    }

    public void draw(int x, int y, int stepY, String... strings) {
        for (String s : strings) {
            draw(s, x, y, FontSize.SMALL);
            y -= stepY;
        }
    }

    public void clear() {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public static enum FontSize {
        DEFAULT, SMALL, BIG
    }
}
