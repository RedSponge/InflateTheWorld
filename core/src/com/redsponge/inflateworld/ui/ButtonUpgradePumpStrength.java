package com.redsponge.inflateworld.ui;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.redsponge.inflateworld.InflateTheWorld;
import com.redsponge.inflateworld.entity.Pump;

public class ButtonUpgradePumpStrength extends Button {

    private Pump pump;
    public ButtonUpgradePumpStrength(float x, float y, float width, float height, Viewport viewport) {
        super(x, y, width, height, "", viewport);
        this.pump = InflateTheWorld.instance.worldScreen.getPump();
        generateLabel();
    }

    public int getCost() {
        return pump.getLevel() * 50;
    }

    private void generateLabel() {
        this.label = "Upgrade Pump\nStrength\nCost: " + getCost();
    }

    @Override
    public void trigger() {
        if(InflateTheWorld.instance.worldScreen.moneyManager.getMoney() >= getCost()) {
            InflateTheWorld.instance.worldScreen.moneyManager.updateMoney(-getCost());
            pump.upgradeLevel();
            generateLabel();
        }
    }
}
