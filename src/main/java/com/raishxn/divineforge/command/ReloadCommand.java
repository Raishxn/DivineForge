package com.raishxn.divineforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.raishxn.divineforge.DivineForge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ReloadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("divineforge")
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("reload")
                        .executes(context -> {
                            long start = System.currentTimeMillis();
                            context.getSource().sendSystemMessage(Component.literal("§eA recarregar DivineForge..."));

                            try {


                                DivineForge.getInstance().reloadConfig();

                                long timeTaken = System.currentTimeMillis() - start;
                                context.getSource().sendSuccess(() -> Component.literal("§aDivineForge recarregado com sucesso em " + timeTaken + "ms!"), true);
                                return 1;
                            } catch (Exception e) {
                                context.getSource().sendFailure(Component.literal("§cErro ao recarregar configurações! Verifique a consola."));
                                DivineForge.LOGGER.error("Erro crítico ao executar /divineforge reload", e);
                                return 0;
                            }
                        })
                )
        );
    }
}