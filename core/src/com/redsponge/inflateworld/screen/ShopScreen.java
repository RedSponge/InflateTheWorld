package com.redsponge.inflateworld.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.ui.Button;
import com.redsponge.inflateworld.ui.ButtonBase;
import com.redsponge.inflateworld.ui.ButtonUpgradePumpStrength;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;
import com.redsponge.inflateworld.util.Utils;

public class ShopScreen extends ScreenAdapter {

    private FitViewport viewport;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Array<Button> buttons;

    public ShopScreen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.viewport = new FitViewport(Reference.WORLD_SIZE.x, Reference.WORLD_SIZE.y);
        buttons = new Array<>();
    }

    @Override
    public void show() {
        buttons.clear();
        buttons.add(new ButtonUpgradePumpStrength(
                viewport.getWorldWidth() / 4 - 50, viewport.getWorldHeight() / 2 - 25, 100, 50, viewport
        ));
        buttons.add(new ButtonBase(viewport.getWorldWidth() / 4 * 3 - 50, 10, 100, 25, "Back to game", viewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.worldScreen)));
    }

    public void update(float delta) {
        buttons.forEach(b -> b.update(delta));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();


        batch.begin();
        Assets.instance.nonTextures.font.setColor(Color.BLACK);
        Assets.instance.nonTextures.font.getData().setScale(.4f);
        Utils.drawTextCentered(batch, "Welcome to the shop!", Assets.instance.nonTextures.font, (int) viewport.getWorldHeight() - 30, viewport.getWorldWidth());
        Utils.drawTextCentered(batch, "Money: " + InflateTheWorld.instance.worldScreen.moneyManager.getMoney(), Assets.instance.nonTextures.font, (int) viewport.getWorldHeight() - 50, viewport.getWorldWidth());
        batch.end();

        Assets.instance.nonTextures.font.setColor(Color.WHITE);
        buttons.forEach(b -> b.render(shapeRenderer, batch));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
