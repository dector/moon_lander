package ua.org.dector.moon_lander;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ua.org.dector.moon_lander.managers.GameManagers;
import ua.org.dector.moon_lander.managers.SoundManager;
import ua.org.dector.moon_lander.models.Rocket;

import static ua.org.dector.moon_lander.AppConfig.*;
import static ua.org.dector.moon_lander.managers.SoundManager.SoundEvent;

/**
 * @author dector (dector9@gmail.com)
 */
public class EntityController {
    private Rocket rocket;

    private SoundManager soundManager;

    public EntityController(GameManagers gameManagers, Rocket rocket) {
        this.rocket = rocket;

        soundManager = gameManagers.getSoundManager();

        loadSounds();
    }

    private void loadSounds() {

    }

    public void moveUpRocket(boolean moveUp){
        rocket.moveUp(moveUp);

        if (moveUp) {
            soundManager.playEvent(SoundEvent.BURN);
        } else {
            soundManager.stopEvent(SoundEvent.BURN);
        }
    }

    public void land() {
        soundManager.playEvent(SoundEvent.LAND);
    }

    public void crash() {
        soundManager.playEvent(SoundEvent.CRASH);
    }

    public void rotateRocketLeft(boolean rotateLeft) {
        rocket.rotateLeft(rotateLeft);
    }

    public void rotateRocketRight(boolean rotateRight) {
        rocket.rotateRight(rotateRight);
    }
}
