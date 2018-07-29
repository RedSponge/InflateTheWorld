package com.redsponge.inflateworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.InputUtils;

public abstract class Button extends Rectangle {

    private boolean selected;
    protected String label;
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

    public Rectangle fixed() {
        return new Rectangle(x + viewport.getLeftGutterWidth(), y, width, height);
    }

    public void update(float delta) {
        if(this.overlaps(InputUtils.getMousePositionAsRect(viewport))) {
//            System.out.println(this + " " + InputUtils.getMousePositionAsRect(viewport));
            selected = true;
        } else {
            selected = false;
        }

        if(selected) {
            if(Gdx.input.justTouched()) {
                trigger();
            }
        }
    }

    public abstract void trigger();

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {

//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.rect(x, y, width, height);
//        shapeRenderer.end();



        Assets.instance.nonTextures.font.getData().setScale(0.25f);
        GlyphLayout layout = new GlyphLayout(Assets.instance.nonTextures.font, label);

        NinePatch toDraw = Assets.instance.textures.buttonRegular;
        if(selected) toDraw = Assets.instance.textures.buttonSelected;

        batch.begin();

        toDraw.draw(batch, x, y, width, height);

        Assets.instance.nonTextures.font.draw(batch, label, x + width / 2 - layout.width / 2, y + height / 2 + layout.height / 2);
        batch.end();
    }

    public static Button centeredAroundX(float y, float width, float height, String label, Viewport viewport, Runnable task) {
        return new ButtonBase(viewport.getWorldWidth() / 2 - width / 2, y, width, height, label, viewport, task);
    }
}
