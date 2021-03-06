package net.minecraft.world.entity;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;

public class SaddleStorage {

    private final DataWatcher dataWatcher;
    private final DataWatcherObject<Integer> dataWatcherBoostTicks;
    private final DataWatcherObject<Boolean> f;
    public boolean boosting;
    public int currentBoostTicks;
    public int boostTicks;

    public SaddleStorage(DataWatcher datawatcher, DataWatcherObject<Integer> datawatcherobject, DataWatcherObject<Boolean> datawatcherobject1) {
        this.dataWatcher = datawatcher;
        this.dataWatcherBoostTicks = datawatcherobject;
        this.f = datawatcherobject1;
    }

    public void a() {
        this.boosting = true;
        this.currentBoostTicks = 0;
        this.boostTicks = (Integer) this.dataWatcher.get(this.dataWatcherBoostTicks);
    }

    public boolean a(Random random) {
        if (this.boosting) {
            return false;
        } else {
            this.boosting = true;
            this.currentBoostTicks = 0;
            this.boostTicks = random.nextInt(841) + 140;
            this.dataWatcher.set(this.dataWatcherBoostTicks, this.boostTicks);
            return true;
        }
    }

    // CraftBukkit add setBoostTicks(int)
    public void setBoostTicks(int ticks) {
        this.boosting = true;
        this.currentBoostTicks = 0;
        this.boostTicks = ticks;
        this.dataWatcher.set(this.dataWatcherBoostTicks, this.boostTicks);
    }
    // CraftBukkit end

    public void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.setBoolean("Saddle", this.hasSaddle());
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.setSaddle(nbttagcompound.getBoolean("Saddle"));
    }

    public void setSaddle(boolean flag) {
        this.dataWatcher.set(this.f, flag);
    }

    public boolean hasSaddle() {
        return (Boolean) this.dataWatcher.get(this.f);
    }
}
