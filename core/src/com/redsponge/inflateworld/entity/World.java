package com.redsponge.inflateworld.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.util.Reference;

public class World {

    private float radius;
    private FitViewport viewport;

    public World(FitViewport viewport) {
        this.viewport = viewport;
    }

    public void init() {
        radius = 50;
    }

    public void update(float delta) {
        radius -= 0.1;
        if(radius < 5) {
            InflateTheWorld.instance.worldScreen.endGame();
        }
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, radius, 50);
        shapeRenderer.end();
    }

    public Vector2 getTopPoint() {
        return new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 + radius);
    }

    public void inflate(int num) {
        radius += num;
    }

    public float getRadius() {
        return radius;
    }
}
