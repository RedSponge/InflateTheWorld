package com.redsponge.inflateworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;

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
        public final BitmapFont titleFont;
        public final BitmapFont fontSmall;

        public NonTextures() {
            font = new BitmapFont(Gdx.files.internal(Reference.FONT_PATH));
            titleFont = new BitmapFont(Gdx.files.internal(Reference.TITLE_FONT_PATH));
            fontSmall = new BitmapFont(Gdx.files.internal(Reference.FONT_SMALL_PATH));
        }
    }

    public class Textures {

        public final Texture world;
        public final Texture pumpBase;
        public final Texture pumpHandle;

        public final NinePatch buttonRegular;
        public final NinePatch buttonSelected;

        public final Texture title;
        public final Texture lightbulb;

        public Textures(AssetManager manager) {
            manager.load(Reference.WORLD_TEXTURE_PATH, Texture.class);
            manager.load(Reference.PUMP_BASE_TEXTURE_PATH, Texture.class);
            manager.load(Reference.PUMP_HANDLE_TEXTURE_PATH, Texture.class);
            manager.load(Reference.BUTTON_REGULAR_TEXTURE, Texture.class);
            manager.load(Reference.BUTTON_SELECTED_TEXTURE, Texture.class);
            manager.load(Reference.TITLE_TEXTURE, Texture.class);
            manager.load(Reference.LIGHTBULB, Texture.class);
            manager.finishLoading();

            world = manager.get(Reference.WORLD_TEXTURE_PATH);
            pumpBase = manager.get(Reference.PUMP_BASE_TEXTURE_PATH);
            pumpHandle = manager.get(Reference.PUMP_HANDLE_TEXTURE_PATH);

            Texture buttonTexture = manager.get(Reference.BUTTON_REGULAR_TEXTURE);
            buttonRegular = new NinePatch(buttonTexture, 5, 5, 5, 5);

            Texture selectedButtonTexture = manager.get(Reference.BUTTON_SELECTED_TEXTURE);
            buttonSelected = new NinePatch(selectedButtonTexture, 5, 5, 5, 5);

            title = manager.get(Reference.TITLE_TEXTURE);

            lightbulb = manager.get(Reference.LIGHTBULB);
        }
    }
}
