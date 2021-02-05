package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;

public class MobManager {

    /**用UUID字串儲存MMID**/
    public static Map<String,String> mythicMobs_mobID_Map = new HashMap<>();

    /**用UUID字串儲存MMID**/
    public static Map<String,String> mythicMobs_Level_Map = new HashMap<>();

    /**用UUID字串+屬性名稱，儲存值**/
    public static Map<String,String> mythicMobs_Attr_Map = new HashMap<>();

    /**ModelEngine資料**/
    public static Map<String,String> modelengine_Map = new HashMap<>();

}
