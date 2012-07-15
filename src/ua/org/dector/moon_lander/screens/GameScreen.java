package ua.org.dector.moon_lander.screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ua.org.dector.gcore.common.Settings;
import ua.org.dector.gcore.game.AbstractScreen;
import ua.org.dector.gcore.managers.SoundManager;
import ua.org.dector.moon_lander.*;
import ua.org.dector.moon_lander.graphics.HUDRenderer;
import ua.org.dector.moon_lander.graphics.NewLevelRenderer;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.utils.LevelRenderer;

import java.awt.Rectangle;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class GameScreen extends AbstractScreen<LanderGame> {
    private Rocket rocket;
    private Level[] levels;
    private Level level;
    private int levelIndex;

    private boolean collided;
    private boolean landed;

    private boolean paused;

    private EntityController entityController;
    private LevelRenderer levelRenderer;

    private NewLevelRenderer newLevelRenderer;
    private HUDRenderer hudRenderer;

    public GameScreen(LanderGame game, Level[] levels) {
        super(game);

        newLevelRenderer = new NewLevelRenderer(game);
        hudRenderer = new HUDRenderer(game);

        rocket = new Rocket();
        setLevelSet(levels);

        loadSounds();
        loadMusic();

        entityController = new EntityController(game, rocket);
//        levelRenderer = new LevelRenderer(game, rocket);    // #render

        playLevel(0);

        reset();

        // TODO fix
//        game.getSoundManager().playMusic();
    }

    private void loadMusic() {
        Music music = game.getResourceLoader().loadMusic(MUSIC_FILE);
        game.getMusicManager().setMusic(music, true);
        game.getMusicManager().play();
    }

    public void dispose() {
        super.dispose();

        game.getMusicManager().disposeAll();
        game.getSoundManager().disposeAll();
    }

    private void loadSounds() {
        SoundManager sm = game.getSoundManager();

        Sound burnSound = game.getResourceLoader().loadSound(BURN_FILE);
        Sound crashSound = game.getResourceLoader().loadSound(CRASH_FILE);
        Sound landingSound = game.getResourceLoader().loadSound(LANDING_FILE);

        sm.addSound(Sounds.BURN, burnSound);
        sm.addSound(Sounds.CRASH, crashSound);
        sm.addSound(Sounds.LAND, landingSound);
    }

    public void playLevel(int levelIndex) {
        if (levelIndex < levels.length) {
            this.levelIndex = levelIndex;
            level = levels[levelIndex];

//            levelRenderer.setLevel(level);  // #render
            reset();
        }
    }

    private void reset() {
        rocket.reset(level.getRocketX(), level.getRocketY(), level.getRocketAngle());

//        levelRenderer.reset();  // #render

        collided = false;
        landed = false;

        paused = false;
    }

    public void render(float delta) {
        if (! collided && ! paused) {
            updateCollisions();
            rocket.updateRocket(delta);
        }

//        levelRenderer.render(   // #render
//                game.getSoundManager().isEnabled(),
//                paused,
//                collided,
//                landed,
//                levelIndex != levels.length - 1
//        );

        game.getGraphics().begin();
        hudRenderer.render(game.getGraphics());
        game.getGraphics().end();
    }

    private void updateCollisions() {
        int rocketLeftX = (int)rocket.getX();
        int rocketRightX = (int)rocket.getX() + ROCKET_WIDTH;
        int rocketBottomY = (int)rocket.getY();

        if (rocketRightX < 0) {
            if (rocketBottomY <= level.get(1))
                collided = true;
        } else if (level.getWidth() < rocketLeftX) {
            if (rocketBottomY <= level.get(level.getMapLength() - 1))
                collided = true;
        } else {
            int[] leftPoint = new int[2];
            int[] rightPoint = new int[2];

            int pointsCount = level.getPointsCount();

            int leftBound = 0;
            int rightBound = pointsCount - 1;

            // Find left and right point

            while (leftBound + 1 < pointsCount
                    && level.get(2 * (leftBound + 1)) <= rocketLeftX) {
                leftBound++;
            }

            while (0 <= rightBound - 1
                    && rocketRightX <= level.get(2 * (rightBound - 1))) {
                rightBound--;
            }

            for (int i = leftBound; i < rightBound && ! collided; i++) {
                leftPoint[0] = level.get(2 * i);
                leftPoint[1] = level.get(2 * i + 1);

                rightPoint[0] = level.get(2 * (i + 1));
                rightPoint[1] = level.get(2 * (i + 1) + 1);

                // Check intersection
                collided = new Rectangle(
                        (int)rocket.getX(),
                        (int)rocket.getY(),
                        ROCKET_WIDTH,
                        ROCKET_HEIGHT
                ).intersectsLine(
                        leftPoint[0],
                        leftPoint[1],
                        rightPoint[0],
                        rightPoint[1]
                );
            }
        }
        
        if (collided) {
            landed = Math.max(level.getLandingLeftX() - rocketLeftX,
                    rocketRightX - level.getLandingRightX()) < ROCKET_WIDTH / 5
                    && Math.abs(rocket.getVx()) <= LANDING_VX_BOUND
                    && Math.abs(rocket.getVy()) <= LANDING_VY_BOUND
                    && Math.abs(rocket.getDirectionAngle() - 90) <= LANDING_DIFF_ANGLE;

            if (landed) {
                entityController.land();
            } else {
                entityController.crash();
            }
        }
    }

    public boolean keyDown(int keycode) {
        Settings gameSettings = game.getSettings();
        int screenWidth = gameSettings.getScreenWidth();
        int screenHeight = gameSettings.getScreenHeight();

        switch (keycode) {
            case Keys.UP:       entityController.moveUpRocket(true);        break;
            case Keys.LEFT:     entityController.rotateRocketLeft(true);    break;
            case Keys.RIGHT:    entityController.rotateRocketRight(true);   break;
            case Keys.ESCAPE:   Gdx.app.exit();                             break;
            case Keys.R:        reset();                                    break;
            case Keys.SPACE:
                if (collided) {
                    if (landed) {
                        playLevel(++levelIndex);
                    }

                    reset();
                }                                                           break;
            case Keys.N:
                if (game.isDebug()) {
                    playLevel(++levelIndex);
                    reset();
                }                                                           break;
            case Keys.M: {
                game.getSoundManager().toggleMuted();
                game.getMusicManager().toggleMuted();
            } break;
            case Keys.P:    paused = ! paused;                              break;
            case Keys.E:
                if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                    Level newLevel = new Level(screenWidth, screenHeight);
                    newLevel.setRocketParams(screenWidth / 2, screenHeight, 90);
                    ((LanderGame) game).openEditor(
                            newLevel,
                            new Rocket()
                    );
                } else {
                    reset();
                    ((LanderGame) game).openEditor(level, rocket);
                }                                                           break;
        }

        return true;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.UP:       entityController.moveUpRocket(false);       break;
            case Keys.LEFT:     entityController.rotateRocketLeft(false);   break;
            case Keys.RIGHT:    entityController.rotateRocketRight(false);  break;
        }

        return true;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        int screenHeight = game.getSettings().getScreenHeight();

        if (game.isDebug()) {
            rocket.setPosition(x, screenHeight - y);

            return true;
        } else
            return false;
    }

    public void setLevelSet(Level[] levels) {
        this.levels = levels;
    }
}
