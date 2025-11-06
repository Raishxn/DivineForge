package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.raishxn.divineforge.data.CustomAbility; // Import necessário para as habilidades
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;

public class BattleDamageListener {

    @SubscribeEvent
    public void onAttackDamage(AttackEvent.Damage event) {
        PixelmonEntity attacker = event.user.getEntity();
        PixelmonEntity defender = event.target.getEntity();
        Attack attack = event.getAttack();

        if (attacker == null || defender == null || attack == null) return;

        double damageMultiplier = 1.0;

        AttackCategory category = attack.getAttackCategory();

        String attackerTypeId = attacker.getPokemon().getPersistentData().getString("divineforge:custom_type");
        if (attackerTypeId != null && !attackerTypeId.isEmpty()) {
            CustomType attackerType = CustomTypeLoader.getType(attackerTypeId);
            if (attackerType != null && attackerType.stat_modifiers != null) {
                if (category == AttackCategory.PHYSICAL) {
                    damageMultiplier *= attackerType.stat_modifiers.getOrDefault("attack", 1.0);
                } else if (category == AttackCategory.SPECIAL) {
                    damageMultiplier *= attackerType.stat_modifiers.getOrDefault("sp_atk", 1.0);
                }
            }
        }

        String defenderTypeId = defender.getPokemon().getPersistentData().getString("divineforge:custom_type");
        if (defenderTypeId != null && !defenderTypeId.isEmpty()) {
            CustomType defenderType = CustomTypeLoader.getType(defenderTypeId);
            if (defenderType != null) {
                String attackType = attack.getType().unwrapKey().get().location().getPath().toLowerCase();

                if (containsIgnoreCase(defenderType.resistances, "all") || containsIgnoreCase(defenderType.resistances, attackType)) {
                    damageMultiplier *= 0.5;
                }
                if (containsIgnoreCase(defenderType.weaknesses, "all") || containsIgnoreCase(defenderType.weaknesses, attackType)) {
                    damageMultiplier *= 2.0;
                }

                if (defenderType.stat_modifiers != null) {
                    if (category == AttackCategory.PHYSICAL) {
                        damageMultiplier /= defenderType.stat_modifiers.getOrDefault("defense", 1.0);
                    } else if (category == AttackCategory.SPECIAL) {
                        damageMultiplier /= defenderType.stat_modifiers.getOrDefault("sp_def", 1.0);
                    }
                }

                if (defenderType.abilities != null) {
                    for (CustomAbility ability : defenderType.abilities) {
                        if (ability.passive != null && ability.passive.containsKey("damage_reduction_percent")) {
                            Object reductionObj = ability.passive.get("damage_reduction_percent");
                            if (reductionObj instanceof Number) {
                                double reductionPercent = ((Number) reductionObj).doubleValue();
                                damageMultiplier *= (1.0 - (reductionPercent / 100.0));
                            }
                        }
                    }
                }
            }
        }

        if (damageMultiplier != 1.0) {
            double oldDamage = event.damage;
            double newDamage = oldDamage * damageMultiplier;

            if (newDamage < 1 && oldDamage > 0 && damageMultiplier > 0) {
                newDamage = 1;
            }

            event.damage = newDamage;
        }
    }

    private boolean containsIgnoreCase(List<String> list, String key) {
        if (list == null) return false;
        for (String item : list) {
            if (item.equalsIgnoreCase(key)) return true;
        }
        return false;
    }
}