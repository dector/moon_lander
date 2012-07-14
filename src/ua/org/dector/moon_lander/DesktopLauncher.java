package ua.org.dector.moon_lander;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class DesktopLauncher implements Runnable {
    public void run() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // TODO fix this hack
        config.width = 800;
        config.height = 600;
        config.fullscreen = false;
        config.title = TITLE;
        config.resizable = false;
        config.useGL20 = false;

        new LwjglApplication(new LanderGame(), config);
    }

    public static void main(String[] args) {
        new DesktopLauncher().run();
    }
}
