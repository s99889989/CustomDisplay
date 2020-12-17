package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.action.list.Holographic2;
import com.daxton.customdisplay.task.action.list.Loop2;

import java.util.HashMap;
import java.util.Map;

public class ActionManager2 {

    private static Map<String , Holographic2> judgment2_Holographic2_Map = new HashMap<>();
    private static Map<String , Loop2> judgment2_Loop2_Map = new HashMap<>();

    public static Map<String, Holographic2> getJudgment2_Holographic2_Map() {
        return judgment2_Holographic2_Map;
    }

    public static Map<String, Loop2> getJudgment2_Loop2_Map() {
        return judgment2_Loop2_Map;
    }
}
