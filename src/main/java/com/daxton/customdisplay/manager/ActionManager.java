package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionManager {



    /**玩家動作Map**/
    private static Map<String, JudgmentAction> playerActionTaskMap = new HashMap<>();

    /**目標紀錄攻擊者**/
    public static Map<UUID, Player> target_getPlayer_Map = new HashMap<>();
    /**Particles**/
    public static Map<String ,SendParticles> particles_Map = new HashMap<>();

    /**JudgmentAction->HolographicNew**/
    private static Map<String, Holographic> judgment_Holographic_Map = new HashMap<>();
    /**JudgmentAction->Loop**/
    private static Map<String, Loop> judgment_Loop_Map = new HashMap<>();

    /**JudgmentAction->BossBar**/
    private static Map<String, SendBossBar> judgment_BossBar_Map = new HashMap<>();
    /**BossBar**/
    private static Map<String, BossBar> BossBar_Map = new HashMap<>();
    /**Loop->JudgmentAction**/
    private static Map<String, JudgmentAction> loop_Judgment_Map = new HashMap<>();
    /**Action->JudgmentAction**/
    private static Map<String, JudgmentAction> action_Judgment_Map = new HashMap<>();
    /**JudgmentAction->Action**/
    private static Map<String, Action> judgment_Action_Map = new HashMap<>();
    /**JudgmentAction->Message**/
    private static Map<String, Message> judgment_Message_Map = new HashMap<>();







    /**玩家動作Map**/
    public static Map<String, JudgmentAction> getPlayerActionTaskMap() {
        return playerActionTaskMap;
    }

    /**目標紀錄攻擊者的UUID**/
    public static Map<UUID, Player> getTarget_getPlayer_Map() {
        return target_getPlayer_Map;
    }

    public static Map<String, SendParticles> getParticles_Map() {
        return particles_Map;
    }

    /**JudgmentAction->HolographicNew**/
    public static Map<String, Holographic> getJudgment_Holographic_Map() {
        return judgment_Holographic_Map;
    }
    /**Loop->JudgmentAction**/
    public static Map<String, JudgmentAction> getLoop_Judgment_Map() {
        return loop_Judgment_Map;
    }
    /**JudgmentAction->Loop**/
    public static Map<String, Loop> getJudgment_Loop_Map() {
        return judgment_Loop_Map;
    }
    /**JudgmentAction->BossBar**/
    public static Map<String, SendBossBar> getJudgment_BossBar_Map() {
        return judgment_BossBar_Map;
    }
    /**BossBar**/
    public static Map<String, BossBar> getBossBar_Map() {
        return BossBar_Map;
    }
    /**Action->JudgmentAction**/
    public static Map<String, JudgmentAction> getAction_Judgment_Map() {
        return action_Judgment_Map;
    }
    /**JudgmentAction->Action**/
    public static Map<String, Action> getJudgment_Action_Map() {
        return judgment_Action_Map;
    }
    /**JudgmentAction->Message**/
    public static Map<String, Message> getJudgment_Message_Map() {
        return judgment_Message_Map;
    }
}
