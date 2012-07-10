package ua.org.dector.moon_lander.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;
import ua.org.dector.moon_lander.*;
import ua.org.dector.moon_lander.Graphics;
import ua.org.dector.moon_lander.managers.GameManagers;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.utils.LevelRenderer;

import java.awt.Rectangle;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class GameScreen extends AbstractScreen {
    private Rocket rocket;
    private Level[] levels;
    private Level level;
    private int levelIndex;

    private boolean collided;
    private boolean landed;

    private boolean paused;

    private boolean debug = false;

    private EntityController entityController;
    private LevelRenderer levelRenderer;

    public GameScreen(GameManagers gameManagers, Rocket rocket, Level[] levels) {
        super(gameManagers);

        this.rocket = rocket;
        this.levels = levels;

        entityController = new EntityController(gameManagers, rocket);
        levelRenderer = new LevelRenderer(rocket);

        playLevel(0);

        reset();

        gameManagers.getSoundManager().playMusic();
    }

    private void playLevel(int levelIndex) {
        if (levelIndex < levels.length) {
            this.levelIndex = levelIndex;
            level = levels[levelIndex];

            levelRenderer.setLevel(level);
        }
    }

    private void reset() {
        rocket.reset(level.getRocketX(), level.getRocketY(), level.getRocketAngle());

        levelRenderer.reset();

        collided = false;
        landed = false;

        paused = false;
    }

    public void render(float delta) {
        if (! collided && ! paused) {
            updateCollisions();
            rocket.updateRocket(delta);
        }

        levelRenderer.render(
                gameManagers.getSoundManager().isMuted(),
                paused,
                collided,
                landed,
                levelIndex != levels.length - 1
        );
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
                if (debug) {
                    playLevel(++levelIndex);
                    reset();
                }                                                           break;
            case Keys.M:    gameManagers.getSoundManager().toggleMuted();   break;
            case Keys.P:    paused = ! paused;                              break;
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
        if (debug) {
            rocket.setPosition(x, SCREEN_HEIGHT - y);

            return true;
        } else
            return false;
    }
}
