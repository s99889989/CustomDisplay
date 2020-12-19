package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderManager {

    /**AcionBar的function**/
    private static Map<String,Boolean> actionBar_function = new HashMap<>();

    /**MMOcore插件的Spell-ActionBar顯示**/
    private static Map<UUID, String> mmocore_ActionBar_Spell_Map = new HashMap<>();
    /**MMOcore插件的Stats-ActionBar顯示**/
    private static Map<UUID , String> mmocore_ActionBar_Stats_Map = new HashMap<>();
    /**攻擊傷害**/
    private static Map<UUID , String> damage_Number_Map = new HashMap<>();
    /**被攻擊傷害**/
    private static Map<UUID , String> damaged_Number_Map = new HashMap<>();
    /**發送給玩家的ActionBarClass**/
    private static Map<String , String> action_Bar_Class_Map = new HashMap<>();
    /**MythicMobs插件的Mod_Level顯示**/
    private static Map<UUID,String> mythicMobs_Level_Map = new HashMap<>();

    /**------------------------------------------------------------------------------------------------------------**/

    /**AcionBar的Function**/
    public static Map<String, Boolean> getActionBar_function() {
        return actionBar_function;
    }

    /**MMOcore插件的Spell-ActionBar顯示**/
    public static Map<UUID, String> getMmocore_ActionBar_Spell_Map() {
        return mmocore_ActionBar_Spell_Map;
    }
    /**MMOcore插件的Stats-ActionBar顯示**/
    public static Map<UUID, String> getMmocore_ActionBar_Stats_Map() {
        return mmocore_ActionBar_Stats_Map;
    }
    /**攻擊傷害**/
    public static Map<UUID, String> getDamage_Number_Map() {
        return damage_Number_Map;
    }
    /**被攻擊傷害**/
    public static Map<UUID, String> getDamaged_Number_Map() {
        return damaged_Number_Map;
    }

    /**MythicMobs插件的Mod_Level顯示**/
    public static Map<UUID, String> getMythicMobs_Level_Map() {
        return mythicMobs_Level_Map;
    }
}
