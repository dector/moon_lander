package ua.org.dector.moon_lander;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.Input.Keys;
import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class GameScreen implements Screen, InputProcessor {
    private Rocket rocket;
    private TextureRegion rocketTexture;

    public GameScreen(Rocket rocket) {
        this.rocket = rocket;

        Texture graphicsTexture = ResourceLoader.loadTexture(GRAPHICS_FILE);
        rocketTexture = new TextureRegion(graphicsTexture,
                ROCKET_TEXTURE_WIDTH, ROCKET_TEXTURE_HEIGHT);
    }

    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Gdx.app.exit();

        rocket.updateRocket(delta);

        Graphics.clear();

        Graphics.begin();
        Graphics.draw(
                rocketTexture,
                rocket.getX(),
                rocket.getY(),
                ROCKET_WIDTH,
                ROCKET_HEIGHT,
                rocket.getDirectionAngle()
        );
        Graphics.draw(
                10, SCREEN_HEIGHT - 10, 15,
                String.format("X: %d", (int)rocket.getX()),
                String.format("Y: %d", (int)rocket.getY()),
                String.format("Vx: %.2f", rocket.getVx()),
                String.format("Vy: %.2f", rocket.getVy()),
                String.format("Angle: %.1f", rocket.getDirectionAngle())
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

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.UP:
                rocket.moveUp(true);
                break;
            case Keys.LEFT:
                rocket.rotateLeft(true);
                break;
            case Keys.RIGHT:
                rocket.rotateRight(true);
                break;
        }

        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.UP:
                rocket.moveUp(false);
                break;
            case Keys.LEFT:
                rocket.rotateLeft(false);
                break;
            case Keys.RIGHT:
                rocket.rotateRight(false);
                break;
        }

        return true;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    public boolean touchMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
}
