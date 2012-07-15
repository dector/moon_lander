package ua.org.dector.moon_lander.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.LanderGame;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.utils.Utils;

import static ua.org.dector.moon_lander.AppConfig.LANDING_PLATFORM_BORDER;
import static ua.org.dector.moon_lander.AppConfig.LANDING_PLATFORM_HEIGHT;

/**
 * @author dector (dector9@gmail.com)
 */
public class EditorLevelRenderer extends LevelRenderer {
    public enum Tool {
        POINTER, DRAWER, ROCKET, FLAG, LAND
    }

    public enum DrawingState {
        NOT_STARTED, DRAWING, FINISHED
    }

    private TextureRegion lineTexture;
    private TextureRegion landTexture;
    private TextureRegion pointTexture;

    private Tool selectedTool;
    private DrawingState drawingState;

    private int x;
    private int y;
    private int landWidth;
    private float rocketAngle;

    private int[] lastPoint;

    public EditorLevelRenderer(LanderGame game) {
        super(game);

        Pixmap p = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        p.setColor(Color.WHITE);
        p.fillCircle(8, 8, 8);
        p.setColor(0, 0, 0, 1);
        p.fillCircle(8, 8, 5);
        Texture pointTex = new Texture(p);
        p.dispose();
        pointTexture = new TextureRegion(pointTex);

        reset();
    }

    public void setLevel(Level level) {
        super.setLevel(level);
        reset();
    }

    private void reset() {
        selectedTool = Tool.POINTER;

        lastPoint = new int[2];

        x = 0;
        y = 0;
        landWidth = 100;
        rocketAngle = 90;
    }

    public void render(Graphics g) {
        super.render(g);

        switch (selectedTool) {
            case DRAWER: {
                if (drawingState == DrawingState.NOT_STARTED && x == 0) {
                    g.draw(pointTexture, x + 8, y + 8);
                } else if (drawingState == DrawingState.DRAWING) {
                    rebuildLineTexture(x, y);

                    g.draw(lineTexture, 0, 0);

                    if (x == getLevel().getWidth() - 1) {
                        g.draw(pointTexture, x - 16, y + 8);
                    }
                }
            } break;
            case FLAG: {
                drawFlag(g, x, y);
            } break;
            case ROCKET: {
                drawRocket(g, x, y, rocketAngle);
            } break;
            case LAND: {
                g.draw(landTexture, x, y);
            } break;
        }

        g.draw(10, 300, 20,
                String.format("Tool %s", selectedTool),
                String.format("Mouse at %d:%d", x, y));
        if (selectedTool == Tool.DRAWER) {
            String text;

            if (drawingState == DrawingState.NOT_STARTED) {
                text = "Click on left border to start drawing";
            } else if (drawingState == DrawingState.DRAWING) {
                text = "Click on right border to finish drawing";
            } else {
                text = "Drawing finished";
            }

            g.draw(text, 10, 260, Graphics.FontSize.SMALL);
        } else if (selectedTool == Tool.ROCKET) {
            g.draw(String.format("Rocket angle: %.1f", rocketAngle), 10, 260,
                    Graphics.FontSize.SMALL);
        } else if (selectedTool == Tool.LAND) {
            g.draw(String.format("Land width: %d", landWidth), 10, 260,
                    Graphics.FontSize.SMALL);
        }
    }

    public void rebuildLineTexture(int x, int y) {
        Level level = getLevel();

        if (lineTexture != null)
            lineTexture.getTexture().dispose();

        Pixmap p = new Pixmap(Utils.toPowerOfTwo(level.getWidth()),
                Utils.toPowerOfTwo(level.getHeight()),
                Pixmap.Format.RGBA8888);

        p.setColor(Color.WHITE);
        p.drawLine(
                x,
                level.getHeight() - y - 1,
                lastPoint[0],
                level.getHeight() - lastPoint[1] - 1
        );

        Texture lineTex = new Texture(p);
        p.dispose();
        lineTexture = new TextureRegion(lineTex, level.getWidth(), level.getHeight());
    }

    public void rebuildLandTexture() {
        if (landTexture != null)
            landTexture.getTexture().dispose();

        Pixmap p = new Pixmap(Utils.toPowerOfTwo(landWidth),
                Utils.toPowerOfTwo(LANDING_PLATFORM_HEIGHT),
                Pixmap.Format.RGBA8888);

        p.setColor(Color.WHITE);
        p.fillRectangle(
                0 + LANDING_PLATFORM_BORDER,
                0,
                landWidth - 2 * LANDING_PLATFORM_BORDER,
                LANDING_PLATFORM_HEIGHT
        );

        Texture landTex = new Texture(p);
        p.dispose();
        landTexture = new TextureRegion(landTex, landWidth, LANDING_PLATFORM_HEIGHT);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLandWidth() {
        return landWidth;
    }

    public void setLandWidth(int landWidth) {
        this.landWidth = landWidth;
    }

    public float getRocketAngle() {
        return rocketAngle;
    }

    public void setRocketAngle(float rocketAngle) {
        this.rocketAngle = rocketAngle;
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }

    public DrawingState getDrawingState() {
        return drawingState;
    }

    public void setDrawingState(DrawingState drawingState) {
        this.drawingState = drawingState;
    }

    public int getLastPoint(int i) {
        return lastPoint[i];
    }

    public void setLastPoint(int i, int j) {
        lastPoint[i] = j;
    }

    public void rebuild() {
        super.rebuildMapTexture();
    }
}
