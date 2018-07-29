package com.redsponge.inflateworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Utils {

    public static float secondsSince(long nano) {
        return (TimeUtils.nanoTime() - nano) / 1000000000f;
    }

    public static void drawTextCentered(SpriteBatch batch, CharSequence text, BitmapFont font, int y, float screenWidth) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, text, screenWidth / 2 - layout.width / 2, y);
    }

    public static void drawTextureCentered(SpriteBatch batch, Texture texture, float screenWidth, int y) {
        batch.draw(texture, screenWidth / 2 - texture.getWidth() / 2f, y);
    }


}
