package com.raishxn.divineforge.type;

import com.raishxn.divineforge.registry.DivineAbility;
import java.util.*;

public enum DivineType {
    // =================================================================================
    // PARA ADICIONAR HABILIDADES: Coloque vírgulas após a descrição e liste as habilidades.
    // Exemplo: GOD("God", "Desc...", DivineAbility.OMNIPOTENT, DivineAbility.OUTRA)
    // =================================================================================

    // Tipos Originais
    GOD("God", "Divine typification reserved for owners."),
    EMBER("Ember", "Persistent flame that burns and warms."),

    // Novos Tipos
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
    // --- CAMPO DE HABILIDADES ADICIONADO ---
    private final List<DivineAbility> abilities;
    // ------------------------------------
    private final Map<String, Float> effectivenessMap = new HashMap<>();
    private final Map<String, Double> statModifiers = new HashMap<>();

    // --- CONSTRUTOR ATUALIZADO ---
    // Aceita uma lista opcional de habilidades no final
    DivineType(String displayName, String description, DivineAbility... abilities) {
        this.displayName = displayName;
        this.description = description;
        this.abilities = Collections.unmodifiableList(Arrays.asList(abilities));
    }

    public String getName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    // --- NOVOS MÉTODOS DE HABILIDADE ---
    public List<DivineAbility> getAbilities() {
        return abilities;
    }

    public boolean hasAbility(DivineAbility ability) {
        return abilities.contains(ability);
    }
    // ---------------------------------

    // --- MÉTODOS EXISTENTES ---
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

    public Map<String, Double> getStatModifiers() {
        return new HashMap<>(statModifiers);
    }

    // --- MÉTODO 'fromName' ADICIONADO ---
    /**
     * Encontra um DivineType pelo seu nome de Enum (ex: "COSMIC") ou seu nome visível (ex: "Cosmic").
     * @param name O nome para procurar.
     * @return O DivineType, ou null se não for encontrado.
     */
    public static DivineType fromName(String name) {
        if (name == null || name.isEmpty()) return null;
        for (DivineType type : values()) {
            // Verifica o nome do Enum (COSMIC) e o nome de exibição (Cosmic)
            if (type.name().equalsIgnoreCase(name) || type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}