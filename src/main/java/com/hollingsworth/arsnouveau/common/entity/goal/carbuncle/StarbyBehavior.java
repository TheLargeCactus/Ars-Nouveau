package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.common.block.SummonBed;
import com.hollingsworth.arsnouveau.common.entity.ChangeableBehavior;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class StarbyBehavior extends ChangeableBehavior {
    public Starbuncle starbuncle;

    public StarbyBehavior(Starbuncle entity, CompoundTag tag) {
        super(entity, tag);
        this.starbuncle = entity;
        goals.add(new WrappedGoal(4, new GoToBedGoal(starbuncle, this)));
        goals.add(new WrappedGoal(8, new LookAtPlayerGoal(starbuncle, Player.class, 3.0F, 0.01F)));
        goals.add(new WrappedGoal(8, new NonHoggingLook(starbuncle, Mob.class, 3.0F, 0.01f)));
        goals.add(new WrappedGoal(1, new OpenDoorGoal(starbuncle, true)));
    }

    public boolean canGoToBed(){
        return true;
    }

    @Override
    public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
        super.onFinishedConnectionFirst(storedPos, storedEntity, playerEntity);
        if (storedPos != null && playerEntity.level.getBlockState(storedPos).getBlock() instanceof SummonBed) {
            PortUtil.sendMessage(playerEntity, Component.translatable("ars_nouveau.starbuncle.set_bed"));
            starbuncle.data.bedPos = storedPos.immutable();
        }
    }

    @Override
    protected ResourceLocation getRegistryName() {
        return new ResourceLocation(ArsNouveau.MODID, "starby");
    }
}
