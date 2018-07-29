package com.redsponge.inflateworld.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.ui.Button;
import com.redsponge.inflateworld.ui.ButtonBase;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;
import com.redsponge.inflateworld.util.Utils;

public class MenuScreen extends ScreenAdapter {

    private Array<Button> buttons;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private int angle;

    private FitViewport viewport;

    public MenuScreen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        viewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        angle = 0;
    }

    @Override
    public void show() {
        buttons = new Array<>();
        buttons.add(Button.centeredAroundX(75, 80, 25, "Start Game", viewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.worldScreen)));
        buttons.add(Button.centeredAroundX(50, 40, 25, "Story", viewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.storyScreen)));
        buttons.add(Button.centeredAroundX(25, 40, 25, "Quit", viewport, () -> System.exit(0)));
    }

    public void update(float delta) {
        buttons.forEach(b -> b.update(delta));
        angle+=360 * delta;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Reference.SKY_COLOR.r, Reference.SKY_COLOR.g, Reference.SKY_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        int backgroundWorldSize = 80;
        batch.draw(
                Assets.instance.textures.world, viewport.getWorldWidth()/2 - backgroundWorldSize, viewport.getWorldHeight()/2 - backgroundWorldSize, viewport.getWorldHeight() / 2 - backgroundWorldSize / 2f, viewport.getWorldHeight() / 2 - backgroundWorldSize / 2f, backgroundWorldSize*2, backgroundWorldSize*2,
                1, 1, (float) Math.toRadians(angle), 0, 0, Assets.instance.textures.world.getWidth(), Assets.instance.textures.world.getHeight(), false, false);

        batch.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeType.Filled);
        Color c = new Color(Reference.SKY_COLOR);
        c.a = 0.5f;
        shapeRenderer.setColor(c);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        Utils.drawTextureCentered(batch, Assets.instance.textures.title, viewport.getWorldWidth(), (int) viewport.getWorldHeight() - 128);
        Assets.instance.nonTextures.fontSmall.getData().setScale(0.5f);
        Utils.drawTextCentered(batch, "Made by RedSponge for the 2018 July Libgdx jam", Assets.instance.nonTextures.fontSmall, 20, viewport.getWorldWidth());
        batch.end();
        buttons.forEach(b -> b.render(shapeRenderer, batch));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
