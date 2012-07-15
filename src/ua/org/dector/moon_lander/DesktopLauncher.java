package ua.org.dector.moon_lander;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class DesktopLauncher implements Runnable {
    public static final int INIT_SCREEN_WIDTH   = 1;
    public static final int INIT_SCREEN_HEIGHT  = 1;
    public static final boolean INIT_FULLSCREEN = false;

    public void run() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = INIT_SCREEN_WIDTH;
        config.height = INIT_SCREEN_HEIGHT;
        config.fullscreen = INIT_FULLSCREEN;
        config.title = TITLE;
        config.resizable = false;
        config.useGL20 = false;

        new LwjglApplication(new LanderGame(), config);
    }

    public static void main(String[] args) {
        new DesktopLauncher().run();
    }
}
