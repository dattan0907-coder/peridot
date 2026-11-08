package com.peridotscout;

import com.peridotscout.entity.PeridotScoutEntity;
import com.peridotscout.registry.ModEntities;
import com.peridotscout.registry.ModItems;
import com.peridotscout.registry.ModSounds;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(PeridotScoutMod.MODID)
public class PeridotScoutMod {
    public static final String MODID = "peridot_scout";

    public PeridotScoutMod(IEventBus modEventBus) {
        ModEntities.ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(this::registerAttributes);
        modEventBus.addListener(this::buildCreativeTabs);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PERIDOT_SCOUT.get(), PeridotScoutEntity.createAttributes().build());
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.PERIDOT_SCOUT_SPAWN_EGG.get());
        }
    }
}
