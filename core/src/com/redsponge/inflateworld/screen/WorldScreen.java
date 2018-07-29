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
    public MoneyManager moneyManager;

    private boolean pauseScreen;

    private World world;
    private Pump pump;
    private float desiredZoom;
    private boolean running;
    private long timeSinceResize;

    private Array<Button> buttons;

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
        init();
    }

    public void init() {
        world.init();
        desiredZoom = 0.5f;

        buttons.clear();
        buttons.add(new ButtonBase(viewport.getWorldWidth()/2 - 50, viewport.getWorldHeight() / 2 - 25, 100, 50, "test", viewport, () -> System.out.println("Working!")));

        running = true;
    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            pauseScreen = !pauseScreen;
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
        Gdx.gl.glClearColor(0.3f, 0.5f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        renderSky();

        world.render(batch, shapeRenderer);
        pump.render(batch, shapeRenderer);

        scoreViewport.apply();
        batch.setProjectionMatrix(scoreViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(scoreViewport.getCamera().combined);
        batch.begin();
        batch.setColor(Color.WHITE);

        Assets.instance.nonTextures.font.getData().setScale(0.3f);
        Assets.instance.nonTextures.font.draw(batch, "Score: " + (int) (world.getRadius()), 0, scoreViewport.getWorldHeight()-10);

        batch.end();

        if(pauseScreen) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.5f);
            shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            shapeRenderer.end();

            buttons.forEach(b -> b.render(shapeRenderer, batch));

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
            camera.zoom += dist * delta;
            if (Math.abs(dist) < 0.001f) {
                camera.zoom = desiredZoom;
            }
        }
    }

    private void calculateDesiredZoom() {
        if(Utils.secondsSince(timeSinceResize) < 3) return;
        boolean shouldChangeZoom = false;
        if((int) (world.getRadius() / 100) + 1 != camera.zoom) shouldChangeZoom = true;
        if(shouldChangeZoom) {
            desiredZoom = (int) (world.getRadius() / 100) + 1f;
            timeSinceResize = TimeUtils.nanoTime();
        }
    }

    private float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void endGame() {
        System.out.println("GAME OVER!");
        running = false;
    }
}
