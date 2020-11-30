package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.action.list.SendBossBar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BBDMapManager {
    public static Map<UUID, SendBossBar> attackBossBarMap = new HashMap<>();

    public static Map<UUID, UUID> targetAttackBossBarMap = new HashMap<>();

    public static Map<UUID, SendBossBar> getAttackBossBarMap() {
        return attackBossBarMap;
    }

    public static Map<UUID, UUID> getTargetAttackBossBarMap() {
        return targetAttackBossBarMap;
    }
}
