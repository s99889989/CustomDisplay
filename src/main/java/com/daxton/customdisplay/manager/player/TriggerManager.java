package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TriggerManager {

    private static Map<UUID,OnTimer> onTimerMap = new HashMap<>();


    public static Map<UUID, OnTimer> getOnTimerMap() {
        return onTimerMap;
    }
}
