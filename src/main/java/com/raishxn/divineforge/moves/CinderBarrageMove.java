package com.raishxn.divineforge.moves;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type;
import
        com.pixelmonmod.pixelmon.client.particle.particles.AttackEffect;
import com.pixelmonmod.pixelmon.battles.attacks.effect.EffectBuilder;
import com.pixelmonmod.pixelmon.battles.status.StatusType;

public class CinderBarrageMove extends CustomDivineMove {

    public CinderBarrageMove() {
        super("Cinder Barrage", // Nome
                Type.FIRE,          // Tipo
                80,                 // Power
                100,                // Accuracy
                AttackCategory.SPECIAL); // Categoria

        // Adiciona o efeito de 30% de chance de Burn
        this.addEffect(EffectBuilder.builder()
                .setChance(0.3f) // 30%
                .setTarget(AttackEffect.Target.TARGET)
                .setStatus(StatusType.Burn)
                .build());
    }
}