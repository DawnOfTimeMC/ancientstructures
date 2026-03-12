package org.dawnoftime.ancientstructures;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.CriteriaTriggers;
import org.dawnoftime.ancientstructures.advancement.CommonStructureChecker;
import org.dawnoftime.ancientstructures.advancement.StructureDiscoveryTrigger;

public class AncientStructures implements ModInitializer {

    private static final int CHECK_INTERVAL = 20; // ticks (= 1 second)

    @Override
    public void onInitialize() {
        CriteriaTriggers.register(StructureDiscoveryTrigger.INSTANCE);

        // Check all online players once per second for structure presence.
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) {
                if (player.tickCount % CHECK_INTERVAL == 0) {
                    CommonStructureChecker.checkAndTrigger(player);
                }
            }
        });
    }
}
