package com.raishxn.divineforge.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raishxn.divineforge.DivineForge;

import java.io.*;
import java.nio.file.Files;

public class CustomTypeLoader {

    private static CustomTypesConfig config;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        File configFile = new File("config/DivineForge/custom_types.json");

        if (!configFile.exists()) {
            createDefault(configFile);
        }

        try (Reader reader = new FileReader(configFile)) {
            // A magia acontece aqui: GSON lê o JSON e converte diretamente para a nossa classe Java
            config = GSON.fromJson(reader, CustomTypesConfig.class);

            if (config != null && config.types != null) {
                DivineForge.LOGGER.info("DivineForge: Carregados " + config.types.size() + " tipos customizados (via GSON).");
            } else {
                DivineForge.LOGGER.warn("DivineForge: Nenhum tipo encontrado no custom_types.json.");
            }
        } catch (IOException e) {
            DivineForge.LOGGER.error("Erro ao ler custom_types.json", e);
        } catch (Exception e) {
            DivineForge.LOGGER.error("Erro ao fazer parse do JSON. Verifique a sintaxe.", e);
        }
    }

    private static void createDefault(File file) {
        try {
            if (file.getParentFile().mkdirs()) {
                DivineForge.LOGGER.info("Criado diretório de configuração para DivineForge.");
            }
            // Cria um JSON básico válido
            Files.writeString(file.toPath(), "{\n  \"types\": {}\n}");
        } catch (IOException e) {
            DivineForge.LOGGER.error("Não foi possível criar o ficheiro padrão custom_types.json", e);
        }
    }

    public static CustomTypesConfig getConfig() {
        return config;
    }

    public static CustomType getType(String id) {
        if (config == null || config.types == null) return null;
        return config.types.get(id);
    }
}