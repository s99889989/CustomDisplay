package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.titledisply.JoinTitle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TDMapManager {
    private static Map<UUID, JoinTitle> joinTitleMap = new HashMap<>();

    public static Map<UUID, JoinTitle> getJoinTitleMap() {
        return joinTitleMap;
    }
}
