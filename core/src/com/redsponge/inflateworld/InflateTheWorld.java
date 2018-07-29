package com.redsponge.inflateworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.inflateworld.screen.WorldScreen;

public class InflateTheWorld extends Game {

	public WorldScreen worldScreen;

	public static InflateTheWorld instance;

	@Override
	public void create() {
		instance = this;
		SpriteBatch batch = new SpriteBatch();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		worldScreen = new WorldScreen(batch, shapeRenderer);
		setScreen(worldScreen);
	}
}
