package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.api.action.Holographic;
import com.daxton.customdisplay.task.player.OnTimer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TriggerManager {

    private static Map<String, OnTimer> onTimerMap = new HashMap<>();

    private static Map<UUID, List<String>> onTimerNameMap = new HashMap<>();

    private static Map<UUID, Holographic> holographicMap = new HashMap<>();


    public static Map<String, OnTimer> getOnTimerMap() {
        return onTimerMap;
    }

    public static Map<UUID, List<String>> getOnTimerNameMap() {
        return onTimerNameMap;
    }

    public static Map<UUID, Holographic> getHolographicMap() {
        return holographicMap;
    }
}
