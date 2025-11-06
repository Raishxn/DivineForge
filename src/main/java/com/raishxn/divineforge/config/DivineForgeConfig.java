package com.raishxn.divineforge.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/ModId/config.yml")
public class DivineForgeConfig extends AbstractYamlConfig {

    private String exampleField = "Hello World";

    public DivineForgeConfig() {
        super();
    }

    public String getExampleField() {
        return this.exampleField;
    }
}
