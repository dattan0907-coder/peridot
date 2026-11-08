package com.peridotscout.registry;

import com.peridotscout.PeridotScoutMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, PeridotScoutMod.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> AMBIENT = register("entity.peridot_scout.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HURT = register("entity.peridot_scout.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEATH = register("entity.peridot_scout.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> STEP = register("entity.peridot_scout.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROAR = register("entity.peridot_scout.roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCAN = register("entity.peridot_scout.scan");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(PeridotScoutMod.MODID, name)));
    }
}
