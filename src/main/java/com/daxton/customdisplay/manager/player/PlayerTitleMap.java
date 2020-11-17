package com.daxton.customdisplay.manager.player;

import com.daxton.customdisplay.task.titledisply.CustomTitle;

import java.util.HashMap;
import java.util.Map;

public class PlayerTitleMap {
    private Map<String, CustomTitle> customTitleMap = new HashMap<>();

    public Map<String, CustomTitle> getCustomTitleMap() {
        return customTitleMap;
    }

}
