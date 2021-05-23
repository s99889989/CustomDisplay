package com.daxton.customdisplay.manager.player;


import com.daxton.customdisplay.api.MobData;
import com.daxton.customdisplay.api.player.CoreAttribute;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.api.player.profession.BossBarSkill2;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    //玩家資料
    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    public static Map<String, PlayerData2> player_Data_Map = new HashMap<>();

    /**目前魔量**/
    public static Map<String, Double> player_nowMana = new HashMap<>();

    /**CustomCore屬性**/
    private static Map<String, CoreAttribute> core_Attribute_Map = new HashMap<>();

    /**CustomCore公式**/
    public static Map<String, String> core_Formula_Map = new HashMap<>();
    /**CustomCore布林**/
    public static Map<String, Boolean> core_Boolean_Map = new HashMap<>();

    //盾牌延遲
    public static Map<String, Boolean> shield_Delay_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> shield_Delay_Run_Map = new HashMap<>();

    //物品CD
    public static Map<String, Boolean> item_Delay_Right_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> item_Delay_Right_Run_Map = new HashMap<>();
    public static Map<String, Boolean> item_Delay_Left_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> item_Delay_Left_Run_Map = new HashMap<>();

    /**攻擊速度計時**/
    public static Map<String, BukkitRunnable> attack_Speed_Map = new HashMap<>();
    public static Map<String, Integer> attack_Count_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean2_Map = new HashMap<>();
    public static Map<String, Boolean> attack_Boolean3_Map = new HashMap<>();
    /**多次攻擊時**/
    public static Map<String, Boolean> attack_Boolean4_Map = new HashMap<>();

    /**施法時間**/
    public static Map<String, BukkitRunnable> cost_Time_Map = new HashMap<>();
    public static Map<String, Integer> cost_Count_Map = new HashMap<>();
    /**施法延遲時間**/
    public static Map<String, Boolean> cost_Delay_Boolean_Map = new HashMap<>();
    /**技能CD時間**/
    public static Map<String, BukkitRunnable> skill_Cool_Down_Run_Map = new HashMap<>();
    public static Map<String, Boolean> skill_Cool_Down_Boolean_Map = new HashMap<>();

    /**技能綁定**/
    public static Map<String, List<String>> skill_Key_Map = new HashMap<>();
    public static Map<String, String> skill_Name_Map = new HashMap<>();
    /**事件的取消**/
    public static Map<String, Boolean> even_Cancel_Map = new ConcurrentHashMap<>();

    /**玩家技能狀態攔**/
    public static Map<String, BossBarSkill2> keyF_BossBarSkill_Map = new HashMap<>();

    /**玩家動作資料**/
    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }


    /**CustomCore屬性**/
    public static Map<String, CoreAttribute> getCore_Attribute_Map() {
        return core_Attribute_Map;
    }



}
