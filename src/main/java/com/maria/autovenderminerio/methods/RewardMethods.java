package com.maria.autovenderminerio.methods;

import java.util.Random;

public class RewardMethods {

    public int nextInt(int min, int max) {
        int x = new Random().nextInt(max) + min;
        int y = new Random().nextInt(max) + min;

        return mid(x, y);
    }

    private int mid(int x, int y) {
        return x + y / 2;
    }

}
