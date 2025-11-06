package com.raishxn.divineforge.listener;

import com.pixelmonmod.pixelmon.entities.npcs.NPC;
import com.raishxn.divineforge.DivineForge;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = DivineForge.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NPCSpawnExampleListener {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof NPC npc)) {
            return;
        }

        npc.setCustomName(Component.literal("Custom Name"));
    }

}
