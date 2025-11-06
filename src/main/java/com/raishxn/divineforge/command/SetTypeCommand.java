package com.raishxn.divineforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.raishxn.divineforge.data.CustomType;
import com.raishxn.divineforge.data.CustomTypeLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.CompletableFuture;

public class SetTypeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("divineforge")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("settype")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("slot", IntegerArgumentType.integer(1, 6))
                                        .then(Commands.argument("type", StringArgumentType.word())
                                                .suggests(SetTypeCommand::suggestTypes)
                                                .executes(context -> {
                                                    try {
                                                        return execute(context, StringArgumentType.getString(context, "type"));
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                })
                                        )
                                )
                        )
                )
        );
    }

    private static CompletableFuture<Suggestions> suggestTypes(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(CustomTypeLoader.getConfig().types.keySet(), builder);
    }

    private static int execute(CommandContext<CommandSourceStack> context, String typeId) throws Exception {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        int slot = IntegerArgumentType.getInteger(context, "slot") - 1;
        CustomType type = CustomTypeLoader.getType(typeId);

        if (type == null) {
            context.getSource().sendFailure(Component.literal("Tipo não encontrado: " + typeId));
            return 0;
        }

        PlayerPartyStorage storage = StorageProxy.getPartyNow(player);
        Pokemon pokemon = storage.get(slot);

        if (pokemon != null) {
            // APLICAÇÃO DO TIPO
            // Aqui vamos salvar o ID do tipo customizado no NBT do Pokémon para persistência
            pokemon.getPersistentData().putString("divineforge:custom_type", type.id);

            // Exemplo de aplicação imediata: Mudar o nickname (temporário, para visualização)
            pokemon.setNickname(Component.literal("§b[" + type.display_name + "] §r" + pokemon.getSpecies().getName()));

            context.getSource().sendSuccess(() -> Component.literal("Aplicado tipo " + type.display_name + " a " + pokemon.getDisplayName().getString()), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("Slot vazio."));
            return 0;
        }
    }
}