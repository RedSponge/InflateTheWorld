package com.redsponge.inflateworld.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;

public class World {

    private float radius;
    private float displayedRadius;
    private FitViewport viewport;

    public World(FitViewport viewport) {
        this.viewport = viewport;
    }

    public void init() {
        radius = 50;
        displayedRadius = radius;
    }

    public void update(float delta) {
        radius -= 0.1;
        if(radius < 5) {
            InflateTheWorld.instance.worldScreen.endGame();
        }
        displayedRadius += (radius - displayedRadius) * delta;
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.circle(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, displayedRadius, 50);
//        shapeRenderer.end();
        batch.begin();
        batch.draw(Assets.instance.textures.world, viewport.getWorldWidth() / 2 - displayedRadius, viewport.getWorldHeight() / 2 - displayedRadius, displayedRadius*2, displayedRadius*2);
        batch.end();
    }

    public Vector2 getTopPoint() {
        return new Vector2(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2 + displayedRadius);
    }

    public void inflate(int num) {
        radius += num;
    }

    public float getRadius() {
        return radius;
    }
}
