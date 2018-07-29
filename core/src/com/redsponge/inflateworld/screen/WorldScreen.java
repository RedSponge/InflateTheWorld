package com.redsponge.inflateworld.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redsponge.inflateworld.entity.Inflator;
import com.redsponge.inflateworld.entity.World;
import com.redsponge.inflateworld.util.Reference;

public class WorldScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private FitViewport scoreViewport;
    private OrthographicCamera camera;

    private World world;
    private Inflator inflator;
    private float desiredZoom;
    private boolean running;

    private BitmapFont font;

    public WorldScreen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        camera = new OrthographicCamera(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        camera.setToOrtho(false);
        viewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y, camera);

        scoreViewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        world = new World(viewport);
        inflator = new Inflator(world, viewport);
        init();
    }

    public void init() {
        world.init();
        desiredZoom = 0.5f;
        running = true;
    }

    public void update(float delta) {
        if(!running) return;
        world.update(delta);
        inflator.update(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        renderSky();
        world.render(batch, shapeRenderer);
        inflator.render(batch, shapeRenderer);

        scoreViewport.apply();
        batch.setProjectionMatrix(scoreViewport.getCamera().combined);

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
        desiredZoom = map(world.getRadius(), 0, 100, 0, 2.0f)+1;
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
