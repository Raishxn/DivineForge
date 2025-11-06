package com.raishxn.divineforge.data;

import java.util.Map;

public class CustomAbility {
    public String id;
    public String name;
    public String description;

    public Map<String, Object> passive;
    public Map<String, Object> on_battle_start;
    public Map<String, Object> on_fire_move;
}