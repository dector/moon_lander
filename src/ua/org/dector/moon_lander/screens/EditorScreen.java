package ua.org.dector.moon_lander.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.moon_lander.Graphics;
import ua.org.dector.moon_lander.LanderGame;
import ua.org.dector.moon_lander.managers.GameManagers;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.utils.LevelRenderer;

import static com.badlogic.gdx.Input.Keys;
import static ua.org.dector.moon_lander.AppConfig.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class EditorScreen extends AbstractScreen {
    private Level level;
    private LanderGame landerGame;

    private Tool selectedTool;
    private DrawingState drawingState;

    private LevelRenderer levelRenderer;

    private TextureRegion pointTexture;

    private enum Tool {
        POINTER, DRAWER, ROCKET, FLAG, LAND
    }

    private enum DrawingState {
        FIRST_POINT, DRAWING, LAST_POINT
    }

    public EditorScreen(GameManagers gameManagers,
                        Level level,
                        Rocket rocket,
                        LanderGame landerGame) {
        super(gameManagers);

        this.landerGame = landerGame;
        this.level = level;

        levelRenderer = new LevelRenderer(rocket);
        levelRenderer.setLevel(level);

        selectedTool = Tool.POINTER;
        drawingState = DrawingState.FIRST_POINT;

        Pixmap p = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        p.setColor(Color.WHITE);
        p.fillCircle(8, 8, 8);
        p.setColor(0, 0, 0, 1);
        p.fillCircle(8, 8, 5);
        Texture pointTex = new Texture(p);
        p.dispose();
        pointTexture = new TextureRegion(pointTex);
    }

    public void render(float delta) {
        int x = Gdx.input.getX();
        int y = SCREEN_HEIGHT - Gdx.input.getY() - 1;

        levelRenderer.render(gameManagers.getSoundManager().isMuted(),
                false, false, false, false);

        Graphics.begin();
        switch (selectedTool) {
            case DRAWER: {
                if (drawingState == DrawingState.FIRST_POINT
                        && x == 0) {
                    Graphics.draw(pointTexture, x + 8, y + 8);
                } else if (drawingState == DrawingState.DRAWING
                        && x == level.getWidth() - 1) {
                    Graphics.draw(pointTexture, x - 16, y + 8);
                }
            } break;
            case FLAG: {
                if (! level.hasFlag()) {
                    levelRenderer.drawFlag(x, y);
                }
            } break;
        }

        Graphics.draw(String.format("Mouse at %d:%d", x, y), 10, 10);
        Graphics.end();
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        y = SCREEN_HEIGHT - y - 1;

        switch (selectedTool) {
            case DRAWER: {
                if (drawingState == DrawingState.FIRST_POINT) {
                    if (x == 0) {
                        level.addPoint(x, y);
                        levelRenderer.rebuild();

                        drawingState = DrawingState.DRAWING;
                    }
                } else if (drawingState == DrawingState.DRAWING) {
                    level.addPoint(x, y);
                    levelRenderer.rebuild();

                    if (x == level.getWidth() - 1) {
                        drawingState = DrawingState.LAST_POINT;
                    }
                }
            } break;
            case FLAG: {
                level.setFlagPosition(x, y);
            } break;
        }

        return true;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE: Gdx.app.exit();                               break;
            case Keys.M:    gameManagers.getSoundManager().toggleMuted();   break;
            case Keys.NUM_1: selectedTool = Tool.POINTER; break;
            case Keys.NUM_2: selectedTool = Tool.DRAWER; break;
            case Keys.NUM_3: selectedTool = Tool.FLAG; break;
            case Keys.NUM_4: selectedTool = Tool.LAND; break;
            case Keys.NUM_5: selectedTool = Tool.ROCKET; break;
        }

        return true;
    }
}
