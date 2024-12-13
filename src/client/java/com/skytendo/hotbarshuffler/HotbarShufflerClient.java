package com.skytendo.hotbarshuffler;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HotbarShufflerClient implements ClientModInitializer {

	public static final String MOD_ID = "hotbarshuffler";
	public static boolean isActive;

	private static KeyBinding configKeyBinding;
	private static KeyBinding activateKeyBinding;

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MOD_ID, HotbarShufflerConfig.class);

		configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.hotbarshuffler.config",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_N,
				"category.hotbarshuffler"
		));

		activateKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.hotbarshuffler.activate",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F7,
				"category.hotbarshuffler"
		));

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			isActive = false;
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (configKeyBinding.wasPressed()) {
				client.setScreen(MidnightConfig.getScreen(client.currentScreen, MOD_ID));
			}
			while (activateKeyBinding.wasPressed()) {
				if (client.player != null) {
					isActive = !isActive;
					if (isActive) {
						client.player.sendMessage(Text.translatable("hotbarshuffler.message.activated"), true);
					} else {
						client.player.sendMessage(Text.translatable("hotbarshuffler.message.deactivated"), true);
					}
				}
			}
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClient())
				return ActionResult.PASS;
			if (player == null)
				return ActionResult.PASS;
			if (!isActive)
				return ActionResult.PASS;

			List<Integer> slots = getSlots();
			if (!getSlots().isEmpty()) {
				MinecraftClient.getInstance().player.getInventory().setSelectedSlot(slots.get(new Random().nextInt(slots.size())));
			}
			return ActionResult.SUCCESS;
		});
	}

	public static List<Integer> getSlots() {
		List<Integer> list = new ArrayList<>();
		if (HotbarShufflerConfig.slot1) list.add(0);
		if (HotbarShufflerConfig.slot2) list.add(1);
		if (HotbarShufflerConfig.slot3) list.add(2);
		if (HotbarShufflerConfig.slot4) list.add(3);
		if (HotbarShufflerConfig.slot5) list.add(4);
		if (HotbarShufflerConfig.slot6) list.add(5);
		if (HotbarShufflerConfig.slot7) list.add(6);
		if (HotbarShufflerConfig.slot8) list.add(7);
		if (HotbarShufflerConfig.slot9) list.add(8);
		return list;
	}
}