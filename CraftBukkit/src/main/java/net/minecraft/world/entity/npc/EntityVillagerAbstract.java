package net.minecraft.world.entity.npc;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.pathfinder.PathType;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.inventory.CraftMerchant;
import org.bukkit.craftbukkit.inventory.CraftMerchantRecipe;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
// CraftBukkit end

public abstract class EntityVillagerAbstract extends EntityAgeable implements NPC, IMerchant {

    // CraftBukkit start
    private CraftMerchant craftMerchant;

    @Override
    public CraftMerchant getCraftMerchant() {
        return (craftMerchant == null) ? craftMerchant = new CraftMerchant(this) : craftMerchant;
    }
    // CraftBukkit end
    private static final DataWatcherObject<Integer> bp = DataWatcher.a(EntityVillagerAbstract.class, DataWatcherRegistry.b);
    @Nullable
    private EntityHuman tradingPlayer;
    @Nullable
    protected MerchantRecipeList trades;
    private final InventorySubcontainer inventory = new InventorySubcontainer(8, (org.bukkit.craftbukkit.entity.CraftAbstractVillager) this.getBukkitEntity()); // CraftBukkit add argument

    public EntityVillagerAbstract(EntityTypes<? extends EntityVillagerAbstract> entitytypes, World world) {
        super(entitytypes, world);
        this.a(PathType.DANGER_FIRE, 16.0F);
        this.a(PathType.DAMAGE_FIRE, -1.0F);
    }

    @Override
    public GroupDataEntity prepare(WorldAccess worldaccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        if (groupdataentity == null) {
            groupdataentity = new EntityAgeable.a(false);
        }

        return super.prepare(worldaccess, difficultydamagescaler, enummobspawn, (GroupDataEntity) groupdataentity, nbttagcompound);
    }

    public int eK() {
        return (Integer) this.datawatcher.get(EntityVillagerAbstract.bp);
    }

    public void s(int i) {
        this.datawatcher.set(EntityVillagerAbstract.bp, i);
    }

    @Override
    public int getExperience() {
        return 0;
    }

    @Override
    protected float b(EntityPose entitypose, EntitySize entitysize) {
        return this.isBaby() ? 0.81F : 1.62F;
    }

    @Override
    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.register(EntityVillagerAbstract.bp, 0);
    }

    @Override
    public void setTradingPlayer(@Nullable EntityHuman entityhuman) {
        this.tradingPlayer = entityhuman;
    }

    @Nullable
    @Override
    public EntityHuman getTrader() {
        return this.tradingPlayer;
    }

    public boolean eN() {
        return this.tradingPlayer != null;
    }

    @Override
    public MerchantRecipeList getOffers() {
        if (this.trades == null) {
            this.trades = new MerchantRecipeList();
            this.eW();
        }

        return this.trades;
    }

    @Override
    public void setForcedExperience(int i) {}

    @Override
    public void a(MerchantRecipe merchantrecipe) {
        merchantrecipe.increaseUses();
        this.e = -this.D();
        this.b(merchantrecipe);
        if (this.tradingPlayer instanceof EntityPlayer) {
            CriterionTriggers.s.a((EntityPlayer) this.tradingPlayer, this, merchantrecipe.getSellingItem());
        }

    }

    protected abstract void b(MerchantRecipe merchantrecipe);

    @Override
    public boolean isRegularVillager() {
        return true;
    }

    @Override
    public void k(ItemStack itemstack) {
        if (!this.world.isClientSide && this.e > -this.D() + 20) {
            this.e = -this.D();
            this.playSound(this.t(!itemstack.isEmpty()), this.getSoundVolume(), this.dH());
        }

    }

    @Override
    public SoundEffect getTradeSound() {
        return SoundEffects.ENTITY_VILLAGER_YES;
    }

    protected SoundEffect t(boolean flag) {
        return flag ? SoundEffects.ENTITY_VILLAGER_YES : SoundEffects.ENTITY_VILLAGER_NO;
    }

    public void eR() {
        this.playSound(SoundEffects.ENTITY_VILLAGER_CELEBRATE, this.getSoundVolume(), this.dH());
    }

    @Override
    public void saveData(NBTTagCompound nbttagcompound) {
        super.saveData(nbttagcompound);
        MerchantRecipeList merchantrecipelist = this.getOffers();

        if (!merchantrecipelist.isEmpty()) {
            nbttagcompound.set("Offers", merchantrecipelist.a());
        }

        nbttagcompound.set("Inventory", this.inventory.g());
    }

    @Override
    public void loadData(NBTTagCompound nbttagcompound) {
        super.loadData(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("Offers", 10)) {
            this.trades = new MerchantRecipeList(nbttagcompound.getCompound("Offers"));
        }

        this.inventory.a(nbttagcompound.getList("Inventory", 10));
    }

    @Nullable
    @Override
    public Entity b(WorldServer worldserver) {
        this.eT();
        return super.b(worldserver);
    }

    protected void eT() {
        this.setTradingPlayer((EntityHuman) null);
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        this.eT();
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    public InventorySubcontainer getInventory() {
        return this.inventory;
    }

    @Override
    public boolean a_(int i, ItemStack itemstack) {
        if (super.a_(i, itemstack)) {
            return true;
        } else {
            int j = i - 300;

            if (j >= 0 && j < this.inventory.getSize()) {
                this.inventory.setItem(j, itemstack);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    protected abstract void eW();

    protected void a(MerchantRecipeList merchantrecipelist, VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption, int i) {
        Set<Integer> set = Sets.newHashSet();

        if (avillagertrades_imerchantrecipeoption.length > i) {
            while (set.size() < i) {
                set.add(this.random.nextInt(avillagertrades_imerchantrecipeoption.length));
            }
        } else {
            for (int j = 0; j < avillagertrades_imerchantrecipeoption.length; ++j) {
                set.add(j);
            }
        }

        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            VillagerTrades.IMerchantRecipeOption villagertrades_imerchantrecipeoption = avillagertrades_imerchantrecipeoption[integer];
            MerchantRecipe merchantrecipe = villagertrades_imerchantrecipeoption.a(this, this.random);

            if (merchantrecipe != null) {
                // CraftBukkit start
                VillagerAcquireTradeEvent event = new VillagerAcquireTradeEvent((AbstractVillager) getBukkitEntity(), merchantrecipe.asBukkit());
                // Suppress during worldgen
                if (this.valid) {
                    Bukkit.getPluginManager().callEvent(event);
                }
                if (!event.isCancelled()) {
                    merchantrecipelist.add(CraftMerchantRecipe.fromBukkit(event.getRecipe()).toMinecraft());
                }
                // CraftBukkit end
            }
        }

    }
}
