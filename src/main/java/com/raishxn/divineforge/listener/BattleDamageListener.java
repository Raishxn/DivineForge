package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.status.Burn;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.raishxn.divineforge.registry.DivineAbility;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.util.DivineHelper;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Random;

public class BattleDamageListener {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public void onAttackDamage(AttackEvent.Damage event) {
        PixelmonEntity attacker = event.user.getEntity();
        PixelmonEntity defender = event.target.getEntity();
        Attack attack = event.getAttack();

        if (attacker == null || defender == null || attack == null) return;

        double damageMultiplier = 1.0;
        AttackCategory category = attack.getAttackCategory();
        String attackType = attack.getActualType().unwrapKey().get().location().getPath().toLowerCase();

        String moveId = attack.getMove().getAttackName().toLowerCase();

        // CORREÇÃO 1: Usar attack.isSoundBased() em vez de attack.getMove().isSoundBased()
        boolean isSoundMove = moveId.contains("sound") || moveId.contains("voice") || moveId.contains("roar") || attack.isSoundBased();
        boolean isBeamMove = moveId.contains("beam") || moveId.contains("laser") || moveId.contains("cannon");
        boolean isBitingMove = moveId.contains("bite") || moveId.contains("fang") || moveId.contains("crunch");
        boolean isBombMove = moveId.contains("bomb") || moveId.contains("blast") || moveId.contains("explosion");

        // =============================================================================
        // --- 1. ATACANTE (Offensive) ---
        // =============================================================================
        DivineType attackerType = DivineHelper.getDivineType(attacker.getPokemon());
        if (attackerType != null) {
            if (category == AttackCategory.PHYSICAL) {
                damageMultiplier *= attackerType.getStatModifier("attack");
            } else if (category == AttackCategory.SPECIAL) {
                damageMultiplier *= attackerType.getStatModifier("sp_atk");
            }

            // BOREAL INSTINCT
            if (attackerType.hasAbility(DivineAbility.BOREAL_INSTINCT)) {
                // CORREÇÃO 2: Usar .status.type.name() em vez de .status.getType()
                String statusName = defender.getPokemon().getStatus().type.name().toUpperCase();
                boolean isTargetFrozen = statusName.contains("FREEZE") || statusName.contains("FROST");
                if (isTargetFrozen && isBitingMove) {
                    damageMultiplier *= 1.5;
                }
            }
            // BOLD AS BRASS (Ofensivo)
            if (attackerType.hasAbility(DivineAbility.BOLD_AS_BRASS) && isSoundMove) {
                damageMultiplier *= 1.5;
            }
            // SOLAR FLARE
            if (attackerType.hasAbility(DivineAbility.SOLAR_FLARE) && attackType.equals("fire")) {
                damageMultiplier *= 1.5;
            }
            // IGNATE
            if (attackerType.hasAbility(DivineAbility.IGNATE) && attackType.equals("fire")) {
                if (RANDOM.nextInt(100) < 30) {
                    // CORREÇÃO 3: Usar defender.getPokemon().setStatus()
                    defender.getPokemon().setStatus(new Burn());
                }
            }
        }

        // =============================================================================
        // --- 2. DEFENSOR (Defensive) ---
        // =============================================================================
        DivineType defenderType = DivineHelper.getDivineType(defender.getPokemon());
        if (defenderType != null) {
            damageMultiplier *= defenderType.getEffectivenessFrom(attackType);

            if (category == AttackCategory.PHYSICAL) {
                damageMultiplier /= defenderType.getStatModifier("defense");
            } else if (category == AttackCategory.SPECIAL) {
                damageMultiplier /= defenderType.getStatModifier("sp_def");
            }

            // BOLD AS BRASS (Defensivo)
            if (defenderType.hasAbility(DivineAbility.BOLD_AS_BRASS)) {
                if (isSoundMove) damageMultiplier *= 1.5;
                if (attackType.equals("fire")) damageMultiplier *= 0.5;
            }
            // COVER ME IN DEBRIS
            if (defenderType.hasAbility(DivineAbility.COVER_ME_IN_DEBRIS) && attackType.equals("fire")) {
                damageMultiplier = 0.0;
                // CORREÇÃO 4: Remover event.setCanceled(true)
            }
            // ENERGY SHIELD
            if (defenderType.hasAbility(DivineAbility.ENERGY_SHIELD) && isBeamMove) {
                damageMultiplier = 0.0;
                // CORREÇÃO 5: Remover event.setCanceled(true)
            }
            // GEMSTONE
            if (defenderType.hasAbility(DivineAbility.GEMSTONE) && isBombMove) {
                damageMultiplier = 0.0;
                // CORREÇÃO 6: Remover event.setCanceled(true)
            }
            // COPPER STATE
            if (defenderType.hasAbility(DivineAbility.COPPER_STATE)) {
                if (attackType.equals("water")) damageMultiplier *= 2.0;
                if (attackType.equals("electric") && damageMultiplier == 0.0) damageMultiplier = 1.0;
            }
            // SOLAR PROMINENCE
            if (defenderType.hasAbility(DivineAbility.SOLAR_PROMINENCE)) {
                damageMultiplier *= 0.7;
            }
        }

        // --- 3. APLICAR DANO FINAL ---
        // CORREÇÃO 7 & 8: Remover !event.isCanceled()
        if (damageMultiplier != 1.0) {
            double newDamage = event.damage * damageMultiplier;
            if (newDamage < 1 && event.damage > 0 && damageMultiplier > 0) {
                newDamage = 1;
            }
            event.damage = newDamage;
        }
    }
}