package org.dawnoftime.ancientstructures.advancement;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.dawnoftime.ancientstructures.Constants;

import java.util.Map;
import java.util.Optional;

/**
 * Shared logic (common to Forge and Fabric) for checking whether a player
 * is standing inside one of the mod's structures and firing the custom trigger.
 *
 * <p>Uses {@code StructureManager#getAllStructuresAt} which reads live chunk
 * STRUCTURE_REFERENCES data. A structure appears in the result when the player's
 * chunk intersects the structure's bounding box — giving a natural discovery radius
 * without requiring a full holder lookup (which is broken in Forge 1.20.1).
 *
 * <p>Call {@link #checkAndTrigger(ServerPlayer)} from a server-side player tick
 * event (throttled to ~1 check per second).
 */
public class CommonStructureChecker {

    private CommonStructureChecker() {}

    public static void checkAndTrigger(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
        StructureManager structureManager = level.structureManager();

        // Returns all structures whose bounding box intersects the player's current chunk.
        Map<Structure, ?> structuresAt = structureManager.getAllStructuresAt(player.blockPosition());

        for (Structure structure : structuresAt.keySet()) {
            Optional<ResourceKey<Structure>> keyOpt = registry.getResourceKey(structure);
            if (keyOpt.isEmpty()) continue;

            ResourceKey<Structure> key = keyOpt.get();
            // Only fire for this mod's structures.
            if (!key.location().getNamespace().equals(Constants.MOD_ID)) continue;

            StructureDiscoveryTrigger.INSTANCE.trigger(player, key.location());
        }
    }
}
