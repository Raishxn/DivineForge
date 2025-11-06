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

@Mixin(PermanentStats.class)
public abstract class PermanentStatsMixin {

    @Shadow
    public abstract Pokemon getPokemon();

    @Inject(method = "calculateStat", at = @At("RETURN"), cancellable = true, remap = false)
    private void onCalculateStat(BattleStatsType stat, Nature nature, Stats stats, int level, CallbackInfoReturnable<Integer> cir) {
        Pokemon pokemon = this.getPokemon();
        if (pokemon == null) return;

        String customTypeId = pokemon.getPersistentData().getString("divineforge:custom_type");
        if (customTypeId == null || customTypeId.isEmpty()) return;

        CustomType customType = CustomTypeLoader.getType(customTypeId);
        if (customType == null || customType.stat_modifiers == null) return;

        double modifier = 1.0;
        switch (stat) {
            case HP:
                modifier = customType.stat_modifiers.getOrDefault("hp", 1.0);
                break;
            case ATTACK:
                modifier = customType.stat_modifiers.getOrDefault("attack", 1.0);
                break;
            case DEFENSE:
                modifier = customType.stat_modifiers.getOrDefault("defense", 1.0);
                break;
            case SPECIAL_ATTACK:
                modifier = customType.stat_modifiers.getOrDefault("sp_atk", 1.0);
                break;
            case SPECIAL_DEFENSE:
                modifier = customType.stat_modifiers.getOrDefault("sp_def", 1.0);
                break;
            case SPEED:
                modifier = customType.stat_modifiers.getOrDefault("speed", 1.0);
                break;
        }

        if (modifier != 1.0) {
            int originalValue = cir.getReturnValue();
            int newValue = (int) (originalValue * modifier);
            cir.setReturnValue(newValue);
        }
    }
}