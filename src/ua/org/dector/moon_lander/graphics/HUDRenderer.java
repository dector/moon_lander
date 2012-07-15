package ua.org.dector.moon_lander.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.LanderGame;
import ua.org.dector.moon_lander.models.Rocket;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class HUDRenderer {
    private LanderGame game;
    private Rocket rocket;

    private TextureRegion[] soundTextures;

    private boolean drawHUD;
    private boolean drawSoundInfo;
    private boolean drawRocketInfo;

    public HUDRenderer(LanderGame game) {
        this.game = game;

        setDrawHUD(true);
        setDrawRocketInfo(true);
        setDrawSoundInfo(true);

        loadTextures();
    }

    private void loadTextures() {
        Texture graphicsTexture = game.getResourceLoader().loadTexture(GRAPHICS_FILE);

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

    public void render(Graphics g) {
        if (! isDrawHUD()) return;

        drawSoundInfo(g);
        drawRocketInfo(g);
    }

    private void drawSoundInfo(Graphics g) {
        if (! isDrawSoundInfo()) return;

        int soundIcoIndex;

        if (game.getMusicManager().isEnabled()) {
            soundIcoIndex = 0;
        } else {
            soundIcoIndex = 1;
        }

        g.draw(
                soundTextures[soundIcoIndex],
                SOUND_ICO_X,
                SOUND_ICO_Y,
                SOUND_ICO_WIDTH,
                SOUND_ICO_HEIGHT
        );
    }

    private void drawRocketInfo(Graphics g) {
        if (! isDrawRocketInfo()) return;

        if (rocket == null) return;

        g.draw(
                10, game.getSettings().getScreenHeight() - 10, 20,
                String.format("X: %d", (int) rocket.getX()),
                String.format("Y: %d", (int) rocket.getY()),
                String.format("Vx: %.2f", rocket.getVx()),
                String.format("Vy: %.2f", rocket.getVy()),
                String.format("Angle: %.1f", rocket.getDirectionAngle())
        );
    }

    public boolean isDrawHUD() {
        return drawHUD;
    }

    public void setDrawHUD(boolean drawHUD) {
        this.drawHUD = drawHUD;
    }

    public void switchDrawHUD() {
        setDrawHUD(! isDrawHUD());
    }

    public boolean isDrawSoundInfo() {
        return drawSoundInfo;
    }

    public void setDrawSoundInfo(boolean drawSoundInfo) {
        this.drawSoundInfo = drawSoundInfo;
    }

    public void switchDrawSoundInfo() {
        setDrawSoundInfo(! isDrawSoundInfo());
    }

    public boolean isDrawRocketInfo() {
        return drawRocketInfo;
    }

    public void setDrawRocketInfo(boolean drawRocketInfo) {
        this.drawRocketInfo = drawRocketInfo;
    }

    public void switchDrawRocketInfo() {
        setDrawRocketInfo(! isDrawRocketInfo());
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }
}
