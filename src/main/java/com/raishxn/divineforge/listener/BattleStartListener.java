package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.api.events.battles.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.status.Hail;
import com.pixelmonmod.pixelmon.battles.status.Rainy;
import com.pixelmonmod.pixelmon.battles.status.Sandstorm;
import com.pixelmonmod.pixelmon.battles.status.Sunny;
import com.pixelmonmod.pixelmon.battles.status.GlobalStatusBase;
import com.raishxn.divineforge.registry.DivineAbility;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.util.DivineHelper;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Random;

public class BattleStartListener {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public void onBattleStart(BattleStartedEvent event) {
        BattleController bc = event.getBattleController();
        for (PixelmonWrapper participant : bc.getActivePokemon()) {
            if (participant == null) continue; // Verificação de segurança

            // CORREÇÃO AQUI:
            // PixelmonWrapper 'é' um Pokemon, então passamos 'participant' diretamente.
            DivineType type = DivineHelper.getDivineType(participant);
            if (type == null) continue;

            // --- HABILIDADES DE CLIMA ---

            // CLIMATE CHANGE: Invoca um clima aleatório
            if (type.hasAbility(DivineAbility.CLIMATE_CHANGE)) {
                GlobalStatusBase[] weathers = {
                        new Sunny(5),
                        new Rainy(5),
                        new Sandstorm(5),
                        new Hail(5)
                };
                GlobalStatusBase chosenWeather = weathers[RANDOM.nextInt(weathers.length)];
                bc.globalStatusController.addGlobalStatus(participant, chosenWeather);
            }

            // ICE AGE: Invoca Granizo
            if (type.hasAbility(DivineAbility.ICE_AGE)) {
                bc.globalStatusController.addGlobalStatus(participant, new Hail(5));
            }

            // STORMBRINGER: Invoca Chuva
            if (type.hasAbility(DivineAbility.STORMBRINGER)) {
                bc.globalStatusController.addGlobalStatus(participant, new Rainy(5));
            }

            // --- HABILIDADES DE EFEITO DE CAMPO ---

            // FOOL'S GOLD: Aumenta Crit, diminui Accuracy
            if (type.hasAbility(DivineAbility.FOOLS_GOLD)) {
                participant.getBattleStats().decreaseStat(1, BattleStatsType.ACCURACY, participant, false);
                bc.sendToAll(participant.getNickname() + " brilha com Fool's Gold!");
            }

            // CIRCUS PROPS: Trick Room
            if (type.hasAbility(DivineAbility.CIRCUS_PROPS)) {
                // Nota: Verifique a sua API para a forma correta de aplicar Trick Room
                // (Pode ser bc.battleMechanics.setTrickRoom(5) ou bc.globalStatusController.addGlobalStatus(new TrickRoom(5)))
            }

            // COSMIC PRESENCE: Gravity
            if (type.hasAbility(DivineAbility.COSMIC_PRESENCE)) {
                // (Mesma nota do Circus Props)
            }
        }
    }
}