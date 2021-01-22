package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.player.CoreAttribute;
import com.daxton.customdisplay.api.player.data.PlayerData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataMap {
    /**玩家動作資料**/
    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    /**CustomCore屬性**/
    private static Map<String, CoreAttribute> core_Attribute_Map = new HashMap<>();

    /**CustomCore公式**/
    public static Map<String, String> core_Formula_Map = new HashMap<>();
    /**CustomCore布林**/
    public static Map<String, Boolean> core_Boolean_Map = new HashMap<>();

    /**攻擊速度計時**/
    public static Map<String, BukkitRunnable> attack_Speed_Map = new HashMap<>();
    public static Map<String, Integer> attack_Count_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean2_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean3_Map = new HashMap<>();

    /**施法時間**/
    public static Map<String, BukkitRunnable> cost_Time_Map = new HashMap<>();
    public static Map<String, Integer> cost_Count_Map = new HashMap<>();
    /**施法延遲時間**/
    public static Map<String, BukkitRunnable> cost_Delay_Time_Map = new HashMap<>();
    public static Map<String, Integer> cost_Delay_Count_Map = new HashMap<>();
    public static Map<String, Boolean> cost_Delay_Boolean_Map = new HashMap<>();
    /**技能CD時間**/
    public static Map<String, BukkitRunnable> skill_Delay_Time_Map = new HashMap<>();
    public static Map<String, Boolean> skill_Delay_Boolean_Map = new HashMap<>();

    /**技能綁定**/
    public static Map<String, List<String>> skill_Key_Map = new HashMap<>();
    /**事件的取消**/
    public static Map<String, Boolean> even_Cancel_Map = new ConcurrentHashMap<>();

    /**玩家動作資料**/
    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }


    /**CustomCore屬性**/
    public static Map<String, CoreAttribute> getCore_Attribute_Map() {
        return core_Attribute_Map;
    }





}
