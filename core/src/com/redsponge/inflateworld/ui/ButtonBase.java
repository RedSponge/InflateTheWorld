package com.redsponge.inflateworld.ui;

import com.badlogic.gdx.utils.viewport.Viewport;

public class ButtonBase extends Button {

    public ButtonBase(float x, float y, float width, float height, String label, Viewport viewport, Runnable task) {
        super(x, y, width, height, label, viewport, task);
    }

    @Override
    public void trigger() {
        task.run();
    }
}
