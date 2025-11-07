package com.raishxn.divineforge.listener;

// Importe o evento correto da API do Pixelmon
import com.pixelmonmod.pixelmon.api.events.RegisterPixelmonAttacksEvent;
import com.pixelmonmod.pixelmon.api.battles.attack.AttackRegistry;
import com.raishxn.divineforge.moves.CinderBarrageMove;
import com.raishxn.divineforge.moves.OmnislashMove;
// Importe seus novos movimentos aqui
// import com.raishxn.divineforge.moves.DivineBlessingMove;
// import com.raishxn.divineforge.moves.CelestialBladeMove;

import net.neoforged.bus.api.SubscribeEvent;

public class AttackRegistrationListener {

    @SubscribeEvent
    // Use o evento correto no parâmetro do método
    public void onAttackRegistry(RegisterPixelmonAttacksEvent event) {
        // O corpo do seu método já estava correto!
        // Ele chama o método registerAttacks do evento.
        event.registerAttacks(
                new CinderBarrageMove(),
                new OmnislashMove()
                // Adicione os novos movimentos da próxima seção aqui:
                // , new DivineBlessingMove()
                // , new CelestialBladeMove()
        );
    }
}