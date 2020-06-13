package me.yankee88888g.Carefulbreak.mixin.shears;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PumpkinBlock.class)
public class PumpkinBlockMixin {
    @Redirect(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    public boolean onUse(World invokeWorld, Entity invokeEntity, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (CarefulDropsConfig.overridePumpkinDrops && ((ServerPlayerEntity) player).inventory.insertStack(((ItemEntity) invokeEntity).getStack())) return true;

        return invokeWorld.spawnEntity(invokeEntity);
    }
}
