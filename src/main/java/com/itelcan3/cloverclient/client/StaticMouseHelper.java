package com.itelcan3.cloverclient.client;

import net.minecraft.util.MouseHelper;

public class StaticMouseHelper extends MouseHelper {
    @Override
    public void mouseXYChange() {
        deltaX = 0;
        deltaY = 0;
    }
}