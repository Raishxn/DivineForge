package com.raishxn.divineforge.mixin;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.status.StatusBase;
import com.pixelmonmod.pixelmon.battles.status.StatusType;
import com.raishxn.divineforge.registry.DivineAbility;
import com.raishxn.divineforge.util.DivineHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PixelmonWrapper.class)
public class StatusImmunityMixin {

    @Inject(method = "cannotHaveStatus(Lcom/pixelmonmod/pixelmon/battles/status/StatusBase;Lcom/pixelmonmod/pixelmon/battles/controller/participants/PixelmonWrapper;)Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void onCannotHaveStatus(StatusBase status, PixelmonWrapper opponent, CallbackInfoReturnable<Boolean> cir) {
        PixelmonWrapper self = (PixelmonWrapper) (Object) this;
        Pokemon pokemon = self.getOriginalPokemon();

        // --- USO DO NOVO HELPER ---
        // Verifica se tem a habilidade 'Cover me in Debris'
        if (DivineHelper.hasAbility(pokemon, DivineAbility.COVER_ME_IN_DEBRIS)) {
            // Se a habilidade existe E o status for Burn, impede a aplicação.
            if (status.type == StatusType.Burn) {
                cir.setReturnValue(true); // Retorna true = "Sim, não pode ter este status"
                return;
            }
        }

        // Exemplo futuro para outras imunidades:
        // if (DivineHelper.hasAbility(pokemon, DivineAbility.OUTRA_HABILIDADE) && status.type == StatusType.Poison) {
        //      cir.setReturnValue(true);
        // }
    }
}