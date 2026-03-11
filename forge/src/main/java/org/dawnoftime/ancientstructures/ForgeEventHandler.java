package org.dawnoftime.ancientstructures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftime.ancientstructures.advancement.CommonStructureChecker;

/**
 * Forge-specific event handler.
 * Registered automatically via {@link Mod.EventBusSubscriber}.
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ForgeEventHandler {

    private static final int CHECK_INTERVAL = 20; // ticks (= 1 second)

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Only run at the end of the tick, on the server side, once per second.
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;
        if (event.player.tickCount % CHECK_INTERVAL != 0) return;

        CommonStructureChecker.checkAndTrigger((ServerPlayer) event.player);
    }
}
