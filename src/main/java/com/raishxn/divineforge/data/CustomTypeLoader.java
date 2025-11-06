package com.raishxn.divineforge.data;

import com.raishxn.divineforge.DivineForge;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.LoaderOptions; // Adicionado import explícito para clareza

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CustomTypeLoader {

    private static CustomTypesConfig config;

    public static void load() {
        File configFile = new File("config/DivineForge/custom_types.yaml");

        if (!configFile.exists()) {
            createDefault(configFile);
        }

        try (InputStream inputStream = new FileInputStream(configFile)) {
            // CORREÇÃO AQUI: Usar DumperOptions se necessário, ou o construtor padrão.
            // Para simplificar e evitar o erro, vamos usar o construtor padrão que geralmente funciona bem.
            Representer representer = new Representer(new org.yaml.snakeyaml.DumperOptions());
            representer.getPropertyUtils().setSkipMissingProperties(true);

            // LoaderOptions é para o Constructor (leitura)
            LoaderOptions loaderOptions = new LoaderOptions();
            Yaml yaml = new Yaml(new Constructor(CustomTypesConfig.class, loaderOptions), representer);

            config = yaml.load(inputStream);
            if (config != null && config.types != null) {
                DivineForge.LOGGER.info("DivineForge: Carregados " + config.types.size() + " tipos customizados.");
            } else {
                DivineForge.LOGGER.warn("DivineForge: Nenhum tipo encontrado ou erro na estrutura do YAML.");
            }
        } catch (Exception e) {
            DivineForge.LOGGER.error("Erro ao carregar custom_types.yaml", e);
        }
    }

    private static void createDefault(File file) {
        try {
            if (file.getParentFile().mkdirs()) {
                DivineForge.LOGGER.info("Criado diretório de configuração para DivineForge.");
            }
            Files.writeString(file.toPath(), "types: {}\n");
        } catch (IOException e) {
            DivineForge.LOGGER.error("Não foi possível criar o arquivo padrão custom_types.yaml", e);
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