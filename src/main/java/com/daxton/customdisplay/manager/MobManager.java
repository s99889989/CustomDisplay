package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.api.mobs.MobData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobManager {

    private static Map<UUID, MobData> mob_Data_Map = new HashMap<>();

    public static Map<UUID, MobData> getMob_Data_Map() {
        return mob_Data_Map;
    }
}
