package com.itelcan3.cloverclient.mixin;

import com.itelcan3.cloverclient.client.AutoBlock;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Inject(
        method = {"getItemUseAction", "func_77975_n"}, 
        at = @At("HEAD"), 
        cancellable = true,
        remap = false
    )
    private void overrideItemUseAction(CallbackInfoReturnable<EnumAction> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (AutoBlock.enabled && stack != null && stack.getItem() instanceof ItemSword && AutoBlock.shouldBlock()) {
            cir.setReturnValue(EnumAction.BLOCK);
        }
    }

    @Inject(
        method = {"getMaxItemUseDuration", "func_77988_m"},
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void overrideMaxItemUseDuration(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (AutoBlock.enabled && stack != null && stack.getItem() instanceof ItemSword && AutoBlock.shouldBlock()) {
            cir.setReturnValue(72000);
        }
    }
}