package com.redsponge.inflateworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.inflateworld.screen.MenuScreen;
import com.redsponge.inflateworld.screen.ShopScreen;
import com.redsponge.inflateworld.screen.StoryScreen;
import com.redsponge.inflateworld.screen.WorldScreen;
import com.redsponge.inflateworld.util.Assets;

public class InflateTheWorld extends Game {

	public WorldScreen worldScreen;
	public ShopScreen shopScreen;
	public MenuScreen menuScreen;
	public StoryScreen storyScreen;

	public static InflateTheWorld instance;

	@Override
	public void create() {
		instance = this;
		SpriteBatch batch = new SpriteBatch();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		worldScreen = new WorldScreen(batch, shapeRenderer);
		shopScreen = new ShopScreen(batch, shapeRenderer);
		menuScreen = new MenuScreen(batch, shapeRenderer);
		storyScreen = new StoryScreen(batch, shapeRenderer);
		Assets.instance.init(new AssetManager());
		setScreen(storyScreen);
	}
}
