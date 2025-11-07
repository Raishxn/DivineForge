package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.util.DivineHelper;
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

        // --- LÓGICA ATUALIZADA ---
        DivineType customType = DivineHelper.getDivineType(pokemon);
        if (customType == null) {
            // Se não tiver tipo, remove modificadores antigos (limpeza)
            removeModifier(pixelmonEntity, Attributes.MAX_HEALTH, HP_MODIFIER_ID);
            removeModifier(pixelmonEntity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID);
            return;
        }

        // --- 1. Modificador de HP ---
        // Obtém o modificador do Enum (ex: 1.25). O padrão é 1.0
        double hpModifier = customType.getStatModifier("hp");
        if (hpModifier != 1.0) {
            // Converte para o formato do AttributeModifier (ex: 1.25 -> 0.25)
            double hpMultiplier = hpModifier - 1.0;

            AttributeInstance maxHealthAttr = pixelmonEntity.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr != null) {
                // Remove o modificador antigo antes de aplicar o novo
                maxHealthAttr.removeModifier(HP_MODIFIER_ID);
                maxHealthAttr.addPermanentModifier(new AttributeModifier(
                        HP_MODIFIER_ID,
                        hpMultiplier,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
                // Corrige a vida atual se ela for maior que o novo máximo
                if (pixelmonEntity.getHealth() > maxHealthAttr.getValue()) {
                    pixelmonEntity.setHealth((float) maxHealthAttr.getValue());
                }
            }
        } else {
            // Se o tipo não tiver modificador de HP, remove qualquer um que exista
            removeModifier(pixelmonEntity, Attributes.MAX_HEALTH, HP_MODIFIER_ID);
        }

        // --- 2. Modificador de MOVEMENT SPEED ---
        double speedModifier = customType.getStatModifier("speed");
        if (speedModifier != 1.0) {
            double speedMultiplier = speedModifier - 1.0;

            AttributeInstance speedAttr = pixelmonEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
                speedAttr.removeModifier(SPEED_MODIFIER_ID);
                speedAttr.addPermanentModifier(new AttributeModifier(
                        SPEED_MODIFIER_ID,
                        speedMultiplier,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
            }
        } else {
            removeModifier(pixelmonEntity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID);
        }
    }

    /**
     * Método auxiliar para remover um modificador de atributo com segurança.
     */
    private void removeModifier(PixelmonEntity entity, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, ResourceLocation id) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}