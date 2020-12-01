package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.player.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataMap {

    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }
}
