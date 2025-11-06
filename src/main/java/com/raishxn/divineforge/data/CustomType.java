package com.raishxn.divineforge.data;

import java.util.List;
import java.util.Map;

public class CustomType {
    public String id;
    public String display_name;
    public String description;
    public boolean allowed_on_legendaries;
    public String replace_or_add;
    public int cooldown_days;
    public String rarity;
    public List<String> resistances;
    public List<String> weaknesses;
    public Map<String, Double> stat_modifiers;
    public List<CustomAbility> abilities;
    public List<CustomMove> moves;
}