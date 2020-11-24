package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.api.action.JudgmentAction;
import com.daxton.customdisplay.api.action.list.Holographic;
import com.daxton.customdisplay.api.action.list.HolographicNew;
import com.daxton.customdisplay.task.player.OnTimer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TriggerManager {

    private static Map<String, OnTimer> onTimerMap = new HashMap<>();

    private static Map<UUID, List<String>> onTimerNameMap = new HashMap<>();

    private static Map<UUID, Holographic> holographicMap = new HashMap<>();

    private static Map<String, HolographicNew> holographicTaskMap = new HashMap<>();


    private static Map<String, JudgmentAction> loopTaskMap = new HashMap<>();

    public static Map<String, OnTimer> getOnTimerMap() {
        return onTimerMap;
    }

    public static Map<UUID, List<String>> getOnTimerNameMap() {
        return onTimerNameMap;
    }

    public static Map<UUID, Holographic> getHolographicMap() {
        return holographicMap;
    }

    public static Map<String, HolographicNew> getHolographicTaskMap() {
        return holographicTaskMap;
    }

    public static Map<String, JudgmentAction> getLoopTaskMap() {
        return loopTaskMap;
    }
}
