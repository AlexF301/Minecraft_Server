package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget {

    private final EntityIronGolem a;
    private EntityLiving b;
    private final PathfinderTargetCondition c = (new PathfinderTargetCondition()).a(64.0D);

    public PathfinderGoalDefendVillage(EntityIronGolem entityirongolem) {
        super(entityirongolem, false, true);
        this.a = entityirongolem;
        this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
    }

    @Override
    public boolean a() {
        AxisAlignedBB axisalignedbb = this.a.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
        List<EntityLiving> list = this.a.world.a(EntityVillager.class, this.c, this.a, axisalignedbb);
        List<EntityHuman> list1 = this.a.world.a(this.c, (EntityLiving) this.a, axisalignedbb);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityLiving entityliving = (EntityLiving) iterator.next();
            EntityVillager entityvillager = (EntityVillager) entityliving;
            Iterator iterator1 = list1.iterator();

            while (iterator1.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator1.next();
                int i = entityvillager.g(entityhuman);

                if (i <= -100) {
                    this.b = entityhuman;
                }
            }
        }

        if (this.b == null) {
            return false;
        } else if (this.b instanceof EntityHuman && (this.b.isSpectator() || ((EntityHuman) this.b).isCreative())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void c() {
        this.a.setGoalTarget(this.b, org.bukkit.event.entity.EntityTargetEvent.TargetReason.DEFEND_VILLAGE, true); // CraftBukkit - reason
        super.c();
    }
}
