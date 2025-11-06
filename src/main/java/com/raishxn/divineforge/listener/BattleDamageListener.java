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
        // Tenta obter a entidade diretamente.
        PixelmonEntity attacker = event.user.getEntity();
        PixelmonEntity defender = event.target.getEntity();
        Attack attack = event.getAttack();

        if (attacker == null || defender == null || attack == null) return;

        double damageMultiplier = 1.0;

        // Usa a categoria diretamente do ataque
        AttackCategory category = attack.getAttackCategory();

        // --- 1. ATACANTE (Offensive) ---
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

        // --- 2. DEFENSOR (Defensive) ---
        String defenderTypeId = defender.getPokemon().getPersistentData().getString("divineforge:custom_type");
        if (defenderTypeId != null && !defenderTypeId.isEmpty()) {
            CustomType defenderType = CustomTypeLoader.getType(defenderTypeId);
            if (defenderType != null) {
                // CORREÇÃO HOLDER: Usa .value() antes de .getName() para "desembrulhar" o tipo.
                String attackType = attack.getType().unwrapKey().get().location().getPath().toLowerCase();

                // 2.1 Resistências e Fraquezas
                if (containsIgnoreCase(defenderType.resistances, "all") || containsIgnoreCase(defenderType.resistances, attackType)) {
                    damageMultiplier *= 0.5;
                }
                if (containsIgnoreCase(defenderType.weaknesses, "all") || containsIgnoreCase(defenderType.weaknesses, attackType)) {
                    damageMultiplier *= 2.0;
                }

                // 2.2 Modificadores de Stat Defensivos
                if (defenderType.stat_modifiers != null) {
                    if (category == AttackCategory.PHYSICAL) {
                        damageMultiplier /= defenderType.stat_modifiers.getOrDefault("defense", 1.0);
                    } else if (category == AttackCategory.SPECIAL) {
                        damageMultiplier /= defenderType.stat_modifiers.getOrDefault("sp_def", 1.0);
                    }
                }

                // 2.3 Habilidades Passivas de Redução de Dano (PASSO 10)
                if (defenderType.abilities != null) {
                    for (CustomAbility ability : defenderType.abilities) {
                        // Verifica se é uma habilidade passiva e se tem o campo 'damage_reduction_percent'
                        if (ability.passive != null && ability.passive.containsKey("damage_reduction_percent")) {
                            Object reductionObj = ability.passive.get("damage_reduction_percent");
                            if (reductionObj instanceof Number) {
                                double reductionPercent = ((Number) reductionObj).doubleValue();
                                // Exemplo: 20% redução = multiplicar por 0.8 (1.0 - 0.20)
                                damageMultiplier *= (1.0 - (reductionPercent / 100.0));
                            }
                        }
                    }
                }
            }
        }

        // --- 3. APLICAR DANO FINAL ---
        if (damageMultiplier != 1.0) {
            double oldDamage = event.damage;
            double newDamage = oldDamage * damageMultiplier;

            // Garante pelo menos 1 de dano se o ataque não foi totalmente anulado
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