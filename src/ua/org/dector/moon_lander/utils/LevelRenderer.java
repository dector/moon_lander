package ua.org.dector.moon_lander.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.Graphics;
import ua.org.dector.moon_lander.ResourceLoader;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;

import static ua.org.dector.moon_lander.AppConfig.*;
import static ua.org.dector.moon_lander.AppConfig.SOUND_TEXTURE_HEIGHT;
import static ua.org.dector.moon_lander.AppConfig.SOUND_TEXTURE_WIDTH;

/**
 * @author dector (dector9@gmail.com)
 */
public class LevelRenderer {
    private Rocket rocket;
    private Level level;

    private OrthographicCamera camera;

    private TextureRegion rocketTexture;
    private TextureRegion fireTexture;
    private TextureRegion pointerTexture;
    private TextureRegion flagTexture;

    private TextureRegion[] soundTextures;

    private TextureRegion levelTexture;
    private TextureRegion backgroundTexture;

    public LevelRenderer(Rocket rocket) {
        this.rocket = rocket;

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        camera.update();

        Graphics.getSpriteBatch().setProjectionMatrix(camera.combined);

        Texture graphicsTexture = ResourceLoader.loadTexture(GRAPHICS_FILE);
        rocketTexture = new TextureRegion(
                graphicsTexture,
                ROCKET_TEXTURE_WIDTH,
                ROCKET_TEXTURE_HEIGHT
        );
        fireTexture = new TextureRegion(
                graphicsTexture,
                ROCKET_TEXTURE_WIDTH,       // x
                0,                          // y
                FIRE_TEXTURE_WIDTH,
                FIRE_TEXTURE_HEIGHT
        );
        pointerTexture = new TextureRegion(
                graphicsTexture,
                ROCKET_TEXTURE_WIDTH + FIRE_TEXTURE_WIDTH,       // x
                0,                          // y
                POINTER_TEXTURE_WIDTH,
                POINTER_TEXTURE_HEIGHT
        );
        flagTexture = new TextureRegion(
                graphicsTexture,
                ROCKET_TEXTURE_WIDTH + FIRE_TEXTURE_WIDTH
                        + POINTER_WIDTH,    // x
                0,                          // y
                FLAG_TEXTURE_WIDTH,
                FLAG_TEXTURE_HEIGHT
        );

        soundTextures = new TextureRegion[2];
        soundTextures[0] = new TextureRegion(
                graphicsTexture,
                0,                          // x
                ROCKET_TEXTURE_HEIGHT,      // y
                SOUND_TEXTURE_WIDTH,
                SOUND_TEXTURE_HEIGHT
        );
        soundTextures[1] = new TextureRegion(
                graphicsTexture,
                ROCKET_TEXTURE_WIDTH,       // x
                FIRE_TEXTURE_HEIGHT,        // y
                SOUND_TEXTURE_WIDTH,
                SOUND_TEXTURE_HEIGHT
        );
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        if (levelTexture != null) {
            levelTexture.getTexture().dispose();
        }

        this.level = level;

        levelTexture = LevelBuilder.buildLevelTexture(level);

        String backgroundImg = level.getBackgroundImage();
        if (backgroundImg != null) {
            backgroundTexture = ResourceLoader.loadLevelTexture(backgroundImg);
        }
    }

    public void reset() {
        if (level.getBackgroundColor() != null) {
            Graphics.clearColor(level.getBackgroundColor());
        }
    }

    public void render(boolean soundMuted, boolean paused, boolean collided,
                       boolean landed, boolean hasMoreLevels) {
        if (level != null) {
            Graphics.clear();
            Graphics.begin();

            drawLevel();
            drawRocket();
            drawHUD(soundMuted);
            drawPointer();

            drawNotifications(paused, collided, landed, hasMoreLevels);

            Graphics.end();
        }
    }

