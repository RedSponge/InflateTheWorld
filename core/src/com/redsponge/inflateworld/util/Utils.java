package com.redsponge.inflateworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Utils {

    public static float secondsSince(long nano) {
        return (TimeUtils.nanoTime() - nano) / 1000000000f;
    }


}
