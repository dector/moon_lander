package ua.org.dector.moon_lander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class LanderGame extends Game {
    private GameScreen gameScreen;
    private Rocket rocket;

    public void create() {
        rocket = new Rocket();
        gameScreen = new GameScreen(rocket);
        Graphics.init();

        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }
}
