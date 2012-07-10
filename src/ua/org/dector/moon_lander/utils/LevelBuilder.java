package ua.org.dector.moon_lander.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.models.Level;

import static ua.org.dector.moon_lander.AppConfig.LANDING_PLATFORM_BORDER;
import static ua.org.dector.moon_lander.AppConfig.LANDING_PLATFORM_HEIGHT;

/**
 * @author dector (dector9@gmail.com)
 */
public class LevelBuilder {
    public static TextureRegion buildLevelTexture(Level level) {
        int levelWidth = level.getWidth();
        int levelHeight = level.getHeight();

        Pixmap pixmap = new Pixmap(
                levelWidth,
                levelHeight,
                Pixmap.Format.RGBA8888
        );

        pixmap.setColor(Color.WHITE);

        int i = 0;
        int[] prevPoint = new int[2];
        int[] currPoint = new int[2];
        int mapLength = level.getMapLength();

        if (mapLength > 0) {
            prevPoint[0] = level.get(i++);
            prevPoint[1] = level.get(i++);

            while (i < mapLength) {
                currPoint[0] = level.get(i++);
                currPoint[1] = level.get(i++);

                pixmap.drawLine(
                        prevPoint[0],
                        levelHeight - prevPoint[1],
                        currPoint[0],
                        levelHeight - currPoint[1]
                );

                prevPoint[0] = currPoint[0];
                prevPoint[1] = currPoint[1];
            }
        }

        // Draw landing platform
        if (level.hasLanding()) {
            pixmap.fillRectangle(
                    level.getLandingLeftX() + LANDING_PLATFORM_BORDER,
                    levelHeight -
                            (level.getLandingBottomY() + LANDING_PLATFORM_HEIGHT / 2),
                    level.getLandingRightX() - level.getLandingLeftX()
                            - 2 * LANDING_PLATFORM_BORDER,
                    LANDING_PLATFORM_HEIGHT
            );
        }

        // Prepare texture

        int textureWidth = Utils.toPowerOfTwo(pixmap.getWidth());
        int textureHeight = Utils.toPowerOfTwo(pixmap.getHeight());

        Pixmap texturePixmap = new Pixmap(
                (textureWidth > 0) ? textureWidth : 1,
                (textureHeight > 0) ? textureHeight : 1,
                Pixmap.Format.RGBA8888
        );

        texturePixmap.drawPixmap(pixmap, 0, 0);
        
        TextureRegion texture = new TextureRegion(
                new Texture(texturePixmap),
                pixmap.getWidth(),
                pixmap.getHeight()
        );

        pixmap.dispose();
        texturePixmap.dispose();

        return texture;
    }
}
