package com.redsponge.inflateworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputUtils {

    public static Vector3 getMousePositionRelativeToViewport(Viewport viewport) {
        return viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public static Rectangle getMousePositionAsRect(Viewport viewport) {
        Vector3 mp = getMousePositionRelativeToViewport(viewport);
        return new Rectangle(mp.x, mp.y, 1, 1);
    }

}
