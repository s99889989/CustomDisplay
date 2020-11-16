package com.daxton.customdisplay.manager;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigMapManager {

    private static Map<String, FileConfiguration> fileConfigurationMap = new HashMap<>();

    private static Map<String, String> stringStringMap = new HashMap<>();

    public static Map<String, FileConfiguration> getFileConfigurationMap() {
        return fileConfigurationMap;
    }

    public static Map<String, String> getStringStringMap() {
        return stringStringMap;
    }
}
