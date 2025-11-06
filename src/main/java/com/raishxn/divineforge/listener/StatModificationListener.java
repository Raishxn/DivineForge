package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class StatModificationListener {

    private static final ResourceLocation HP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("divineforge", "hp_modifier");
    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("divineforge", "speed_modifier");

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof PixelmonEntity pixelmonEntity)) {
            return;
        }

        if (pixelmonEntity.level().isClientSide) {
            return;
        }

        Pokemon pokemon = pixelmonEntity.getPokemon();
        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");

        if (customTypeId == null || customTypeId.isEmpty()) {
            return;
        }

        CustomType customType = CustomTypeLoader.getType(customTypeId);
        if (customType == null || customType.stat_modifiers == null) {
            return;
        }

        if (customType.stat_modifiers.containsKey("hp")) {
            double hpMultiplier = customType.stat_modifiers.get("hp") - 1.0;

            if (hpMultiplier != 0) {
                AttributeInstance maxHealthAttr = pixelmonEntity.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealthAttr != null) {
                    maxHealthAttr.removeModifier(HP_MODIFIER_ID);

                    maxHealthAttr.addPermanentModifier(new AttributeModifier(
                            HP_MODIFIER_ID,
                            hpMultiplier,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ));

                    if (pixelmonEntity.getHealth() > maxHealthAttr.getValue()) {
                        pixelmonEntity.setHealth((float) maxHealthAttr.getValue());
                    }
                }
            }
        }

        // --- 2. Modificador de MOVEMENT SPEED ---
        if (customType.stat_modifiers.containsKey("speed")) {
            double speedMultiplier = customType.stat_modifiers.get("speed") - 1.0;

            if (speedMultiplier != 0) {
                AttributeInstance speedAttr = pixelmonEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speedAttr != null) {
                    speedAttr.removeModifier(SPEED_MODIFIER_ID);
                    speedAttr.addPermanentModifier(new AttributeModifier(
                            SPEED_MODIFIER_ID,
                            speedMultiplier,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ));
                }
            }
        }
    }
}