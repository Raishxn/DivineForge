package com.raishxn.divineforge.data;

import java.util.Map;

public class CustomAbility {
    public String id;
    public String name;
    public String description;

    // Mapeamento direto dos gatilhos usados no YAML
    // Usamos Map<String, Object> para flexibilidade dentro de cada gatilho
    public Map<String, Object> passive;
    public Map<String, Object> on_battle_start;
    public Map<String, Object> on_fire_move;
}