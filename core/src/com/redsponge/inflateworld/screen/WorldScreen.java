package com.redsponge.inflateworld.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.entity.Pump;
import com.redsponge.inflateworld.entity.World;
import com.redsponge.inflateworld.shop.MoneyManager;
import com.redsponge.inflateworld.ui.Button;
import com.redsponge.inflateworld.ui.ButtonBase;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;
import com.redsponge.inflateworld.util.Utils;

public class WorldScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private FitViewport scoreViewport;
    private OrthographicCamera camera;

    private static boolean pressedEsc = false;
    public static boolean tutorial = true;
    public MoneyManager moneyManager;

    private boolean pauseScreen;

    private World world;
    private Pump pump;
    private float desiredZoom;
    private boolean running;
    private long timeSinceResize;
    private boolean zooming;

    private Array<Button> buttons;
    private Array<Button> gameOverButtons;
    private int stage;
    private Color sky;
    private Color desiredSkyColor;
    private boolean goneShopping;

    public WorldScreen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        camera = new OrthographicCamera(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        camera.setToOrtho(false);
        viewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y, camera);

        scoreViewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        world = new World(viewport);
        pump = new Pump(world, viewport);

        buttons = new Array<Button>();
        gameOverButtons = new Array<Button>();
        moneyManager = new MoneyManager();
        stage = 1;
        goneShopping = false;
    }

    @Override
    public void show() {
        if(!goneShopping)
            init();
        else goneShopping = false;
    }

    public void init() {
        world.init();

        moneyManager.setMoney(0);

        buttons.clear();
        buttons.add(new ButtonBase(viewport.getWorldWidth() / 2 - 25, viewport.getWorldHeight() / 4 * 2, 50, 20, "Shop", scoreViewport, () -> {InflateTheWorld.instance.setScreen(InflateTheWorld.instance.shopScreen); goneShopping = true;}));


        buttons.add(new ButtonBase(viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 8 * 3, 100, 20, "Back to game", scoreViewport, () -> pauseScreen = false));

        buttons.add(new ButtonBase(viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 8, 100, 20, "Back to menu", scoreViewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.menuScreen)));


        gameOverButtons.clear();
        gameOverButtons.add(Button.centeredAroundX(viewport.getWorldHeight()/4, 100, 25, "Retry", scoreViewport, this::init));

        gameOverButtons.add(Button.centeredAroundX(viewport.getWorldHeight() / 8, 100, 25, "Back to menu", scoreViewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.menuScreen)));
        sky = new Color(Reference.SKY_COLOR);
        desiredSkyColor = new Color(sky);
        pauseScreen = false;
        running = true;
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            pauseScreen = !pauseScreen;
            pressedEsc = true;
        }
    }

    public void update(float delta) {
        if(!running) return;

        handleInput();

        if(!pauseScreen) {
            world.update(delta);
            pump.update(delta);
            updateZoom(delta);
        } else {
            buttons.forEach(b -> b.update(delta));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(sky.r, sky.g, sky.b, sky.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            update(delta);

            viewport.apply();
            batch.setProjectionMatrix(viewport.getCamera().combined);
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        if(running) {
            world.render(batch, shapeRenderer);
            pump.render(batch, shapeRenderer);

            scoreViewport.apply();
            batch.setProjectionMatrix(scoreViewport.getCamera().combined);
            shapeRenderer.setProjectionMatrix(scoreViewport.getCamera().combined);
            batch.begin();
            batch.setColor(Color.WHITE);

            Assets.instance.nonTextures.font.getData().setScale(0.3f);
            Assets.instance.nonTextures.font.draw(batch, "Score: " + (int) (world.getRadius()), 10, scoreViewport.getWorldHeight() - 10);
            Assets.instance.nonTextures.font.draw(batch, "Difficulty: " + world.getDifficulty(), 10, scoreViewport.getWorldHeight() - 30);
            Assets.instance.nonTextures.font.draw(batch, "Money: " + moneyManager.getMoney(), 10, scoreViewport.getWorldHeight() - 50);

            if(tutorial) {
                Assets.instance.nonTextures.fontSmall.draw(batch, "<- Click the pump to inflate the world!", viewport.getWorldWidth() / 2 + 10, 190);
            }
            if (!pressedEsc) {
                Assets.instance.nonTextures.fontSmall.draw(batch, "Psst.. press ESC for pause menu and shop", scoreViewport.getWorldWidth() - 200, 10);
            }
            batch.end();

            if (pauseScreen) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

                shapeRenderer.begin(ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.5f);
                shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
                shapeRenderer.end();

                Gdx.gl.glDisable(GL20.GL_BLEND);

                buttons.forEach(b -> b.render(shapeRenderer, batch));
            }
        } else {
            scoreViewport.apply();
            batch.setProjectionMatrix(scoreViewport.getCamera().combined);
            shapeRenderer.setProjectionMatrix(scoreViewport.getCamera().combined);
            world.update(delta);
            world.render(batch, shapeRenderer);

            batch.begin();
            Assets.instance.nonTextures.font.getData().setScale(0.5f);
            Utils.drawTextCentered(batch, "Game Over!", Assets.instance.nonTextures.font, (int) scoreViewport.getWorldHeight()/2, scoreViewport.getWorldWidth());
            batch.end();

            gameOverButtons.forEach(b -> b.update(delta));
            gameOverButtons.forEach(b -> b.render(shapeRenderer, batch));
        }
    }

    private void renderSky() {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0.3f, 0.5f, 1f, 1f);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        scoreViewport.update(width, height, true);
    }

    public void changeVPSizeBy(int width, int height) {
        viewport.setWorldWidth(viewport.getWorldWidth() + width);
        viewport.setWorldHeight(viewport.getWorldWidth() + height);
    }

    public void updateZoom(float delta) {
        calculateDesiredZoom();
        if(camera.zoom != desiredZoom) {
            float dist = desiredZoom - camera.zoom;
            camera.zoom += dist * delta * 3;
            updateSkyColor(delta);
            if (Math.abs(dist) < 0.01f) {
                camera.zoom = desiredZoom;
                zooming = false;
            } else {
                zooming = true;
            }
        }
    }

    private void updateSkyColor(float delta) {
        float rd = desiredSkyColor.r - sky.r;
        float gd = desiredSkyColor.g - sky.g;
        float bd = desiredSkyColor.b - sky.b;

        sky.r += rd * delta * 3;
        sky.g += gd * delta * 3;
        sky.b += bd * delta * 3;
    }

    private void calculateDesiredZoom() {
        //if(zooming) return;
        if(Utils.secondsSince(timeSinceResize) < 3) return;
        boolean shouldChangeZoom = false;
        if((int) (world.getRadius() / 100) + 1 != camera.zoom) shouldChangeZoom = true;
        if(shouldChangeZoom) {
            desiredZoom = (int) (world.getRadius() / 100) + 1f;
            stage = (int) desiredZoom;
            desiredSkyColor = new Color(Reference.SKY_COLOR);
            desiredSkyColor.b -= (stage-1) * 0.1;
            desiredSkyColor.r -= (stage-1) * 0.1;
            desiredSkyColor.g -= (stage-1) * 0.1;
            timeSinceResize = TimeUtils.nanoTime();
        }
    }

    public void endGame() {
        world.setRadius(0);
        running = false;
    }

    public Pump getPump() {
        return pump;
    }

    public boolean isZooming() {
        return zooming;
    }
}
