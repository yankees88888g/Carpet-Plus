
package me.yankee88888g.Carefulbreak.mixin;

import com.google.common.collect.ImmutableList;

import me.yankee88888g.Carefulbreak.command.Read;
import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

import static net.minecraft.block.Block.getDroppedStacks;

@Mixin(Block.class)
public class BlockBreakMixin {


    // public static boolean isSneaking = false;
    @Redirect(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"
            ),
            method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V")
    private static List<ItemStack> dropStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        // Obtain the drops to process
        List<ItemStack> drops = getDroppedStacks(state, world, pos, blockEntity, entity, stack);

        // Only place in the inventory if the entity is a player and block overrides are enabled
        if(CarefulDropsConfig.isOverrideKeyBind && CarefulDropsConfig.overrideBlockDrops) {
            if (entity instanceof PlayerEntity && entity.isSneaking()) {

                // boolean isSneaking = true;

                //            // Cast as a player for easy access.  We could continually
                // cast below, but this is better
                ServerPlayerEntity player = (ServerPlayerEntity) entity;

                // This loops through the items and runs code on each item
                // Note that because we are modifying the list while looping,
                // we make a copy of it here.
                ImmutableList.copyOf(drops).forEach((itemStack) -> {
                    // Attempt to add to the player's inventory
                    if (player.inventory.insertStack(itemStack)) {
                        // Getting here means adding was successful
                        // and we can remove the item from the drops
                        drops.remove(itemStack);
                    }
                });
            }

            // Whatever is left in the list was skipped for some reason
            // or another.  Returning will allow Minecraft to process
            // like normal

            return drops;
        }else{
            if(CarefulDropsConfig.overrideBlockDrops){
                ServerPlayerEntity player = (ServerPlayerEntity) entity;

                // This loops through the items and runs code on each item
                // Note that because we are modifying the list while looping,
                // we make a copy of it here.
                ImmutableList.copyOf(drops).forEach((itemStack) -> {
                    // Attempt to add to the player's inventory
                    if (player.inventory.insertStack(itemStack)) {
                        // Getting here means adding was successful
                        // and we can remove the item from the drops
                        drops.remove(itemStack);

                    }
                });
            }

        }
        return drops;
    }

}