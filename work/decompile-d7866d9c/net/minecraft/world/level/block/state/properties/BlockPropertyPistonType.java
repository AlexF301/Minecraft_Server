package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyPistonType implements INamable {

    DEFAULT("normal"), STICKY("sticky");

    private final String c;

    private BlockPropertyPistonType(String s) {
        this.c = s;
    }

    public String toString() {
        return this.c;
    }

    @Override
    public String getName() {
        return this.c;
    }
}
