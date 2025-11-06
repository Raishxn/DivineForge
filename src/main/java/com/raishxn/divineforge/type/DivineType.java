package com.raishxn.divineforge.type;

import java.util.HashMap;
import java.util.Map;

public enum DivineType {
    // Tipos Originais
    GOD("God", "Divine typification reserved for owners."),
    EMBER("Ember", "Persistent flame that burns and warms."),

    // Novos Tipos do PDF (Incluindo Shadow)
    UNKNOWN("???", "Mysterious and undefined type."),
    SHADOW("Shadow", "Darker than dark, ancient and corrupting."),
    COSMIC("Cosmic", "Power from the vast cosmos."),
    CRYSTAL("Crystal", "Shimmering and highly reflective."),
    DIGITAL("Digital", "Born from data and code."),
    LIGHT("Light", "Pure illumination that banishes darkness."),
    NUCLEAR("Nuclear", "Unstable, radioactive, and immensely powerful."),
    PLASTIC("Plastic", "Synthetic, durable, and versatile material."),
    SLIME("Slime", "Amorphous, sticky, and adaptable."),
    SOUND("Sound", "Powerful sonic vibrations."),
    WIND("Wind", "Swift currents of air and storms."),
    ELDRITCH("Eldritch", "Incomprehensible horrors from beyond."),
    BLOOD("Blood", "Vital essence of life, powerful but vulnerable.");

    private final String displayName;
    private final String description;
    private final Map<String, Float> effectivenessMap = new HashMap<>();
    private final Map<String, Double> statModifiers = new HashMap<>();

    DivineType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setEffectivenessFrom(String attackingType, float multiplier) {
        effectivenessMap.put(attackingType.toLowerCase(), multiplier);
    }

    public float getEffectivenessFrom(String attackingType) {
        return effectivenessMap.getOrDefault(attackingType.toLowerCase(), 1.0f);
    }

    public void setStatModifier(String stat, double modifier) {
        statModifiers.put(stat.toLowerCase(), modifier);
    }

    public double getStatModifier(String stat) {
        return statModifiers.getOrDefault(stat.toLowerCase(), 1.0);
    }
}