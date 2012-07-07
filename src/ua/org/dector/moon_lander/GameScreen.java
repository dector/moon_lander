package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.Input.Keys;
import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class GameScreen implements Screen {
    private LanderGame game;
    private TextureRegion rocketTexture;

    public GameScreen(LanderGame game) {
        this.game = game;

        Texture graphicsTexture = ResourceLoader.loadTexture(GRAPHICS_FILE);
        rocketTexture = new TextureRegion(graphicsTexture,
                ROCKET_TEXTURE_WIDTH, ROCKET_TEXTURE_HEIGHT);
    }

    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Gdx.app.exit();

        Graphics.begin();
        Graphics.draw(
                rocketTexture,
                game.getRocketX(),
                game.getRocketY(),
                ROCKET_WIDTH,
                ROCKET_HEIGHT
        );
        Graphics.end();
    }

    public void resize(int width, int height) {
    }

    public void show() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
