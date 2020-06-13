package me.yankee88888g.Carefulbreak.mixin;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class EntityDropMixin {

    @Redirect(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/loot/LootTable;dropLimited(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"
        ),
        method = "dropLoot"
    )
    protected void dropLoot(LootTable lootTable, LootContext context, Consumer<ItemStack> dropItemConsumer, DamageSource source, boolean causedByPlayer) {
        // Filter out scenarios that we cannot (or should not) take over the process
        if (!CarefulDropsConfig.overrideEntityDrops || !causedByPlayer || source == null || source.getAttacker() == null || !(source.getAttacker() instanceof ServerPlayerEntity)) {
            // In these case, let the processing happen as normal
            lootTable.dropLimited(context, dropItemConsumer);
        } else {
            // Cast as a player for easy access.  We could continually
            // cast below, but this is better
            ServerPlayerEntity player = (ServerPlayerEntity) source.getAttacker();

            // Obtain the loot and pass each to our consumer instead
            lootTable.dropLimited(context, stack -> {
                // Attempt to add to the player's inventory
                if (!player.inventory.insertStack(stack)) {
                    // Getting here means adding was unsuccessful
                    // and we should pass to the normal consumer
                    dropItemConsumer.accept(stack);
                }
            });
        }

    }
}
