package com.raishxn.divineforge.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.registry.DivineAbility;

public class DivineHelper {

    // A chave NBT onde guardamos o nome do tipo (ex: "COSMIC")
    public static final String NBT_KEY = "divineforge:custom_type";

    /**
     * Obtém o DivineType de um Pokémon com base na sua tag NBT.
     * @param pokemon O Pokémon a verificar.
     * @return O DivineType correspondente, ou null se não tiver nenhum.
     */
    public static DivineType getDivineType(Pokemon pokemon) {
        if (pokemon == null) return null;
        String typeName = pokemon.getPersistentData().getString(NBT_KEY);
        if (typeName == null || typeName.isEmpty()) return null;

        try {
            // Tenta encontrar o tipo pelo nome do Enum (ex: "COSMIC")
            return DivineType.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Se o nome no NBT não corresponder a nenhum tipo, retorna null
            return null;
        }
    }

    /**
     * Verifica se um Pokémon possui uma habilidade específica.
     * @param pokemon O Pokémon a verificar.
     * @param ability A habilidade que queremos saber se ele tem.
     * @return true se tiver a habilidade, false caso contrário.
     */
    public static boolean hasAbility(Pokemon pokemon, DivineAbility ability) {
        DivineType type = getDivineType(pokemon);
        // Verifica se o Pokémon tem um tipo E se esse tipo tem a habilidade na sua lista
        return type != null && type.hasAbility(ability);
    }
}