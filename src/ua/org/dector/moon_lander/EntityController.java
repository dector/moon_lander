package ua.org.dector.moon_lander;

import ua.org.dector.gcore.managers.SoundManager;
import ua.org.dector.moon_lander.models.Rocket;

/**
 * @author dector (dector9@gmail.com)
 */
public class EntityController {
    private Rocket rocket;

    private SoundManager soundManager;

    public EntityController(LanderGame game, Rocket rocket) {
        this.rocket = rocket;

        soundManager = game.getSoundManager();

        loadSounds();
    }

    private void loadSounds() {

    }

    public void moveUpRocket(boolean moveUp){
        rocket.setEngineOn(moveUp);

        if (moveUp) {
            soundManager.loop(Sounds.BURN);
        } else {
            soundManager.stop(Sounds.BURN);
        }
    }

    public void land() {
        soundManager.play(Sounds.LAND);
    }

    public void crash() {
        soundManager.play(Sounds.CRASH);
    }

    public void rotateRocketLeft(boolean rotateLeft) {
        rocket.rotateLeft(rotateLeft);
    }

    public void rotateRocketRight(boolean rotateRight) {
        rocket.rotateRight(rotateRight);
    }
}
