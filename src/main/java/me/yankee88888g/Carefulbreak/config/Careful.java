package me.yankee88888g.Carefulbreak.config;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static net.minecraft.block.Block.getDroppedStacks;

public class Careful {

    public static boolean isSneaking = false;

    public static void dropStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {

        // Obtain the drops to process

        List<ItemStack> drops = getDroppedStacks(state, world, pos, blockEntity, entity, stack);

        if (entity instanceof PlayerEntity && entity.isSneaking()) {

            boolean isSneaking = true;

        }
    }
}
