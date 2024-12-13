package com.skytendo.hotbarshuffler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class HotbarShufflerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

		});
	}
}