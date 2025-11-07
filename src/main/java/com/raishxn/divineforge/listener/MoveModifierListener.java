package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type; // Usamos a classe Type da sua API
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.raishxn.divineforge.registry.DivineAbility;
import com.raishxn.divineforge.util.DivineHelper;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;

public class MoveModifierListener {

    // Este evento é disparado ANTES do cálculo de dano
    @SubscribeEvent
    public void onAttackUse(AttackEvent.Use event) {
        // PixelmonWrapper (event.user) estende Pokemon
        Pokemon user = event.user;
        Attack attack = event.getAttack();

        if (user == null || attack == null) return;

        // Verificamos apenas se o tipo original do movimento é NORMAL
        if (attack.getActualType().is(Type.NORMAL)) {

            // --- HABILIDADES DE CONVERSÃO DE TIPO ---
            //
            if (DivineHelper.hasAbility(user, DivineAbility.DIGITIZE)) {
                // Tenta encontrar "pixelmon:digital" ou "divineforge:digital"
                setType(attack, "digital");
            }
            //
            else if (DivineHelper.hasAbility(user, DivineAbility.FOSSILIZE)) {
                attack.overrideType(Type.ROCK);
            }
            //
            else if (DivineHelper.hasAbility(user, DivineAbility.ATOMIZATE)) {
                setType(attack, "nuclear");
            }
            //
            else if (DivineHelper.hasAbility(user, DivineAbility.ENERGIZATE)) {
                attack.overrideType(Type.ELECTRIC);
            }
            //
            else if (DivineHelper.hasAbility(user, DivineAbility.EQUALIZE)) {
                setType(attack, "sound");
            }
            //
            else if (DivineHelper.hasAbility(user, DivineAbility.SCALATE)) {
                attack.overrideType(Type.DRAGON);
            }
        }
    }

    /**
     * Método auxiliar para definir um tipo customizado que pode não estar nas constantes.
     * Tenta "divineforge:typename" e "pixelmon:typename".
     */
    private void setType(Attack attack, String typeName) {
        // Tenta buscar o tipo (ex: "digital", "nuclear", "sound")
        // O método Type.parseOrNull funciona com "pixelmon:nome" ou apenas "nome"
        try {
            ResourceKey<Type> newTypeKey = Type.parseOrNull(typeName);
            if (newTypeKey != null) {
                attack.overrideType(newTypeKey);
            }
        } catch (Exception e) {
            // Ignora se o tipo não existir
        }
    }
}