package com.raishxn.divineforge.mixin;

import com.pixelmonmod.pixelmon.api.pokemon.Nature;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Stats;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.PermanentStats;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.type.DivineTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PermanentStats.class, remap = false)
public abstract class PermanentStatsMixin {

    @Shadow
    public abstract Pokemon getPokemon();

    @Inject(method = "calculateHP", at = @At("RETURN"), cancellable = true)
    private void onCalculateHP(Stats stats, int level, CallbackInfoReturnable<Integer> cir) {
        Pokemon pokemon = this.getPokemon();
        if (pokemon == null) return;

        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");
        DivineType type = DivineTypeRegistry.fromName(customTypeId);

        if (type != null) {
            double modifier = type.getStatModifier("hp");
            if (modifier != 1.0) {
                cir.setReturnValue((int) (cir.getReturnValue() * modifier));
            }
        }
    }

    @Inject(method = "calculateStat", at = @At("RETURN"), cancellable = true)
    private void onCalculateStat(BattleStatsType stat, Nature nature, Stats stats, int level, CallbackInfoReturnable<Integer> cir) {
        if (stat == BattleStatsType.HP) return;

        Pokemon pokemon = this.getPokemon();
        if (pokemon == null) return;

        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");
        DivineType type = DivineTypeRegistry.fromName(customTypeId);

        if (type != null) {
            double modifier = 1.0;
            switch (stat) {
                case ATTACK: modifier = type.getStatModifier("attack"); break;
                case DEFENSE: modifier = type.getStatModifier("defense"); break;
                case SPECIAL_ATTACK: modifier = type.getStatModifier("sp_atk"); break;
                case SPECIAL_DEFENSE: modifier = type.getStatModifier("sp_def"); break;
                case SPEED: modifier = type.getStatModifier("speed"); break;
            }

            if (modifier != 1.0) {
                cir.setReturnValue((int) (cir.getReturnValue() * modifier));
            }
        }
    }
}