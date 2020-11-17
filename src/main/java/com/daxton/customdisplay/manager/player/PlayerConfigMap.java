package com.daxton.customdisplay.manager.player;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigMap {
    private static Map<String , FileConfiguration> stringStringMap = new HashMap<>();

    public static Map<String, FileConfiguration> getStringStringMap() {
        return stringStringMap;
    }
}
