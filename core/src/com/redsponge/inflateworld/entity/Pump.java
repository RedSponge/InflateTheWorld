package com.redsponge.inflateworld.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.screen.WorldScreen;
import com.redsponge.inflateworld.util.Assets;
import com.redsponge.inflateworld.util.Reference;
import com.redsponge.inflateworld.util.Utils;

public class Pump extends InputAdapter {

    private World world;
    private Rectangle rectangle;
    private FitViewport viewport;
    private long lastClickTime;
    private int level;

    public Pump(World world, FitViewport viewport) {
        Gdx.input.setInputProcessor(this);
        this.world = world;
        this.rectangle = new Rectangle();
        this.viewport = viewport;
        init();
    }

    public void init() {
        level = 1;
    }

    public void update(float delta) {
        Vector2 top  = world.getTopPoint();
        rectangle.x = top.x - Reference.INFLATOR_SIZE.x / 2;
        rectangle.y = top.y;
        rectangle.width = Reference.INFLATOR_SIZE.x;
        rectangle.height = Reference.INFLATOR_SIZE.y;

        Vector3 mousePos = viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if(Gdx.input.justTouched() && !InflateTheWorld.instance.worldScreen.isZooming()) {
            if(new Rectangle(mousePos.x, mousePos.y, 1, 1).overlaps(rectangle)) {
                world.inflate(level);
                WorldScreen.tutorial = false;
                InflateTheWorld.instance.worldScreen.moneyManager.updateMoney(1);
                lastClickTime = TimeUtils.nanoTime();
            }
        }
    }

    public void upgradeLevel() {
        level++;
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//        shapeRenderer.end();
        batch.begin();
        float currentHandleYOffset = 0;
        float secondsSince = Utils.secondsSince(lastClickTime);
        if(secondsSince < Reference.PUMP_ANIMATION_DURATION) {
            if(secondsSince < Reference.PUMP_ANIMATION_DURATION / 2) {
                currentHandleYOffset = Reference.PUMP_ANIMATION_HANDLE_MAX_HEIGHT * (secondsSince / (Reference.PUMP_ANIMATION_DURATION / 2));
            } else {
                currentHandleYOffset = Reference.PUMP_ANIMATION_HANDLE_MAX_HEIGHT - Reference.PUMP_ANIMATION_HANDLE_MAX_HEIGHT * ((secondsSince - Reference.PUMP_ANIMATION_DURATION / 2) / (Reference.PUMP_ANIMATION_DURATION / 2));
            }
        }
        batch.draw(Assets.instance.textures.pumpHandle, rectangle.x, rectangle.y+currentHandleYOffset, rectangle.width, rectangle.height);
        batch.draw(Assets.instance.textures.pumpBase, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        batch.end();
    }

    public int getLevel() {
        return level;
    }
}
