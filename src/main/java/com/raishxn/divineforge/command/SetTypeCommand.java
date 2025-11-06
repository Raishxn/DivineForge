package com.raishxn.divineforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.raishxn.divineforge.type.DivineType;
import com.raishxn.divineforge.type.DivineTypeRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SetTypeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("divineforge")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("settype")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                        .then(Commands.argument("type", StringArgumentType.word())
                                                .suggests(SetTypeCommand::suggestTypes)
                                                .executes(context -> execute(context, StringArgumentType.getString(context, "type")))
                                        )
                                )
                        )
                )
        );
    }

    private static CompletableFuture<Suggestions> suggestTypes(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        // Sugere os nomes dos tipos do nosso Enum
        return SharedSuggestionProvider.suggest(
                Arrays.stream(DivineType.values()).map(t -> t.name().toLowerCase()).collect(Collectors.toList()),
                builder
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context, String typeId) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        int slot = IntegerArgumentType.getInteger(context, "slot") - 1;

        // USA O NOVO REGISTRY
        DivineType type = DivineTypeRegistry.fromName(typeId);

        if (type == null) {
            context.getSource().sendFailure(Component.literal("§cErro: Tipo '" + typeId + "' não encontrado."));
            return 0;
        }

        PlayerPartyStorage storage = StorageProxy.getPartyNow(player);
        Pokemon pokemon = storage.get(slot);

        if (pokemon != null) {
            // Grava o NOME do Enum no NBT
            pokemon.getPersistentData().putString("divineforge:custom_type", type.name());

            pokemon.getStats().recalculateStats();
            pokemon.heal();

            pokemon.setNickname(Component.literal("§b[" + type.getName() + "] §r" + pokemon.getSpecies().getName()));

            context.getSource().sendSuccess(() -> Component.literal("§aAplicado tipo " + type.getName() + " a " + pokemon.getDisplayName().getString()), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("§cSlot vazio."));
            return 0;
        }
    }
}