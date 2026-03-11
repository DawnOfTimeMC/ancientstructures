package org.dawnoftime.ancientstructures.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

/**
 * Custom criterion trigger that fires when a player physically enters
 * one of the mod's structures. This bypasses the broken LocationPredicate
 * holder resolution in Forge 1.20.1 for dynamic-registry structures.
 *
 * <p>Advancement JSON usage:
 * <pre>{@code
 * "my_criterion": {
 *   "trigger": "ancientstructures:structure_discovery",
 *   "conditions": {
 *     "structure": "ancientstructures:german_farm"
 *   }
 * }
 * }</pre>
 */
public class StructureDiscoveryTrigger extends SimpleCriterionTrigger<StructureDiscoveryTrigger.TriggerInstance> {

    public static final StructureDiscoveryTrigger INSTANCE = new StructureDiscoveryTrigger();
    public static final ResourceLocation ID = new ResourceLocation("ancientstructures", "structure_discovery");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        ResourceLocation structure = new ResourceLocation(GsonHelper.getAsString(json, "structure"));
        return new TriggerInstance(predicate, structure);
    }

    /**
     * Call this from an event handler whenever a player is detected inside a structure.
     *
     * @param player      the server-side player
     * @param structureId the namespaced ID of the structure (e.g. "ancientstructures:german_farm")
     */
    public void trigger(ServerPlayer player, ResourceLocation structureId) {
        this.trigger(player, instance -> instance.matches(structureId));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final ResourceLocation structure;

        public TriggerInstance(ContextAwarePredicate predicate, ResourceLocation structure) {
            super(ID, predicate);
            this.structure = structure;
        }

        public boolean matches(ResourceLocation structureId) {
            return this.structure.equals(structureId);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("structure", this.structure.toString());
            return json;
        }
    }
}
