package com.raishxn.divineforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.util.DivineHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetTypeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("divineforge")
                .then(Commands.literal("settype")
                        .requires(source -> source.hasPermission(2)) // Requer permissão de OP nível 2
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                        .then(Commands.argument("type", StringArgumentType.word())
                                                .executes(SetTypeCommand::execute))))));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        // Pixelmon usa slots 0-5, o comando usa 1-6 para ser mais amigável
        int slot = IntegerArgumentType.getInteger(context, "slot") - 1;
        String typeName = StringArgumentType.getString(context, "type");

        PlayerPartyStorage storage = StorageProxy.getPartyNow(player);
        Pokemon pokemon = storage.get(slot);

        if (pokemon == null) {
            context.getSource().sendFailure(Component.literal("Não existe nenhum Pokémon nessa slot."));
            return 0;
        }

        // --- VALIDAÇÃO DO NOVO SISTEMA HARDCODED ---
        DivineType type = DivineType.fromName(typeName);

        if (type == null) {
            // Se o tipo não foi encontrado no Enum, mostra erro e lista os disponíveis (opcional)
            context.getSource().sendFailure(Component.literal("Tipo divino inválido: " + typeName));
            return 0;
        }

        // Guarda o NOME INTERNO do Enum (ex: "COSMIC") para consistência
        pokemon.getPersistentData().putString(DivineHelper.NBT_KEY, type.name());

        context.getSource().sendSuccess(() -> Component.literal(
                "Definido o tipo divino de " + pokemon.getDisplayName().getString() +
                        " para " + type.getName() + " (Slot " + (slot + 1) + ")"), true);

        return 1;
    }
}