package ua.org.dector.moon_lander.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.org.dector.gcore.common.Settings;
import ua.org.dector.gcore.game.AbstractScreen;
import ua.org.dector.moon_lander.AppConfig;
import ua.org.dector.moon_lander.graphics.EditorLevelRenderer;
import ua.org.dector.moon_lander.graphics.Graphics;
import ua.org.dector.moon_lander.LanderGame;
import ua.org.dector.moon_lander.models.Level;
import ua.org.dector.moon_lander.models.Rocket;
import ua.org.dector.moon_lander.utils.Utils;

import static com.badlogic.gdx.Input.Keys;
import static ua.org.dector.moon_lander.AppConfig.*;
import static ua.org.dector.moon_lander.graphics.EditorLevelRenderer.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class EditorScreen extends AbstractScreen<LanderGame> {
    private EditorLevelRenderer levelRenderer;

    private String levelName;

    public Level getLevel() {
        return levelRenderer.getLevel();
    }

    public EditorScreen(Level level, LanderGame game) {
        super(game);

        levelRenderer = new EditorLevelRenderer(game);
        levelRenderer.setLevel(level);
        levelRenderer.setRocket(
                new Rocket(level.getRocketX(), level.getRocketY(), level.getRocketAngle())
        );

        editLevel(level, null);

        levelRenderer.setSelectedTool(Tool.POINTER);

        if (level.getMapLength() > 0) {
            if (level.get(level.getMapLength() - 2) == level.getWidth()) {
                levelRenderer.setDrawingState(DrawingState.FINISHED);
            } else {
                levelRenderer.setDrawingState(DrawingState.DRAWING);
            }
        } else {
            levelRenderer.setDrawingState(DrawingState.NOT_STARTED);
        }

        levelRenderer.rebuildLandTexture();
    }

    public void editLevel(Level level, String levelName) {
        levelRenderer.setLevel(level);
        levelRenderer.getRocket().setPosition(level.getRocketX(), level.getRocketY());
        levelRenderer.getRocket().setDirectionAngle(level.getRocketAngle());

        if (level.getMapLength() == 0) {
            levelRenderer.setDrawingState(DrawingState.NOT_STARTED);
        }

        if (levelName != null)
            this.levelName = levelName;
    }

    public void render(float delta) {
        Settings gameSettings = game.getSettings();
        int screenHeight = gameSettings.getScreenHeight();

        int x = Gdx.input.getX();
        int y = screenHeight - Gdx.input.getY() - 1;

        levelRenderer.setX(x);
        levelRenderer.setY(y);

        Graphics g = game.getGraphics();
        g.clear();
        g.begin();
            levelRenderer.render(g);
        g.end();
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        Settings gameSettings = game.getSettings();
        int screenHeight = gameSettings.getScreenHeight();

        y = screenHeight - y - 1;

        // TODO It must be some controller, not renderer
        switch (levelRenderer.getSelectedTool()) {
            case DRAWER: {
                if (levelRenderer.getDrawingState() == DrawingState.NOT_STARTED) {
                    if (x == 0) {
                        setPoint(x, y);
                        levelRenderer.rebuild();

                        levelRenderer.setDrawingState(DrawingState.DRAWING);
                    }
                } else if (levelRenderer.getDrawingState() == DrawingState.DRAWING) {
                    setPoint(x, y);
                    levelRenderer.rebuild();

                    if (x == getLevel().getWidth() - 1) {
                        levelRenderer.setDrawingState(DrawingState.FINISHED);
                    }
                }
            } break;
            case FLAG: {
                getLevel().setFlagPosition(x, y);
                levelRenderer.rebuild();
            } break;
            case ROCKET: {
                getLevel().setRocketParams(x, y, levelRenderer.getRocketAngle());
                levelRenderer.getRocket().setPosition(x, y);
                levelRenderer.getRocket().setDirectionAngle(levelRenderer.getRocketAngle());
                levelRenderer.rebuild();
            } break;
            case LAND: {
                getLevel().setLand(x, y + LANDING_PLATFORM_HEIGHT / 2,
                        levelRenderer.getLandWidth());
                levelRenderer.rebuild();
            } break;
        }


        return true;
    }

    private void setPoint(int x, int y) {
        if (x >= levelRenderer.getLastPoint(0)) {
            getLevel().addPoint(x, y);
            levelRenderer.setLastPoint(0, x);
            levelRenderer.setLastPoint(1, y);
            levelRenderer.rebuild();
        }
    }

    public boolean keyDown(int keycode) {
        final Level level = getLevel();

        switch (keycode) {
            case Keys.ESCAPE: Gdx.app.exit();                               break;
            case Keys.M: {
                game.getSoundManager().toggleMuted();
                game.getMusicManager().toggleMuted();
            } break;
            case Keys.NUM_1: levelRenderer.setSelectedTool(Tool.POINTER); break;
            case Keys.NUM_2: levelRenderer.setSelectedTool(Tool.DRAWER); break;
            case Keys.NUM_3: levelRenderer.setSelectedTool(Tool.FLAG); break;
            case Keys.NUM_4: levelRenderer.setSelectedTool(Tool.LAND); break;
            case Keys.NUM_5: levelRenderer.setSelectedTool(Tool.ROCKET); break;
            case Keys.UP:
                if (levelRenderer.getSelectedTool() == Tool.ROCKET) {
                    levelRenderer.setRocketAngle(90);
                } break;
            case Keys.RIGHT:
                if (levelRenderer.getSelectedTool() == Tool.ROCKET) {
                    levelRenderer.setRocketAngle(0);
                } break;
            case Keys.DOWN:
                if (levelRenderer.getSelectedTool() == Tool.ROCKET) {
                    levelRenderer.setRocketAngle(270);
                } break;
            case Keys.LEFT:
                if (levelRenderer.getSelectedTool() == Tool.ROCKET) {
                    levelRenderer.setRocketAngle(180);
                } break;
            case Keys.BACKSPACE:
                if (levelRenderer.getSelectedTool() == Tool.DRAWER) {
                    if (levelRenderer.getDrawingState() != DrawingState.NOT_STARTED) {
                        level.removeLastPoint();
                        levelRenderer.rebuild();

                        int mapLength = level.getMapLength();
                        if (mapLength != 0) {
                            levelRenderer.setLastPoint(0, level.get(mapLength - 2));
                            levelRenderer.setLastPoint(1, level.get(mapLength - 1));
                        } else {
                            levelRenderer.setLastPoint(0, 0);
                            levelRenderer.setLastPoint(1, 0);
                        }
                        
                        if (mapLength == 0) {
                            levelRenderer.setDrawingState(DrawingState.NOT_STARTED);
                        } else if (levelRenderer.getDrawingState() == DrawingState.FINISHED) {
                            levelRenderer.setDrawingState(DrawingState.DRAWING);
                        }
                    }
                } break;
            case Keys.R: {
                game.play(level);
            } break;
            case Keys.S: {
                if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && levelName != null) {
                    level.toFile(levelName, true);
                } else {
                    Input.TextInputListener inputer = new Input.TextInputListener() {
                        public void input(String text) {
                            level.toFile(text, false);
                            levelName = text;
                        }

                        public void canceled() {}
                    };
                    Gdx.input.getTextInput(inputer, "Input file name", "level");
                }

            } break;
            case Keys.L: {
                Input.TextInputListener inputer = new Input.TextInputListener() {
                    public void input(String text) {
                        setLevel(Level.fromFile(text));

                        if (text.startsWith(SAVED_LEVELS_DIR))
                            text = text.substring(SAVED_LEVELS_DIR.length(), text.length());
                        editLevel(getLevel(), text);
                    }

                    public void canceled() {}
                };
                Gdx.input.getTextInput(inputer, "Input file name",
                        AppConfig.SAVED_LEVELS_DIR + (
                                (levelName != null) ? levelName : "level"));
            } break;
        }

        return true;
    }

    private void setLevel(Level level) {
        levelRenderer.setLevel(level);
    }

    public boolean scrolled(int amount) {
        switch (levelRenderer.getSelectedTool()) {
            case ROCKET: {
                float diff = -amount;

                if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                    diff *= 10;
                } else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
                    diff *= .1f;
                }

                levelRenderer.setRocketAngle(levelRenderer.getRocketAngle() + diff);
                if (levelRenderer.getRocketAngle() > 360) {
                    levelRenderer.setRocketAngle(levelRenderer.getRocketAngle() - 360);
                } else if (levelRenderer.getRocketAngle() < 0) {
                    levelRenderer.setRocketAngle(levelRenderer.getRocketAngle() + 360);
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

                levelRenderer.setLandWidth(levelRenderer.getLandWidth() + diff);
                if (levelRenderer.getLandWidth() > getLevel().getWidth()) {
                    levelRenderer.setLandWidth(getLevel().getWidth());
                } else if (levelRenderer.getLandWidth() < 5) {
                    levelRenderer.setLandWidth(5);
                }

                levelRenderer.rebuildLandTexture();
            }
        }

        return true;
    }
}
