package com.redsponge.inflateworld.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.util.Reference;

public class Inflator extends InputAdapter {

    private World world;
    private Rectangle rectangle;
    private boolean justClicked;
    private boolean clicked;
    private FitViewport viewport;

    public Inflator(World world, FitViewport viewport) {
        Gdx.input.setInputProcessor(this);
        this.world = world;
        this.rectangle = new Rectangle();
        this.viewport = viewport;
    }

    public void update(float delta) {
        Vector2 top  = world.getTopPoint();
        rectangle.x = top.x - Reference.INFLATOR_SIZE.x / 2;
        rectangle.y = top.y;
        rectangle.width = Reference.INFLATOR_SIZE.x;
        rectangle.height = Reference.INFLATOR_SIZE.y;

        Vector3 mousePos = viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if(clicked && !justClicked) {
            System.out.println("HI");
            System.out.println(mousePos);
            System.out.println(rectangle);
            if(new Rectangle(mousePos.x, mousePos.y, 1, 1).overlaps(rectangle)) {
                System.out.println("WORK");
                world.inflate(1);
            }
        }

        justClicked = clicked;
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapeRenderer.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button != Buttons.LEFT || pointer > 0) return false;
        clicked = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button != Buttons.LEFT || pointer > 0) return false;
        clicked = false;
        return true;
    }
}
