package ua.org.dector.moon_lander.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.LanderGame;

import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class HUDRenderer {
    private LanderGame game;

    private TextureRegion[] soundTextures;

    public HUDRenderer(LanderGame game) {
        this.game = game;

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
}
