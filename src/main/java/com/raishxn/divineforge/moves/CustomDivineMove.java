package com.raishxn.divineforge.moves;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.battles.attacks.specialAttacks.basic.SpecialAttackBase;
import net.minecraft.resources.ResourceKey;

import java.util.Collections;
import java.util.List;

/**
 * Classe base para todos os novos movimentos do DivineForge.
 * Simplifica o registo ao definir propriedades comuns no construtor.
 */
public class CustomDivineMove extends SpecialAttackBase {

    private final String name;
    private final ResourceKey<Type> type;
    private final int power;
    private final int accuracy;
    private final AttackCategory category;

    public CustomDivineMove(String name, ResourceKey<Type> type, int power, int accuracy, AttackCategory category) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.category = category;
    }

    @Override
    public String getAttackName() {
        return this.name;
    }

    @Override
    public ResourceKey<Type> getAttackType() {
        return this.type;
    }

    @Override
    public int getBasePower() {
        return this.power;
    }

    @Override
    public int getAccuracy() {
        return this.accuracy;
    }

    @Override
    public AttackCategory getAttackCategory() {
        return this.category;
    }

    // Define o PP padrão
    @Override
    public int getPPBase() {
        return 10;
    }
}