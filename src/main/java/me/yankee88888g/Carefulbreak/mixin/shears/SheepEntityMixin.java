package me.yankee88888g.Carefulbreak.mixin.shears;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends Entity {
    @Shadow public abstract void setSheared(boolean bl);

    @Shadow @Final private static Map<DyeColor, ItemConvertible> DROPS;

    @Shadow public abstract DyeColor getColor();

    public SheepEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;dropItems()V"), method = "interactMob")
    public void interactMob(SheepEntity sheep, PlayerEntity player, Hand hand) {
        if (!CarefulDropsConfig.overrideSheepDrops || !(player instanceof ServerPlayerEntity)) {
            sheep.dropItems();
            return;
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        setSheared(true);
        int i = 1 + random.nextInt(3);

        for(int j = 0; j < i; ++j) {
            ItemConvertible drop = DROPS.get(getColor());

            if (!serverPlayer.inventory.insertStack(new ItemStack(drop))) {
                ItemEntity itemEntity = this.dropItem(drop, 1);

                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add(((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                }
            }

        }

    }
}
