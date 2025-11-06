package com.raishxn.divineforge.mixin;

import com.pixelmonmod.pixelmon.api.pokemon.Nature;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Stats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.PermanentStats;
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PermanentStats.class, remap = false)
public abstract class PermanentStatsMixin {

    @Shadow
    public abstract Pokemon getPokemon();

    // =============================================================================
    // INJECT 1: HP (Vida Máxima) com DEBUG
    // =============================================================================
    @Inject(method = "calculateHP", at = @At("RETURN"), cancellable = true, remap = false)
    private void onCalculateHP(Stats stats, int level, CallbackInfoReturnable<Integer> cir) {
        Pokemon pokemon = this.getPokemon();

        // DEBUG 1: Verifica se o Pokémon existe
        if (pokemon == null) {
            // System.out.println("DEBUG HP: Pokemon é null");
            return;
        }

        // Tenta obter o ID. Se não existir, devolve null ou string vazia.
        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");

        // DEBUG 2: Mostra qual é o ID que encontrou (ou se está vazio)
        // System.out.println("DEBUG HP: " + pokemon.getDisplayName().getString() + " tem custom_type='" + customTypeId + "'");

        if (customTypeId == null || customTypeId.isEmpty()) return;

        CustomType customType = CustomTypeLoader.getType(customTypeId);

        // DEBUG 3: Verifica se encontrou o tipo na config
        if (customType == null) {
            System.out.println("ERRO CRÍTICO: Tipo '" + customTypeId + "' existe no NBT mas NÃO foi carregado do JSON!");
            return;
        }

        if (customType.stat_modifiers == null) return;

        double modifier = customType.stat_modifiers.getOrDefault("hp", 1.0);

        if (modifier != 1.0) {
            int originalHp = cir.getReturnValue();
            int newHp = (int) (originalHp * modifier);
            cir.setReturnValue(newHp);

            // DEBUG 4: Sucesso! Mostra a alteração.
            System.out.println(">> SUCESSO HP: " + pokemon.getDisplayName().getString() + " alterado de " + originalHp + " para " + newHp + " (x" + modifier + ")");
        }
    }

    // =============================================================================
    // INJECT 2: Outros Stats
    // =============================================================================
    @Inject(method = "calculateStat", at = @At("RETURN"), cancellable = true, remap = false)
    private void onCalculateStat(BattleStatsType stat, Nature nature, Stats stats, int level, CallbackInfoReturnable<Integer> cir) {
        if (stat == BattleStatsType.HP) return;

        Pokemon pokemon = this.getPokemon();
        if (pokemon == null) return;

        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");
        if (customTypeId == null || customTypeId.isEmpty()) return;

        CustomType customType = CustomTypeLoader.getType(customTypeId);
        if (customType == null || customType.stat_modifiers == null) return;

        double modifier = 1.0;
        switch (stat) {
            case ATTACK: modifier = customType.stat_modifiers.getOrDefault("attack", 1.0); break;
            case DEFENSE: modifier = customType.stat_modifiers.getOrDefault("defense", 1.0); break;
            case SPECIAL_ATTACK: modifier = customType.stat_modifiers.getOrDefault("sp_atk", 1.0); break;
            case SPECIAL_DEFENSE: modifier = customType.stat_modifiers.getOrDefault("sp_def", 1.0); break;
            case SPEED: modifier = customType.stat_modifiers.getOrDefault("speed", 1.0); break;
        }

        if (modifier != 1.0) {
            cir.setReturnValue((int) (cir.getReturnValue() * modifier));
        }
    }
}