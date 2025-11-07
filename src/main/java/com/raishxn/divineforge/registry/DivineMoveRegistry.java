package com.raishxn.divineforge.registry;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.battles.attacks.effect.AttackEffect;
import com.pixelmonmod.pixelmon.battles.attacks.effect.EffectBase;
import com.pixelmonmod.pixelmon.battles.attacks.effect.EffectBuilder;
import com.pixelmonmod.pixelmon.battles.status.StatusType;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.ArrayList;

public class DivineMoveRegistry {

    public static final List<ImmutableAttack> MOVES = new ArrayList<>();

    // --- DEFINIÇÃO DOS MOVIMENTOS ---
    // Vamos definir os seus movimentos aqui como constantes estáticas.

    // Exemplo: Cinder Barrage (EMBER) - Especial, 80 BP, 30% chance de Burn
    public static final ImmutableAttack CINDER_BARRAGE = createMove(
            "cinderbarrage", "Cinder Barrage", Type.FIRE,
            80, 100, AttackCategory.SPECIAL,
            EffectBuilder.builder()
                    .setChance(0.3f) // 30%
                    .setTarget(AttackEffect.Target.TARGET)
                    .setStatus(StatusType.Burn)
                    .build()
    );

    // Exemplo: Omnislash (GOD) - Físico, 150 BP, ignora defesa
    // Nota: "Ignorar defesa" requer um EffectBase customizado ou flags especiais.
    // Por agora, vamos registá-lo como um ataque forte.
    public static final ImmutableAttack OMNISLASH = createMove(
            "omnislash", "Omnislash", DivineType.GOD, // Usa o tipo GOD do seu Enum
            150, 100, AttackCategory.PHYSICAL
            // Adicionaremos o efeito "ignora defesa" depois
    );

    // Exemplo: Celestial Judgment (GOD) - Especial, 200 BP, acerta todos os oponentes
    public static final ImmutableAttack CELESTIAL_JUDGMENT = createMove(
            "celestialjudgment", "Celestial Judgment", DivineType.GOD,
            200, 100, AttackCategory.SPECIAL
            // Precisará de flags para acertar múltiplos alvos
    );

    /**
     * Método auxiliar para criar um ImmutableAttack simples.
     */
    private static ImmutableAttack createMove(String internalName, String displayName, ResourceKey<Type> type,
                                              int power, int accuracy, AttackCategory category, EffectBase... effects) {

        ImmutableAttack.Builder builder = ImmutableAttack.builder()
                .name(internalName)
                .displayName(displayName)
                .type(type)
                .power(power)
                .accuracy(accuracy)
                .category(category)
                .pp(10); // PP Padrão, pode alterar

        for (EffectBase effect : effects) {
            builder.addEffect(effect);
        }

        ImmutableAttack attack = builder.build();
        MOVES.add(attack);
        return attack;
    }

    /**
     * Método auxiliar para tipos customizados (DivineType).
     * Converte o Enum DivineType para a ResourceKey<Type> que o Pixelmon espera.
     */
    private static ImmutableAttack createMove(String internalName, String displayName, DivineType divineType,
                                              int power, int accuracy, AttackCategory category, EffectBase... effects) {

        ResourceKey<Type> pixelmonType = null;
        try {
            // Tenta buscar o tipo pelo nome (ex: "GOD" -> "divineforge:god" ou "pixelmon:god")
            pixelmonType = Type.parseOrNull(divineType.name().toLowerCase());
        } catch (Exception e) {
            // Fallback para tipo Normal se o tipo customizado não for encontrado
            pixelmonType = Type.NORMAL;
        }

        return createMove(internalName, displayName, pixelmonType, power, accuracy, category, effects);
    }
}