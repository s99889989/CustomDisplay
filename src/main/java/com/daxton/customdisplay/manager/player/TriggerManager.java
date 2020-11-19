package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TriggerManager {

    private static Map<String,OnTimer> onTimerMap = new HashMap<>();


    public static Map<String, OnTimer> getOnTimerMap() {
        return onTimerMap;
    }
}
