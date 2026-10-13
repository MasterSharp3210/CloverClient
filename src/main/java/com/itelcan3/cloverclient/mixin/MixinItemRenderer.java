package com.itelcan3.cloverclient.mixin;

import com.itelcan3.cloverclient.client.AutoBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Shadow @Final private Minecraft mc;

    @Redirect(
        method = "renderItemInFirstPerson",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;getItemUseAction()Lnet/minecraft/item/EnumAction;"
        )
    )
    private EnumAction redirectGetItemUseAction(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemSword && AutoBlock.shouldBlock()) {
            return EnumAction.BLOCK;
        }
        return stack != null ? stack.getItemUseAction() : EnumAction.NONE;
    }


    @Redirect(
        method = "renderItemInFirstPerson",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUsingItem()Z"
        )
    )
    private boolean redirectIsUsingItem(AbstractClientPlayer player) {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemSword && AutoBlock.shouldBlock()) {
            return true;
        }
        return player.isUsingItem();
    }


    @Redirect(
        method = "renderItemInFirstPerson",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I"
        )
    )
    private int redirectGetItemInUseCount(AbstractClientPlayer player) {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemSword && AutoBlock.shouldBlock()) {
            return 72000; // Valore arbitrario alto per simulare un blocco continuo
        }
        return player.getItemInUseCount();
    }
}