    private void drawNotifications(boolean paused, boolean collided,
                                   boolean landed, boolean hasMoreLevels) {
        // Draw centered text
        if (paused) {
            Graphics.drawCentered("Paused", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2,
                    Graphics.FontSize.BIG);
        } else if (collided) {
            rocket.moveUp(false);

            String text;

            if (landed) {
                if (hasMoreLevels) {
                    text = "Landed! =)";
                } else {
                    text = "You Won!";
                }
            } else {
                text = "Crashed! =(";
            }

            Graphics.drawCentered(text, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2,
                    Graphics.FontSize.BIG);
        }
    }

    private void drawHUD(boolean soundMuted) {
        // Draw sound ico
        int soundTextureIndex;
        if (soundMuted) {
            soundTextureIndex = 1;
        } else {
            soundTextureIndex = 0;
        }

        Graphics.draw(
                soundTextures[soundTextureIndex],
                SOUND_ICO_X,
                SOUND_ICO_Y,
                SOUND_ICO_WIDTH,
                SOUND_ICO_HEIGHT
        );

        // Draw text

        Graphics.draw(
                10, SCREEN_HEIGHT - 10, 20,
                String.format("X: %d", (int) rocket.getX()),
                String.format("Y: %d", (int) rocket.getY()),
                String.format("Vx: %.2f", rocket.getVx()),
                String.format("Vy: %.2f", rocket.getVy()),
                String.format("Angle: %.1f", rocket.getDirectionAngle())
        );
    }

    private void drawLevel() {
        if (backgroundTexture != null)
            Graphics.draw(backgroundTexture, 0, 0);
        Graphics.draw(levelTexture, 0, 0);
        Graphics.draw(
                flagTexture,
                level.getFlagX(),
                level.getFlagY(),
                FLAG_WIDTH,
                FLAG_HEIGHT
        );
    }


    private void drawRocket() {
        Graphics.draw(
                rocketTexture,
                rocket.getX(),
                rocket.getY(),
                ROCKET_WIDTH,
                ROCKET_HEIGHT,
                rocket.getDirectionAngle()
        );
        if (rocket.isMoveUp()) {
            Graphics.draw(
                    fireTexture,
                    rocket.getX() + FIRE_PADDING,
                    rocket.getY() - FIRE_HEIGHT,
                    FIRE_WIDTH / 2,
                    FIRE_HEIGHT + ROCKET_HEIGHT / 2,
                    FIRE_WIDTH,
                    FIRE_HEIGHT,
                    rocket.getDirectionAngle()
            );
        }
    }

    private void drawPointer() {
        if (rocket.getX() < 0
                || level.getWidth() < rocket.getX()
                || level.getHeight() < rocket.getY()) {
            int pointerX = 0;
            int pointerY = 0;
            float pointerAngle = 0;

            if (rocket.getX() < 0) {
                pointerX = 0;

                if (level.getHeight() < rocket.getY()) {
                    pointerY = SCREEN_HEIGHT - POINTER_HEIGHT;
                    pointerAngle = (float)Math.toDegrees(Math.atan(
                            (rocket.getY() - level.getHeight()) / rocket.getX()
                    )) - 180;
                } else {
                    pointerY = (int)rocket.getY();
                    pointerAngle = 180;
                }
            } else if (level.getWidth() < rocket.getX()) {
                pointerX = SCREEN_WIDTH - POINTER_WIDTH;

                if (level.getHeight() < rocket.getY()) {
                    pointerY = SCREEN_HEIGHT - POINTER_HEIGHT;
                    pointerAngle = (float)Math.toDegrees(Math.atan(
                            (rocket.getY() - level.getHeight()) / rocket.getX()
                    ));
                } else {
                    pointerY = (int)rocket.getY();
                    pointerAngle = 0;
                }
            } else if (level.getHeight() < rocket.getY()) {
                pointerX = (int)rocket.getX();
                pointerY = SCREEN_HEIGHT - POINTER_HEIGHT;
                pointerAngle = 90;
            }

            Graphics.draw(
                    pointerTexture,
                    pointerX,
                    pointerY,
                    POINTER_WIDTH,
                    POINTER_HEIGHT,
                    pointerAngle
            );
        }
    }
}
