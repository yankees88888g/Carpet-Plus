package me.yankee88888g.Carefulbreak.mixin.shears;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MooshroomEntity.class)
public class MooshroomEntityMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), method = "interactMob")
    public boolean interactMob(World world, Entity entity, PlayerEntity player, Hand hand) {
        if (CarefulDropsConfig.overrideMooshroomDrops && (entity instanceof ItemEntity)) {
            if (((ServerPlayerEntity) player).inventory.insertStack(((ItemEntity) entity).getStack())) return true;
        }

        return world.spawnEntity(entity);
    }
}
