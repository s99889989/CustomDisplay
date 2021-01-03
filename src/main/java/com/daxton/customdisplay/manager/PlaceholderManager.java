package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaceholderManager {

    /**AcionBar的function**/
    private static Map<String,Boolean> actionBar_function = new HashMap<>();

    /**Particles的function**/
    private static Map<String,String> particles_function = new HashMap<>();

    /**佔位符訊息**/
    private static Map<String,String> cd_Placeholder_Map = new HashMap<>();

    /**MythicMobs插件的Mod_Level顯示**/
    private static Map<UUID,String> mythicMobs_Level_Map = new HashMap<>();

    /**MythicMobs的屬性**/
    private static Map<String,String> mythicMobs_Attr_Map = new HashMap<>();

    /**------------------------------------------------------------------------------------------------------------**/

    /**AcionBar的Function**/
    public static Map<String, Boolean> getActionBar_function() {
        return actionBar_function;
    }
    /**Particles的function**/
    public static Map<String, String> getParticles_function() {
        return particles_function;
    }

    /**佔位符訊息**/
    public static Map<String, String> getCd_Placeholder_Map() {
        return cd_Placeholder_Map;
    }

    /**MythicMobs插件的Mod_Level顯示**/
    public static Map<UUID, String> getMythicMobs_Level_Map() {
        return mythicMobs_Level_Map;
    }

    /**MythicMobs的屬性**/
    public static Map<String, String> getMythicMobs_Attr_Map() {
        return mythicMobs_Attr_Map;
    }
}
