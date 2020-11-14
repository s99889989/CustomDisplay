package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.actionbardisplay.PlayerActionBar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ABDMapManager {
    private static Map<UUID, PlayerActionBar> playerActionBarHashMap = new HashMap<>();

    public static Map<UUID, PlayerActionBar> getPlayerActionBarHashMap() {
        return playerActionBarHashMap;
    }
}
