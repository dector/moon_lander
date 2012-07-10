package ua.org.dector.moon_lander;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ua.org.dector.moon_lander.managers.GameManagers;
import ua.org.dector.moon_lander.models.Rocket;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class EntityController {
    private Rocket rocket;

    private Sound burnSound;
    private Sound crashSound;
    private Sound landingSound;

    private GameManagers gameManagers;

    public EntityController(GameManagers gameManagers, Rocket rocket) {
        this.gameManagers = gameManagers;
        this.rocket = rocket;

        loadSounds();
    }

    private void loadSounds() {
        burnSound = ResourceLoader.loadSound(BURN_FILE);
        crashSound = ResourceLoader.loadSound(CRASH_FILE);
        landingSound = ResourceLoader.loadSound(LANDING_FILE);
    }

    public void moveUpRocket(boolean moveUp){
        rocket.moveUp(moveUp);
        if (moveUp) {
            if (! gameManagers.getSoundManager().isMuted())
                burnSound.loop(SFX_VOLUME);
        } else {
            burnSound.stop();
        }
    }

    public void land() {
        if (! gameManagers.getSoundManager().isMuted()) {
            landingSound.play(SFX_VOLUME);
        }
    }

    public void crash() {
        if (! gameManagers.getSoundManager().isMuted()) {
            crashSound.play(SFX_VOLUME);
        }
    }

    public void rotateRocketLeft(boolean rotateLeft) {
        rocket.rotateLeft(rotateLeft);
    }

    public void rotateRocketRight(boolean rotateRight) {
        rocket.rotateRight(rotateRight);
    }
}
