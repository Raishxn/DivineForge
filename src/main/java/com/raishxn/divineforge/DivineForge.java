package com.raishxn.divineforge;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.config.api.yaml.YamlConfigFactory;
import com.pixelmonmod.tcg.TCG;
import com.raishxn.divineforge.command.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.raishxn.divineforge.config.DivineForgeConfig;
import your.domain.path.listener.PixelmonEggHatchExampleListener;
import your.domain.path.listener.PokemonSpawnExampleListener;
import your.domain.path.listener.tcg.PackOpeningListener;

import java.io.IOException;

@Mod(DivineForge.MOD_ID)
@EventBusSubscriber(modid = DivineForge.MOD_ID)
public class DivineForge {

    public static final String MOD_ID = "divineforge";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static DivineForge instance;

    private DivineForgeConfig config;

    public DivineForge(IEventBus bus) {
        instance = this;

        reloadConfig();
        com.raishxn.divineforge.data.CustomTypeLoader.load();
        NeoForge.EVENT_BUS.register(new com.raishxn.divineforge.listener.StatModificationListener());
        bus.addListener(DivineForge::onModLoad);
    }

    public static void onModLoad(FMLCommonSetupEvent event) {
        // Here is how you register a listener for Pixelmon events
        // Pixelmon has its own event bus for its events, as does TCG
        // So any event listener for those mods need to be registered to those specific event buses
        Pixelmon.EVENT_BUS.register(new PixelmonEggHatchExampleListener());
        Pixelmon.EVENT_BUS.register(new PokemonSpawnExampleListener());
        TCG.EVENT_BUS.register(new PackOpeningListener());
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // Logic for when the server is starting here
    }

    public void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(DivineForgeConfig.class);
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        // Logic for once the server has started here
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        //Register command logic here
        // Commands don't have to be registered here
        // However, not registering them here can lead to some hybrids/server software not recognising the commands
        ExampleCommand.register(event.getDispatcher());
        SetTypeCommand.register(event.getDispatcher());
        ReloadCommand.register(event.getDispatcher());
        MoreComplicatedCommand.register(event.getDispatcher());
        CustomHealCommand.register(event.getDispatcher());
        CustomSpawnCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        // Logic for when the server is stopping
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        // Logic for when the server is stopped
    }

    public static DivineForge getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static DivineForgeConfig getConfig() {
        return instance.config;
    }
}
