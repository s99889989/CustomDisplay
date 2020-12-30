package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.player.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataMap {
    /**玩家動作資料**/
    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    /**玩家屬性資料**/
    private static Map<String,Integer> player_Attr_Stats = new HashMap<>();


    /**玩家動作資料**/
    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }
    /**玩家屬性資料**/
    public static Map<String, Integer> getPlayer_Attr_Stats() {
        return player_Attr_Stats;
    }
}
