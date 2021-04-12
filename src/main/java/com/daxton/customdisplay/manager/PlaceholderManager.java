package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlaceholderManager {

    /**AcionBar的function**/
    private static Map<String,Boolean> actionBar_function = new HashMap<>();

    /**Particles的function**/
    public static Map<String,String> particles_function = new HashMap<>();

    /**佔位符訊息**/
    private static Map<String,String> cd_Placeholder_Map = new HashMap<>();

    /**攻擊數字**/
    public static Map<String,String> cd_Attack_Number = new HashMap<>();





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





}
