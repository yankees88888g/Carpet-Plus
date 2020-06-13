package me.yankee88888g.Carefulbreak.mixin.shears;

import me.yankee88888g.Carefulbreak.config.Careful;
import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.block.Block.dropStack;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin {
    @Shadow
    public static void dropHoneycomb(World world, BlockPos pos) { }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BeehiveBlock;dropHoneycomb(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"), method = "onUse")
    private void onUse(World beehiveWorld, BlockPos beehiveBlockPos, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if(Careful.isSneaking && CarefulDropsConfig.overrideBeehiveDrops){

        if (CarefulDropsConfig.overrideBeehiveDrops || !(player instanceof ServerPlayerEntity)) {
            dropHoneycomb(beehiveWorld, beehiveBlockPos);
            return;
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        ItemStack drop = new ItemStack(Items.HONEYCOMB, 3);

        if (!serverPlayer.inventory.insertStack(drop)) {
            dropStack(world, pos, drop);
            }
        }else{
            if (CarefulDropsConfig.overrideBeehiveDrops || !(player instanceof ServerPlayerEntity)) {
                dropHoneycomb(beehiveWorld, beehiveBlockPos);
                return;
            }

            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            ItemStack drop = new ItemStack(Items.HONEYCOMB, 3);

            if (!serverPlayer.inventory.insertStack(drop)) {
                dropStack(world, pos, drop);
            }
        }
    }

}
