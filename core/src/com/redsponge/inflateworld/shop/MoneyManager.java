package com.redsponge.inflateworld.shop;

public class MoneyManager {

    private int money;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void updateMoney(int money) {
        this.money += money;
    }
}
