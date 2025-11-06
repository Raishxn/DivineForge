package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.events.battles.BattleStartedEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.raishxn.divineforge.data.CustomAbility;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class BattleStartListener {

    @SubscribeEvent
    public void onBattleStart(BattleStartedEvent event) {
        // CORREÇÃO 1: Usa .getBattleController() em vez de .bc
        // Verifica todos os participantes da batalha
        for (PixelmonWrapper participant : event.getBattleController().getActivePokemon()) {
            // CORREÇÃO 2: Usa .getEntity() em vez de .entity
            if (participant.getEntity() == null) continue;

            String customTypeId = participant.getEntity().getPokemon().getPersistentData().getString("divineforge:custom_type");
            if (customTypeId == null || customTypeId.isEmpty()) continue;

            CustomType customType = CustomTypeLoader.getType(customTypeId);
            if (customType == null || customType.abilities == null) continue;

            for (CustomAbility ability : customType.abilities) {
                if (ability.on_battle_start != null) {
                    applyBattleStartEffect(participant, ability.on_battle_start, ability.name);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void applyBattleStartEffect(PixelmonWrapper target, Map<String, Object> effectData, String abilityName) {
        if (effectData.containsKey("apply_buff")) {
            Map<String, Object> buffData = (Map<String, Object>) effectData.get("apply_buff");
            List<String> statsToBuff = (List<String>) buffData.get("stats");
            int amountPercent = ((Number) buffData.getOrDefault("amount_percent", 0)).intValue();
            // int turns = ... (podemos implementar duração depois se necessário)

            if (statsToBuff != null && amountPercent > 0) {
                // +1 stage é aproximadamente +50% no Pixelmon.
                // Para 25%, podemos dar 1 stage para simplificar, ou usar modificadores mais complexos.
                int stages = Math.max(1, amountPercent / 25);

                for (String statName : statsToBuff) {
                    BattleStatsType stat = convertStringToStat(statName);
                    if (stat != BattleStatsType.NONE) {
                        target.getBattleStats().increaseStat(stages, stat, target, false);
                    }
                }

                // CORREÇÃO 3: Usa .getBattleController() para enviar a mensagem
                target.getBattleController().sendToAll(target.getNickname() + "'s " + abilityName + " aumentou os seus status!");
            }
        }
    }

    private BattleStatsType convertStringToStat(String statName) {
        switch (statName.toLowerCase()) {
            case "attack": return BattleStatsType.ATTACK;
            case "defense": return BattleStatsType.DEFENSE;
            case "sp_atk": return BattleStatsType.SPECIAL_ATTACK;
            case "sp_def": return BattleStatsType.SPECIAL_DEFENSE;
            case "speed": return BattleStatsType.SPEED;
            case "accuracy": return BattleStatsType.ACCURACY;
            case "evasion": return BattleStatsType.EVASION;
            default: return BattleStatsType.NONE;
        }
    }
}