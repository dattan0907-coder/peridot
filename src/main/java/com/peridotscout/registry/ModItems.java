package com.peridotscout.registry;

import com.peridotscout.PeridotScoutMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PeridotScoutMod.MODID);

    public static final DeferredItem<DeferredSpawnEggItem> PERIDOT_SCOUT_SPAWN_EGG =
            ITEMS.register("peridot_scout_spawn_egg", () ->
                    new DeferredSpawnEggItem(ModEntities.PERIDOT_SCOUT, 0x8CC63F, 0x2F4F1F, new Item.Properties()));
}
