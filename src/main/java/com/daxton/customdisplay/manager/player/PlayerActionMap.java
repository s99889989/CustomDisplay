package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.task.player.ActionDisplay;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerActionMap {
    private static Map<String , FileConfiguration> stringStringMap = new HashMap<>();

    private static Map<UUID, ActionDisplay> actionDisplayMap = new HashMap<>();

    public static Map<String, FileConfiguration> getStringStringMap() {
        return stringStringMap;
    }

    public static Map<UUID, ActionDisplay> getActionDisplayMap() {
        return actionDisplayMap;
    }
}
