package com.daxton.customdisplay.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListenerManager {

    private static Map<String,Boolean> Cast_On_Stop = new HashMap<>();

    public static Map<String, Boolean> getCast_On_Stop() {
        return Cast_On_Stop;
    }
}
