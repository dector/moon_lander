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
import ua.org.dector.moon_lander.utils.Utils;

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

    // Editor opts
    private TextureRegion pointTexture;
    private TextureRegion landTexture;
    private float rocketAngle;
    private int landWidth;
    private int[] lastPoint;

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

        rocketAngle = 90;

        landWidth = 100;
        rebuildLandTexture();

        lastPoint = new int[2];

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
                levelRenderer.drawFlag(x, y);
            } break;
            case ROCKET: {
                levelRenderer.drawRocket(x, y, rocketAngle);
            } break;
            case LAND: {
                Graphics.draw(landTexture, x, y);
            } break;
        }

        Graphics.draw(10, 300, 20,
                String.format("Tool %s", selectedTool),
                String.format("Mouse at %d:%d", x, y));
        if (selectedTool == Tool.DRAWER) {
            String text;

            if (drawingState == DrawingState.FIRST_POINT) {
                text = "Click on left border to start drawing";
            } else if (drawingState == DrawingState.DRAWING) {
                text = "Click on right border to finish drawing";
            } else {
                text = "Drawing finished";
            }

            Graphics.draw(text, 10, 260, Graphics.FontSize.SMALL);
        } else if (selectedTool == Tool.ROCKET) {
            Graphics.draw(String.format("Rocket angle: %.1f", rocketAngle), 10, 260,
                    Graphics.FontSize.SMALL);
        } else if (selectedTool == Tool.LAND) {
            Graphics.draw(String.format("Land width: %d", landWidth), 10, 260,
                    Graphics.FontSize.SMALL);
        }
        Graphics.end();
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        y = SCREEN_HEIGHT - y - 1;

        switch (selectedTool) {
            case DRAWER: {
                if (drawingState == DrawingState.FIRST_POINT) {
                    if (x == 0) {
                        setPoint(x, y);

                        drawingState = DrawingState.DRAWING;
                    }
                } else if (drawingState == DrawingState.DRAWING) {
                    setPoint(x, y);

                    if (x == level.getWidth() - 1) {
                        drawingState = DrawingState.LAST_POINT;
                    }
                }
            } break;
            case FLAG: {
                level.setFlagPosition(x, y);
            } break;
            case ROCKET: {
                level.setRocketParams(x, y, rocketAngle);
                levelRenderer.getRocket().setPosition(x, y);
                levelRenderer.getRocket().setDirectionAngle(rocketAngle);
            } break;
            case LAND: {
                level.setLand(x, y, landWidth);
                levelRenderer.rebuild();
            } break;
        }

        return true;
    }

    private void setPoint(int x, int y) {
        if (x >= lastPoint[0]) {
            level.addPoint(x, y);
            lastPoint[0] = x;
            lastPoint[1] = y;
            levelRenderer.rebuild();
        }
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
            case Keys.UP:
                if (selectedTool == Tool.ROCKET) {
                    rocketAngle = 90;
                } break;
            case Keys.RIGHT:
                if (selectedTool == Tool.ROCKET) {
                    rocketAngle = 0;
                } break;
            case Keys.DOWN:
                if (selectedTool == Tool.ROCKET) {
                    rocketAngle = 270;
                } break;
            case Keys.LEFT:
                if (selectedTool == Tool.ROCKET) {
                    rocketAngle = 180;
                } break;
            case Keys.BACKSPACE:
                if (selectedTool == Tool.DRAWER) {
                    if (drawingState != DrawingState.FIRST_POINT) {
                        level.removeLastPoint();
                        levelRenderer.rebuild();

                        int mapLength = level.getMapLength();
                        if (mapLength != 0) {
                            lastPoint[0] = level.get(mapLength - 2);
                            lastPoint[1] = level.get(mapLength - 1);
                        } else {
                            lastPoint[0] = 0;
                            lastPoint[1] = 0;
                        }
                        
                        if (mapLength == 0) {
                            drawingState = DrawingState.FIRST_POINT;
                        } else if (drawingState == DrawingState.LAST_POINT) {
                            drawingState = DrawingState.DRAWING;
                        }
                    }
                } break;
            case Keys.R: {
                landerGame.play(level);
            } break;
        }

        return true;
    }

    public boolean scrolled(int amount) {
        switch (selectedTool) {
            case ROCKET: {
                float diff = -amount;

                if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                    diff *= 10;
                } else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                    diff *= .1f;
                }

                rocketAngle += diff;
                if (rocketAngle > 360) {
                    rocketAngle -= 360;
                } else if (rocketAngle < 0) {
                    rocketAngle += 360;
                }
            } break;
            case LAND: {
                int diff = -amount;

                if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                    diff *= 50;
                } else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                    diff *= 1;
                } else {
                    diff *= 10;
                }

                landWidth += diff;
                if (landWidth > level.getWidth()) {
                    landWidth = level.getWidth();
                } else if (landWidth < 5) {
                    landWidth = 5;
                }

                rebuildLandTexture();
            }
        }

        return true;
    }

    private void rebuildLandTexture() {
        // Rebuild land texture
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
}
