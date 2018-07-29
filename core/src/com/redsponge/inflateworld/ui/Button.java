package com.redsponge.inflateworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.InputUtils;
import com.redsponge.inflateworld.util.Utils;

public abstract class Button extends Rectangle {

    private boolean selected;
    private String label;
    private Viewport viewport;
    protected final Runnable task;

    protected Button(float x, float y, float width, float height, String label, Viewport viewport) {
        super(x, y, width, height);
        this.label = label;
        this.viewport = viewport;
        this.task = this::trigger;
    }

    public Button(float x, float y, float width, float height, String label, Viewport viewport, Runnable task) {
        super(x, y, width, height);
        this.label = label;
        this.viewport = viewport;
        this.task = task;
    }

    public void update(float delta) {
        if(this.overlaps(InputUtils.getMousePositionAsRect(viewport))) {
            selected = true;
        } else {
            selected = false;
        }

        if(selected) {
            if(Gdx.input.justTouched()) {
                System.out.println("CLICK!");
            }
        }
    }

    public abstract void trigger();

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        if(selected) shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();
        Assets.instance.nonTextures.font.draw(batch, label, x, y+width/2);
        batch.end();
    }
}
