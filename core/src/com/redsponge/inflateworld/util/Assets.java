package com.redsponge.inflateworld.util;

import com.badlogic.gdx.assets.AssetManager;

public class Assets {

    public static final Assets instance = new Assets();

    private AssetManager manager;

    public void init(AssetManager manager) {
        this.manager = manager;
    }
}
