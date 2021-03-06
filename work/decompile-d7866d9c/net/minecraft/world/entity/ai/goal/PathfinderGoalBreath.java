package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Iterator;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalBreath extends PathfinderGoal {

    private final EntityCreature a;

    public PathfinderGoalBreath(EntityCreature entitycreature) {
        this.a = entitycreature;
        this.a(EnumSet.of(PathfinderGoal.Type.MOVE, PathfinderGoal.Type.LOOK));
    }

    @Override
    public boolean a() {
        return this.a.getAirTicks() < 140;
    }

    @Override
    public boolean b() {
        return this.a();
    }

    @Override
    public boolean C_() {
        return false;
    }

    @Override
    public void c() {
        this.g();
    }

    private void g() {
        Iterable<BlockPosition> iterable = BlockPosition.b(MathHelper.floor(this.a.locX() - 1.0D), MathHelper.floor(this.a.locY()), MathHelper.floor(this.a.locZ() - 1.0D), MathHelper.floor(this.a.locX() + 1.0D), MathHelper.floor(this.a.locY() + 8.0D), MathHelper.floor(this.a.locZ() + 1.0D));
        BlockPosition blockposition = null;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition1 = (BlockPosition) iterator.next();

            if (this.a(this.a.world, blockposition1)) {
                blockposition = blockposition1;
                break;
            }
        }

        if (blockposition == null) {
            blockposition = new BlockPosition(this.a.locX(), this.a.locY() + 8.0D, this.a.locZ());
        }

        this.a.getNavigation().a((double) blockposition.getX(), (double) (blockposition.getY() + 1), (double) blockposition.getZ(), 1.0D);
    }

    @Override
    public void e() {
        this.g();
        this.a.a(0.02F, new Vec3D((double) this.a.aR, (double) this.a.aS, (double) this.a.aT));
        this.a.move(EnumMoveType.SELF, this.a.getMot());
    }

    private boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition);

        return (iworldreader.getFluid(blockposition).isEmpty() || iblockdata.a(Blocks.BUBBLE_COLUMN)) && iblockdata.a((IBlockAccess) iworldreader, blockposition, PathMode.LAND);
    }
}
