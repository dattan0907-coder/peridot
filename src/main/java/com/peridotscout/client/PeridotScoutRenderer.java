package com.peridotscout.client;

import com.peridotscout.PeridotScoutMod;
import com.peridotscout.entity.PeridotScoutEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Loads (modid = peridot_scout, asset = peridot_scout):
 *   assets/peridot_scout/geo/entity/peridot_scout.geo.json
 *   assets/peridot_scout/animations/entity/peridot_scout.animation.json
 *   assets/peridot_scout/textures/entity/peridot_scout.png
 */
public class PeridotScoutRenderer extends GeoEntityRenderer<PeridotScoutEntity> {
    public PeridotScoutRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(
                ResourceLocation.fromNamespaceAndPath(PeridotScoutMod.MODID, "peridot_scout"), true));
    }
}
