package com.raishxn.divineforge.type;


public final class DivineTypeRegistry {

    public static void init() {

        initShadow();
        initUnknown();
        initCosmic();
        initCrystal();
        initDigital();
        initLight();
        initNuclear();
        initPlastic();
        initSlime();
        initSound();
        initWind();
        initEldritch();
        initBlood();
    }

    private static void initShadow() {
        DivineType.SHADOW.setEffectivenessFrom("normal", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("fire", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("water", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("electric", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("grass", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("ice", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("fighting", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("poison", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("ground", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("flying", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("psychic", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("bug", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("rock", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("ghost", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("dragon", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("dark", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("steel", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("fairy", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("shadow", 0.5f);
        DivineType.SHADOW.setEffectivenessFrom("cosmic", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("crystal", 0.5f);
        DivineType.SHADOW.setEffectivenessFrom("digital", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("light", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("nuclear", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("plastic", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("slime", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("sound", 0.5f);
        DivineType.SHADOW.setEffectivenessFrom("wind", 2.0f);
        DivineType.SHADOW.setEffectivenessFrom("eldritch", 0.5f);
        DivineType.SHADOW.setEffectivenessFrom("blood", 2.0f);
    }

    private static void initUnknown() {
        // '???' row in table - all zeros in your table; map to divine unknown type if exists
        DivineType.UNKNOWN.setEffectivenessFrom("normal", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("fire", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("water", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("electric", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("grass", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("ice", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("fighting", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("poison", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("ground", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("flying", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("psychic", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("bug", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("rock", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("ghost", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("dragon", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("dark", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("steel", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("fairy", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("shadow", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("cosmic", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("crystal", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("digital", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("light", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("nuclear", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("plastic", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("slime", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("sound", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("wind", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("eldritch", 0.0f);
        DivineType.UNKNOWN.setEffectivenessFrom("blood", 0.0f);
    }

    private static void initCosmic() {
        DivineType.COSMIC.setEffectivenessFrom("fire", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("water", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("electric", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("grass", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("ice", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("fighting", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("poison", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("ground", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("flying", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("psychic", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("bug", 0.5f);
        DivineType.COSMIC.setEffectivenessFrom("dragon", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("shadow", 2.0f);
        DivineType.COSMIC.setEffectivenessFrom("sound", 0.5f);
    }

    private static void initCrystal() {
        DivineType.CRYSTAL.setEffectivenessFrom("fire", 2.0f);
        DivineType.CRYSTAL.setEffectivenessFrom("ice", 2.0f);
        DivineType.CRYSTAL.setEffectivenessFrom("fighting", 2.0f);
        DivineType.CRYSTAL.setEffectivenessFrom("poison", 0.5f);
        DivineType.CRYSTAL.setEffectivenessFrom("ground", 2.0f);
        DivineType.CRYSTAL.setEffectivenessFrom("flying", 0.5f);
        DivineType.CRYSTAL.setEffectivenessFrom("psychic", 0.5f);
        DivineType.CRYSTAL.setEffectivenessFrom("shadow", 0.5f);
    }

    private static void initDigital() {
        DivineType.DIGITAL.setEffectivenessFrom("normal", 0.5f);
        DivineType.DIGITAL.setEffectivenessFrom("fire", 0.5f);
        DivineType.DIGITAL.setEffectivenessFrom("water", 0.5f);
        DivineType.DIGITAL.setEffectivenessFrom("electric", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("grass", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("ice", 0.5f);
        DivineType.DIGITAL.setEffectivenessFrom("fighting", 0.5f);
        DivineType.DIGITAL.setEffectivenessFrom("poison", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("ground", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("flying", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("psychic", 2.0f);
        DivineType.DIGITAL.setEffectivenessFrom("bug", 2.0f);
    }

    private static void initLight() {
        DivineType.LIGHT.setEffectivenessFrom("fire", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("water", 0.0f);
        DivineType.LIGHT.setEffectivenessFrom("electric", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("grass", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("ice", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("fighting", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("poison", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("ground", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("flying", 2.0f);
        DivineType.LIGHT.setEffectivenessFrom("psychic", 0.0f);
        DivineType.LIGHT.setEffectivenessFrom("bug", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("rock", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("ghost", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("dragon", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("dark", 0.5f);
        DivineType.LIGHT.setEffectivenessFrom("shadow", 2.0f);
    }

    private static void initNuclear() {
        DivineType.NUCLEAR.setEffectivenessFrom("normal", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("fire", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("water", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("electric", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("grass", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("ice", 0.5f);
        DivineType.NUCLEAR.setEffectivenessFrom("fighting", 0.5f);
        DivineType.NUCLEAR.setEffectivenessFrom("poison", 0.5f);
        DivineType.NUCLEAR.setEffectivenessFrom("ground", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("flying", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("psychic", 0.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("bug", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("rock", 2.0f);
        DivineType.NUCLEAR.setEffectivenessFrom("ghost", 2.0f);
    }

    private static void initPlastic() {
        DivineType.PLASTIC.setEffectivenessFrom("fire", 2.0f);
        DivineType.PLASTIC.setEffectivenessFrom("water", 2.0f);
        DivineType.PLASTIC.setEffectivenessFrom("electric", 2.0f);
        DivineType.PLASTIC.setEffectivenessFrom("grass", 0.5f);
        DivineType.PLASTIC.setEffectivenessFrom("ice", 2.0f);
        DivineType.PLASTIC.setEffectivenessFrom("fighting", 0.5f);
        DivineType.PLASTIC.setEffectivenessFrom("poison", 0.5f);
        DivineType.PLASTIC.setEffectivenessFrom("ground", 2.0f);
        DivineType.PLASTIC.setEffectivenessFrom("flying", 0.5f);
        DivineType.PLASTIC.setEffectivenessFrom("psychic", 0.5f);
        DivineType.PLASTIC.setEffectivenessFrom("sound", 0.5f);
    }

    private static void initSlime() {
        DivineType.SLIME.setEffectivenessFrom("water", 2.0f);
        DivineType.SLIME.setEffectivenessFrom("grass", 2.0f);
        DivineType.SLIME.setEffectivenessFrom("fire", 0.5f);
        DivineType.SLIME.setEffectivenessFrom("electric", 0.5f);
        DivineType.SLIME.setEffectivenessFrom("ice", 0.5f);
        DivineType.SLIME.setEffectivenessFrom("fighting", 0.5f);
        DivineType.SLIME.setEffectivenessFrom("poison", 0.5f);
    }

    private static void initSound() {
        DivineType.SOUND.setEffectivenessFrom("normal", 2.0f);
        DivineType.SOUND.setEffectivenessFrom("fire", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("water", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("electric", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("grass", 2.0f);
        DivineType.SOUND.setEffectivenessFrom("ice", 2.0f);
        DivineType.SOUND.setEffectivenessFrom("fighting", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("poison", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("ground", 0.0f);
        DivineType.SOUND.setEffectivenessFrom("flying", 2.0f);
        DivineType.SOUND.setEffectivenessFrom("psychic", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("bug", 2.0f);
        DivineType.SOUND.setEffectivenessFrom("rock", 0.5f);
        DivineType.SOUND.setEffectivenessFrom("ghost", 0.5f);
    }

    private static void initWind() {
        DivineType.WIND.setEffectivenessFrom("fire", 2.0f);
        DivineType.WIND.setEffectivenessFrom("water", 2.0f);
        DivineType.WIND.setEffectivenessFrom("electric", 2.0f);
        DivineType.WIND.setEffectivenessFrom("grass", 0.5f);
        DivineType.WIND.setEffectivenessFrom("ice", 2.0f);
        DivineType.WIND.setEffectivenessFrom("fighting", 0.0f);
        DivineType.WIND.setEffectivenessFrom("poison", 0.5f);
        DivineType.WIND.setEffectivenessFrom("ground", 0.5f);
    }

    private static void initEldritch() {
        DivineType.ELDRITCH.setEffectivenessFrom("normal", 0.5f);
        DivineType.ELDRITCH.setEffectivenessFrom("fire", 2.0f);
        DivineType.ELDRITCH.setEffectivenessFrom("water", 2.0f);
        DivineType.ELDRITCH.setEffectivenessFrom("electric", 0.5f);
        DivineType.ELDRITCH.setEffectivenessFrom("grass", 0.5f);
        DivineType.ELDRITCH.setEffectivenessFrom("ice", 0.5f);
        DivineType.ELDRITCH.setEffectivenessFrom("fighting", 2.0f);
        DivineType.ELDRITCH.setEffectivenessFrom("poison", 0.5f);
        DivineType.ELDRITCH.setEffectivenessFrom("ground", 2.0f);
        DivineType.ELDRITCH.setEffectivenessFrom("flying", 2.0f);
        DivineType.ELDRITCH.setEffectivenessFrom("psychic", 0.5f);
    }

    private static void initBlood() {
        DivineType.BLOOD.setStatModifier("hp", 1.25);
        // Correção: removida efetividade contra "normal" conforme solicitado.
        // Valores restantes baseados na sua tabela original — ajuste se necessário.
        DivineType.BLOOD.setEffectivenessFrom("fire", 2.0f);
        DivineType.BLOOD.setEffectivenessFrom("water", 2.0f);
        DivineType.BLOOD.setEffectivenessFrom("electric", 0.5f);
        DivineType.BLOOD.setEffectivenessFrom("grass", 2.0f);
        DivineType.BLOOD.setEffectivenessFrom("ice", 0.5f);
        DivineType.BLOOD.setEffectivenessFrom("fighting", 2.0f);
        DivineType.BLOOD.setEffectivenessFrom("poison", 2.0f);
        DivineType.BLOOD.setEffectivenessFrom("ground", 0.5f);
    }
    public static DivineType fromName(String name) {
        if (name == null || name.isEmpty()) return null;
        for (DivineType type : DivineType.values()) {
            // Compara tanto com o nome do Enum (ex: "COSMIC") como com o display name (ex: "Cosmic")
            if (type.name().equalsIgnoreCase(name) || type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
