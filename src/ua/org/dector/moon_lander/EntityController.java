package ua.org.dector.moon_lander;

import com.badlogic.gdx.audio.Sound;
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

    private boolean soundMuted; // TODO Add Sound Manager

    public EntityController(Rocket rocket) {
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
            if (! soundMuted)
                burnSound.loop(SFX_VOLUME);
        } else {
            burnSound.stop();
        }
    }

    public void land() {
        if (! soundMuted) {
            landingSound.play(SFX_VOLUME);
        }
    }

    public void crash() {
        if (! soundMuted) {
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
