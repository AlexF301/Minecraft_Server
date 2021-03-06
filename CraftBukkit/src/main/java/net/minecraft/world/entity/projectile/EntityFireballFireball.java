package net.minecraft.world.entity.projectile;

import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public abstract class EntityFireballFireball extends EntityFireball {

    private static final DataWatcherObject<ItemStack> e = DataWatcher.a(EntityFireballFireball.class, DataWatcherRegistry.g);

    public EntityFireballFireball(EntityTypes<? extends EntityFireballFireball> entitytypes, World world) {
        super(entitytypes, world);
    }

    public EntityFireballFireball(EntityTypes<? extends EntityFireballFireball> entitytypes, double d0, double d1, double d2, double d3, double d4, double d5, World world) {
        super(entitytypes, d0, d1, d2, d3, d4, d5, world);
    }

    public EntityFireballFireball(EntityTypes<? extends EntityFireballFireball> entitytypes, EntityLiving entityliving, double d0, double d1, double d2, World world) {
        super(entitytypes, entityliving, d0, d1, d2, world);
    }

    public void setItem(ItemStack itemstack) {
        if (itemstack.getItem() != Items.FIRE_CHARGE || itemstack.hasTag()) {
            this.getDataWatcher().set(EntityFireballFireball.e, SystemUtils.a(itemstack.cloneItemStack(), (itemstack1) -> { // CraftBukkit - decompile error
                itemstack1.setCount(1);
            }));
        }

    }

    public ItemStack getItem() {
        return (ItemStack) this.getDataWatcher().get(EntityFireballFireball.e);
    }

    @Override
    protected void initDatawatcher() {
        this.getDataWatcher().register(EntityFireballFireball.e, ItemStack.b);
    }

    @Override
    public void saveData(NBTTagCompound nbttagcompound) {
        super.saveData(nbttagcompound);
        ItemStack itemstack = this.getItem();

        if (!itemstack.isEmpty()) {
            nbttagcompound.set("Item", itemstack.save(new NBTTagCompound()));
        }

    }

    @Override
    public void loadData(NBTTagCompound nbttagcompound) {
        super.loadData(nbttagcompound);
        ItemStack itemstack = ItemStack.a(nbttagcompound.getCompound("Item"));

        if (!itemstack.isEmpty()) this.setItem(itemstack); // CraftBukkit - SPIGOT-5474 probably came from bugged earlier versions
    }
}
