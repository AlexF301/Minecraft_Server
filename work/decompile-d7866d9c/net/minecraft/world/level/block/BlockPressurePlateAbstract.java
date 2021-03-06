package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public abstract class BlockPressurePlateAbstract extends Block {

    protected static final VoxelShape a = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 0.5D, 15.0D);
    protected static final VoxelShape b = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

    protected BlockPressurePlateAbstract(BlockBase.Info blockbase_info) {
        super(blockbase_info);
    }

    @Override
    public VoxelShape b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
        return this.getPower(iblockdata) > 0 ? BlockPressurePlateAbstract.a : BlockPressurePlateAbstract.b;
    }

    protected int c() {
        return 20;
    }

    @Override
    public boolean ai_() {
        return true;
    }

    @Override
    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    @Override
    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        BlockPosition blockposition1 = blockposition.down();

        return c((IBlockAccess) iworldreader, blockposition1) || a(iworldreader, blockposition1, EnumDirection.UP);
    }

    @Override
    public void tickAlways(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, Random random) {
        int i = this.getPower(iblockdata);

        if (i > 0) {
            this.a(worldserver, blockposition, iblockdata, i);
        }

    }

    @Override
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
        if (!world.isClientSide) {
            int i = this.getPower(iblockdata);

            if (i == 0) {
                this.a(world, blockposition, iblockdata, i);
            }

        }
    }

    protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
        int j = this.b(world, blockposition);
        boolean flag = i > 0;
        boolean flag1 = j > 0;

        if (i != j) {
            IBlockData iblockdata1 = this.a(iblockdata, j);

            world.setTypeAndData(blockposition, iblockdata1, 2);
            this.a(world, blockposition);
            world.b(blockposition, iblockdata, iblockdata1);
        }

        if (!flag1 && flag) {
            this.b((GeneratorAccess) world, blockposition);
        } else if (flag1 && !flag) {
            this.a((GeneratorAccess) world, blockposition);
        }

        if (flag1) {
            world.getBlockTickList().a(new BlockPosition(blockposition), this, this.c());
        }

    }

    protected abstract void a(GeneratorAccess generatoraccess, BlockPosition blockposition);

    protected abstract void b(GeneratorAccess generatoraccess, BlockPosition blockposition);

    @Override
    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (!flag && !iblockdata.a(iblockdata1.getBlock())) {
            if (this.getPower(iblockdata) > 0) {
                this.a(world, blockposition);
            }

            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
        }
    }

    protected void a(World world, BlockPosition blockposition) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.down(), this);
    }

    @Override
    public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return this.getPower(iblockdata);
    }

    @Override
    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? this.getPower(iblockdata) : 0;
    }

    @Override
    public boolean isPowerSource(IBlockData iblockdata) {
        return true;
    }

    @Override
    public EnumPistonReaction getPushReaction(IBlockData iblockdata) {
        return EnumPistonReaction.DESTROY;
    }

    protected abstract int b(World world, BlockPosition blockposition);

    protected abstract int getPower(IBlockData iblockdata);

    protected abstract IBlockData a(IBlockData iblockdata, int i);
}
