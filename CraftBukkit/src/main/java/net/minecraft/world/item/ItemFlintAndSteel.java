package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;

public class ItemFlintAndSteel extends Item {

    public ItemFlintAndSteel(Item.Info item_info) {
        super(item_info);
    }

    @Override
    public EnumInteractionResult a(ItemActionContext itemactioncontext) {
        EntityHuman entityhuman = itemactioncontext.getEntity();
        World world = itemactioncontext.getWorld();
        BlockPosition blockposition = itemactioncontext.getClickPosition();
        IBlockData iblockdata = world.getType(blockposition);

        if (BlockCampfire.h(iblockdata) && !org.bukkit.craftbukkit.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition, entityhuman).isCancelled()) { // CraftBukkit
            world.playSound(entityhuman, blockposition, SoundEffects.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, ItemFlintAndSteel.RANDOM.nextFloat() * 0.4F + 0.8F);
            world.setTypeAndData(blockposition, (IBlockData) iblockdata.set(BlockProperties.r, true), 11);
            if (entityhuman != null) {
                itemactioncontext.getItemStack().damage(1, entityhuman, (entityhuman1) -> {
                    entityhuman1.broadcastItemBreak(itemactioncontext.getHand());
                });
            }

            return EnumInteractionResult.a(world.s_());
        } else {
            BlockPosition blockposition1 = blockposition.shift(itemactioncontext.getClickedFace());

            if (BlockFireAbstract.a(world, blockposition1, itemactioncontext.f())) {
                // CraftBukkit start - Store the clicked block
                if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition1, org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
                    itemactioncontext.getItemStack().damage(1, entityhuman, (entityhuman1) -> {
                        entityhuman1.broadcastItemBreak(itemactioncontext.getHand());
                    });
                    return EnumInteractionResult.PASS;
                }
                // CraftBukkit end
                world.playSound(entityhuman, blockposition1, SoundEffects.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, ItemFlintAndSteel.RANDOM.nextFloat() * 0.4F + 0.8F);
                IBlockData iblockdata1 = BlockFireAbstract.a((IBlockAccess) world, blockposition1);

                world.setTypeAndData(blockposition1, iblockdata1, 11);
                ItemStack itemstack = itemactioncontext.getItemStack();

                if (entityhuman instanceof EntityPlayer) {
                    CriterionTriggers.y.a((EntityPlayer) entityhuman, blockposition1, itemstack);
                    itemstack.damage(1, entityhuman, (entityhuman1) -> {
                        entityhuman1.broadcastItemBreak(itemactioncontext.getHand());
                    });
                }

                return EnumInteractionResult.a(world.s_());
            } else {
                return EnumInteractionResult.FAIL;
            }
        }
    }
}
