package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.status.Burn;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.raishxn.divineforge.data.CustomAbility;
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;
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
        // Usa a chave de registo para obter o nome do tipo (ex: "fire", "water")
        String attackType = attack.getType().unwrapKey().get().location().getPath().toLowerCase();

        // =============================================================================
        // --- 1. ATACANTE (Offensive) ---
        // =============================================================================
        String attackerTypeId = attacker.getPokemon().getPersistentData().getString("divineforge:custom_type");
        if (attackerTypeId != null && !attackerTypeId.isEmpty()) {
            CustomType attackerType = CustomTypeLoader.getType(attackerTypeId);
            if (attackerType != null) {
                // 1.1 Modificadores de Stat Ofensivos
                if (attackerType.stat_modifiers != null) {
                    if (category == AttackCategory.PHYSICAL) {
                        damageMultiplier *= attackerType.stat_modifiers.getOrDefault("attack", 1.0);
                    } else if (category == AttackCategory.SPECIAL) {
                        damageMultiplier *= attackerType.stat_modifiers.getOrDefault("sp_atk", 1.0);
                    }
                }

                // 1.2 Habilidades Ofensivas
                if (attackerType.abilities != null) {
                    for (CustomAbility ability : attackerType.abilities) {
                        // Gatilho: on_fire_move
                        if (ability.on_fire_move != null && attackType.equals("fire")) {
                            // Passamos o BattleController do evento
                            handleOnFireMove(event, ability.on_fire_move, event.getBattleController());

                            // Se houver buff de dano neste ataque
                            Object boostObj = ability.on_fire_move.get("temp_attack_boost_percent");
                            if (boostObj instanceof Number) {
                                double boost = ((Number) boostObj).doubleValue() / 100.0;
                                damageMultiplier *= (1.0 + boost);
                            }
                        }
                    }
                }
            }
        }

        // =============================================================================
        // --- 2. DEFENSOR (Defensive) ---
        // =============================================================================
        String defenderTypeId = defender.getPokemon().getPersistentData().getString("divineforge:custom_type");
        if (defenderTypeId != null && !defenderTypeId.isEmpty()) {
            CustomType defenderType = CustomTypeLoader.getType(defenderTypeId);
            if (defenderType != null) {
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

                // 2.3 Habilidades Passivas de Redução de Dano
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

        // --- 3. APLICAR DANO FINAL ---
        if (damageMultiplier != 1.0) {
            double oldDamage = event.damage;
            double newDamage = oldDamage * damageMultiplier;
            if (newDamage < 1 && oldDamage > 0 && damageMultiplier > 0) {
                newDamage = 1;
            }
            event.damage = newDamage;
        }
    }

    private void handleOnFireMove(AttackEvent.Damage event, Map<String, Object> effectData, BattleController bc) {
        if (bc == null) return;

        // Efeito: chance_burn
        Object chanceObj = effectData.get("chance_burn");
        if (chanceObj instanceof Number) {
            int chance = ((Number) chanceObj).intValue();
            // Verifica a chance e se o controlador de batalha é válido
            if (RANDOM.nextInt(100) < chance) {
                // Aplica Burn ao alvo do ataque (defender)
                event.target.setStatus(new Burn());
                bc.sendToAll(event.target.getNickname() + " foi queimado pela habilidade Embersurge!");
            }
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