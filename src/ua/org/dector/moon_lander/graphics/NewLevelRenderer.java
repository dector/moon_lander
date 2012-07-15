package ua.org.dector.moon_lander.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.gcore.common.Settings;
import ua.org.dector.gcore.utils.ResourceLoader;
import ua.org.dector.moon_lander.LanderGame;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.utils.LevelBuilder;
import ua.org.dector.moon_lander.utils.LevelLoader;

import static ua.org.dector.moon_lander.AppConfig.*;
import static ua.org.dector.moon_lander.AppConfig.FLAG_TEXTURE_HEIGHT;

/**
 * @author dector (dector9@gmail.com)
 */
public class NewLevelRenderer {
    private Level level;
    private Rocket rocket;

    private OrthographicCamera camera;

    private TextureRegion rocketTexture;
    private TextureRegion fireTexture;
    private TextureRegion pointerTexture;
    private TextureRegion flagTexture;
    private TextureRegion mapTexture;

    public NewLevelRenderer(LanderGame game) {
        setupCamera(game);
        loadTextures(game.getResourceLoader());
    }

    private void setupCamera(LanderGame game) {
        Settings gameSettings = game.getSettings();
        int screenWidth = gameSettings.getScreenWidth();
        int screenHeight = gameSettings.getScreenHeight();

        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();

        game.getGraphics().setProjectionMatrix(camera.combined);
    }

    private void loadTextures(ResourceLoader loader) {
        Texture graphicsTexture = loader.loadTexture(GRAPHICS_FILE);
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
    }


    public void render(Graphics g) {
        drawLevel(g, getLevel());
        drawRocket(g, getRocket());
    }

    private void drawRocket(Graphics g, Rocket rocket) {
        if (rocket == null) return;

        float x = rocket.getX();
        float y = rocket.getY();
        float directionAngle = rocket.getDirectionAngle();

        g.draw(
                rocketTexture,
                x,
                y,
                ROCKET_WIDTH,
                ROCKET_HEIGHT,
                directionAngle
        );

        if (rocket.isEngineOn()) {
            g.draw(
                    fireTexture,
                    x + FIRE_PADDING,
                    y - FIRE_HEIGHT,
                    FIRE_WIDTH / 2,
                    FIRE_HEIGHT + ROCKET_HEIGHT / 2,
                    FIRE_WIDTH,
                    FIRE_HEIGHT,
                    directionAngle
            );
        }
    }

    private void drawLevel(Graphics g, Level level) {
        if (getLevel() == null) return;

        drawBackground(g);
        drawFlag(g);
        drawMap(g);
    }

    private void drawMap(Graphics g) {
        if (mapTexture == null) return;

        g.draw(mapTexture, 0, 0);
    }

    private void drawFlag(Graphics g) {
        if (! level.hasFlag()) return;

        g.draw(
                flagTexture,
                level.getFlagX(),
                level.getFlagY(),
                FLAG_WIDTH,
                FLAG_HEIGHT
        );
    }

    private void drawBackground(Graphics g) {
        if (! level.hasBackground()) return;

        // TODO Implement background draw
    }

    private void rebuildMapTexture() {
        if (mapTexture != null) {
            mapTexture.getTexture().dispose();
        }

        mapTexture = LevelBuilder.buildLevelTexture(level);

        // TODO Implement background image building
//        String backgroundImg = level.getBackgroundImage();
//        if (backgroundImg != null) {
//            backgroundTexture = LevelLoader.loadLevelTexture(backgroundImg);
//        } else {
//            backgroundTexture = null;
//        }
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setLevel(Level level) {
        Level oldLevel = getLevel();

        this.level = level;

        if (level != null && oldLevel != level) {
            rebuildMapTexture();
        }
    }

    public Level getLevel() {
        return level;
    }
}
