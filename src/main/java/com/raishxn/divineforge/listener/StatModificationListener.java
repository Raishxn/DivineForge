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

    // Identificadores únicos para os nossos modificadores
    // NOTA: Em versões recentes do NeoForge (1.20.5+), usa-se ResourceLocation em vez de UUIDs para modificadores.
    private static final ResourceLocation HP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("divineforge", "hp_modifier");
    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("divineforge", "speed_modifier");

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        // Verifica se a entidade é um Pixelmon
        if (!(event.getEntity() instanceof PixelmonEntity pixelmonEntity)) {
            return;
        }

        // Verifica se estamos no lado do servidor (importante para atributos)
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

        // --- 1. Modificador de MAX HEALTH (HP) ---
        if (customType.stat_modifiers.containsKey("hp")) {
            // O valor no YAML é 1.20 (para +20%), então o modificador deve ser 0.20
            double hpMultiplier = customType.stat_modifiers.get("hp") - 1.0;

            if (hpMultiplier != 0) {
                AttributeInstance maxHealthAttr = pixelmonEntity.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealthAttr != null) {
                    // Remove o modificador antigo para não acumular infinitamente cada vez que entra no mundo
                    maxHealthAttr.removeModifier(HP_MODIFIER_ID);

                    // Adiciona o novo modificador
                    maxHealthAttr.addPermanentModifier(new AttributeModifier(
                            HP_MODIFIER_ID,
                            hpMultiplier,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ));

                    // Se a vida atual for superior à nova máxima (no caso de remover um tipo), ajusta.
                    // Se ganhou vida (buff), opcionalmente cura a diferença ou deixa como está.
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