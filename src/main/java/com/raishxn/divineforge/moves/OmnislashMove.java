package com.raishxn.divineforge.moves;

import com.pixelmonmod.pixelmon.api.battles.AttackCategory;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type;
import com.raishxn.divineforge.type.DivineType;

public class OmnislashMove extends CustomDivineMove {

    public OmnislashMove() {
        super("Omnislash",
                Type.parseOrNull(DivineType.GOD.name().toLowerCase()), // Tenta buscar o tipo customizado "GOD"
                150,
                100,
                AttackCategory.PHYSICAL);
    }
}