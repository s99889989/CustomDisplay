package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class PermissionManager {

    private static Map<String,String> permission_String_Map = new HashMap<>();

    private static Map<String , FileConfiguration> permission_FileConfiguration_Map = new HashMap<>();

    public static Map<String, String> getPermission_String_Map() {
        return permission_String_Map;
    }

    public static Map<String, FileConfiguration> getPermission_FileConfiguration_Map() {
        return permission_FileConfiguration_Map;
    }
}
