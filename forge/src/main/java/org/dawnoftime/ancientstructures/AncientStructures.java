package org.dawnoftime.ancientstructures;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import org.dawnoftime.ancientstructures.advancement.StructureDiscoveryTrigger;

@Mod(Constants.MOD_ID)
public class AncientStructures {

    public AncientStructures() {
        CriteriaTriggers.register(StructureDiscoveryTrigger.INSTANCE);
    }
}
