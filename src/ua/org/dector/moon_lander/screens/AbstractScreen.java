package ua.org.dector.moon_lander.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import ua.org.dector.moon_lander.managers.GameManagers;

/**
 * @author dector (dector9@gmail.com)
 */
public class AbstractScreen implements Screen, InputProcessor {
    protected GameManagers gameManagers;

    public AbstractScreen(GameManagers gameManagers) {
        this.gameManagers = gameManagers;
    }

    public void render(float delta) {}

    public void resize(int width, int height) {}

    public void show() {}

    public void hide() {}

    public void pause() {}

    public void resume() {}

    public void dispose() {}

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
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
