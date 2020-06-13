package me.yankee88888g.Carefulbreak.mixin;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HopperBlockEntity.class)


    public class HopperBlockEntityMixin {


        @ModifyConstant(

            method = "insertAndExtract",
            constant = @Constant(intValue = 8)
    )

    private int modify(int original) {
            if (CarefulDropsConfig.isFastHopper) {
                return 0;
            }else{
                return 8;
            }
        }
    @ModifyConstant(
            method = "transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;",
            constant = @Constant(intValue = 8)
    )
    private static int modifyTransfer(int original) {
        return 0;
    }


}
