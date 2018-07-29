package com.redsponge.inflateworld.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.ui.Button;
import com.redsponge.inflateworld.ui.ButtonBase;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;

public class StoryScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;

    private int page;
    private int totalPages;
    private Button buttonNext, buttonBack, buttonMenu;

    private String[] pages;

    public StoryScreen(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.viewport = new FitViewport(Reference.STORY_SCREEN.x, Reference.STORY_SCREEN.y);
        buttonNext = new ButtonBase(viewport.getWorldWidth() - 100, 10, 100, 25, "Next >", viewport, this::nextPage);
        buttonBack = new ButtonBase(0, 10, 100, 25, "< Back", viewport, this::backPage);
        buttonMenu = new ButtonBase(viewport.getWorldWidth() - 100, 10, 100, 25, "Menu >", viewport, () -> InflateTheWorld.instance.setScreen(InflateTheWorld.instance.menuScreen));

        pages = new String[] {
                "In the year of 3724,\nthe thing scientists feared\nwould happen for a few hundred years\nfinally happened,\nand the world began the shrink!",
                "In last attempts to save it,\nthe most intelligent scientists\nfrom all over the world have teamed up\nto find a solution.",
                "After days of thinking, it seemed\nnobody could come up with anything\nthat would work, and then,\nwhen it seemed all hope was gone...",
                "One of the people shouted: EUREKA!\nand started telling the other\npeople in the room his idea.",
                "Everyone agreed it was the best solution\npossible currently, and started\nworking on it.",
                "And after a few days of hard work,\nthey finished it...",
                "A pump, connected to the world,\nto inflate it back to its original size!"
        };
    }

    @Override
    public void show() {
        page = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Reference.SKY_COLOR.r, Reference.SKY_COLOR.g, Reference.SKY_COLOR.b, Reference.SKY_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        Assets.instance.nonTextures.font.setColor(Color.WHITE);
        Assets.instance.nonTextures.font.getData().setScale(0.25f);

        Assets.instance.nonTextures.font.draw(batch, pages[page], 0, viewport.getWorldHeight()/4*3);

        if(page == 0) {
            batch.draw(Assets.instance.textures.world, viewport.getWorldWidth() / 2 - 100, 0, 100, 100);
        }

        if(page == 3) {
            batch.draw(Assets.instance.textures.lightbulb, viewport.getWorldWidth() / 2 - 50, 0, 100, 120);
        }

        if(page == pages.length-1) {
            batch.draw(Assets.instance.textures.pumpHandle, viewport.getWorldWidth() / 2 - 25, 10, 50, 100);
            batch.draw(Assets.instance.textures.pumpBase, viewport.getWorldWidth() / 2 - 25, 10, 50, 100);

            buttonMenu.update(delta);
            batch.end();
            buttonMenu.render(shapeRenderer, batch);
            batch.begin();
        }

        batch.end();

        if(page != pages.length - 1) {
            buttonNext.update(delta);
            buttonNext.render(shapeRenderer, batch);
        }
        if(page != 0) {
            buttonBack.update(delta);
            buttonBack.render(shapeRenderer, batch);
        }
    }

    private void nextPage() {
        if(page == pages.length-1) InflateTheWorld.instance.setScreen(InflateTheWorld.instance.menuScreen);
        page++;
    }

    private void backPage() {
        if(page == 0) return;
        page--;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
