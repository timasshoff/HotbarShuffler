package com.skytendo.hotbarshuffler.mixin.client;

import com.skytendo.hotbarshuffler.HotbarShufflerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderHotbar(" +
            "Lnet/minecraft/client/gui/DrawContext;" +
            "Lnet/minecraft/client/render/RenderTickCounter;" +
            ")V", at = @At("TAIL"))
    private void renderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PlayerEntity playerEntity = MinecraftClient.getInstance().player;
        if (playerEntity != null) {
            int i = context.getScaledWindowWidth() / 2;
            for (int m = 0; m < 9; m++) {
                int n = i - 90 + m * 20 + 2;
                int o = context.getScaledWindowHeight() - 16 - 3;

                if (HotbarShufflerClient.isActive) {
                    if (HotbarShufflerClient.getSlots().contains(m)) {
                        context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of(HotbarShufflerClient.MOD_ID, "hud/slot_background"), n, o, 16, 16);
                    }
                }
            }
        }
    }
}
