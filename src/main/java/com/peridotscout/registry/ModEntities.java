package com.peridotscout.registry;

import com.peridotscout.PeridotScoutMod;
import com.peridotscout.entity.PeridotScoutEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, PeridotScoutMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<PeridotScoutEntity>> PERIDOT_SCOUT =
            ENTITIES.register("peridot_scout", () ->
                    EntityType.Builder.of(PeridotScoutEntity::new, MobCategory.MONSTER)
                            .sized(1.6F, 3.0F)
                            .clientTrackingRange(10)
                            .build("peridot_scout"));
}
