package com.redsponge.inflateworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

    public static final Assets instance = new Assets();

    private AssetManager manager;
    public NonTextures nonTextures;
    public Textures textures;

    public void init(AssetManager manager) {
        this.manager = manager;
        nonTextures = new NonTextures();
        textures = new Textures(manager);
    }

    public class NonTextures {

        public final BitmapFont font;

        public NonTextures() {
            font = new BitmapFont(Gdx.files.internal(Reference.FONT_PATH));
        }
    }

    public class Textures {

        public final Texture world;
        public final Texture pumpBase;
        public final Texture pumpHandle;

        public Textures(AssetManager manager) {
            manager.load(Reference.WORLD_TEXTURE_PATH, Texture.class);
            manager.load(Reference.PUMP_BASE_TEXTURE_PATH, Texture.class);
            manager.load(Reference.PUMP_HANDLE_TEXTURE_PATH, Texture.class);
            manager.finishLoading();

            world = manager.get(Reference.WORLD_TEXTURE_PATH);
            pumpBase = manager.get(Reference.PUMP_BASE_TEXTURE_PATH);
            pumpHandle = manager.get(Reference.PUMP_HANDLE_TEXTURE_PATH);
        }
    }
}
