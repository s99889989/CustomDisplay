package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;

public class MobManager {

    /**用UUID儲存MMID**/
    private static Map<String,String> mobID_Map = new HashMap<>();

    public static Map<String, String> getMobID_Map() {
        return mobID_Map;
    }

}
