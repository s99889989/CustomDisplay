package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.player.AttributeEnum;
import com.daxton.customdisplay.api.player.CoreAttribute;
import com.daxton.customdisplay.api.player.PlayerData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerDataMap {
    /**玩家動作資料**/
    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    /**CustomCore屬性**/
    private static Map<String, CoreAttribute> core_Attribute_Map = new HashMap<>();

    /**攻擊速度計時**/
    public static Map<String, BukkitRunnable> attack_Speed_Map = new HashMap<>();
    public static Map<String, Integer> attack_Count_Map = new HashMap<>();

    /**技能綁定**/
    public static Map<String, List<String>> skill_Key_Map = new HashMap<>();

    /**事件的取消**/
    public static Map<String, Boolean> even_Cancel_Map = new HashMap<>();

    /**玩家動作資料**/
    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    /**CustomCore屬性**/
    public static Map<String, CoreAttribute> getCore_Attribute_Map() {
        return core_Attribute_Map;
    }





}
