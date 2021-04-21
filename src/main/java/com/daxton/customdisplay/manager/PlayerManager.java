package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.item.gui.*;
import com.daxton.customdisplay.api.player.CoreAttribute;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.profession.BossBarSkill2;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    /**玩家動作資料**/
    private static Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    /**目前魔量**/
    public static Map<String, Double> player_nowMana = new HashMap<>();

    /**CustomCore屬性**/
    private static Map<String, CoreAttribute> core_Attribute_Map = new HashMap<>();

    /**CustomCore公式**/
    public static Map<String, String> core_Formula_Map = new HashMap<>();
    /**CustomCore布林**/
    public static Map<String, Boolean> core_Boolean_Map = new HashMap<>();

    /**盾牌延遲**/
    public static Map<String, Boolean> shield_Delay_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> shield_Delay_Run_Map = new HashMap<>();

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

    //物品主Menu
    public static Map<String , Inventory> menu_ItemCategorySelection_Inventory_Map = new HashMap<>();
    public static Map<String , ItemCategorySelection> menu_ItemCategorySelection_Map = new HashMap<>();

    //物品分列表
    public static Map<String , Inventory> menu_SelectItems_Inventory_Map = new HashMap<>();
    public static Map<String , SelectItems> menu_SelectItems_Map = new HashMap<>();
    public static Map<String , Boolean> menu_SelectItems_Chat_Map = new HashMap<>();
    //編輯物品
    public static Map<String , Inventory> menu_EditItem_Inventory_Map = new HashMap<>();
    public static Map<String , EditItem> menu_EditItem_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditItem_Chat_Map = new HashMap<>();
    //編輯物品附魔
    public static Map<String , Inventory> menu_EditEnchantment_Inventory_Map = new HashMap<>();
    public static Map<String , EditEnchantment> menu_EditEnchantment_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditEnchantment_Chat_Map = new HashMap<>();
    //編輯物品屬性
    public static Map<String , Inventory> menu_EditAttributes_Inventory_Map = new HashMap<>();
    public static Map<String , EditAttributes> menu_EditAttributes_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditAttributes_Chat_Map = new HashMap<>();
    //物品材質清單
    public static Map<String , Inventory> menu_ItemList_Inventory_Map = new HashMap<>();
    public static Map<String , ItemList> menu_ItemList_Map = new HashMap<>();
    //編輯物品Flag
    public static Map<String , Inventory> menu_EditFlags_Inventory_Map = new HashMap<>();
    public static Map<String , EditFlags> menu_EditFlags_Map = new HashMap<>();
    //編輯物品Lore
    public static Map<String , Inventory> menu_EditLore_Inventory_Map = new HashMap<>();
    public static Map<String , EditLore> menu_EditLore_Map = new HashMap<>();
    public static Map<String , Boolean> menu_EditLore_Chat_Map = new HashMap<>();


    /**玩家動作資料**/
    public static Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }


    /**CustomCore屬性**/
    public static Map<String, CoreAttribute> getCore_Attribute_Map() {
        return core_Attribute_Map;
    }



}
