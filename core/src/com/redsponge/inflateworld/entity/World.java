package com.redsponge.inflateworld.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;
import com.redsponge.inflateworld.util.Utils;

public class World {

    private float radius;
    private float displayedRadius;
    private FitViewport viewport;
    private int difficulty;
    private long startTime;
    private Array<Integer> changedAt;

    public World(FitViewport viewport) {
        this.viewport = viewport;
    }

    public void init() {
        radius = 51;
        displayedRadius = radius;
        difficulty = 1;
        startTime = TimeUtils.nanoTime();
        changedAt = new Array<>();
        changedAt.add(0);
    }

    public void update(float delta) {
        if(!InflateTheWorld.instance.worldScreen.isZooming())
            radius -= 0.025 * difficulty;
        if(radius < 5) {
            InflateTheWorld.instance.worldScreen.endGame();
        }
        displayedRadius += (radius - displayedRadius) * delta;
        if((int) Utils.secondsSince(startTime) % 20 == 0 && !changedAt.contains((int)Utils.secondsSince(startTime), false)) {
            difficulty++;
            changedAt.add((int) Utils.secondsSince(startTime));
        }
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
