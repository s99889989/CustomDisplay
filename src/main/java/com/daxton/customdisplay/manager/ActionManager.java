package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.action.JudgmentAction2;
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
    /**MMOcore插件的Spell-ActionBar顯示**/
    private static Map<UUID , String> mmocore_ActionBar_Spell_Map = new HashMap<>();
    /**MMOcore插件的Stats-ActionBar顯示**/
    private static Map<UUID , String> mmocore_ActionBar_Stats_Map = new HashMap<>();

    /**MythicMobs插件的Mod_Level顯示**/
    private static Map<UUID,String> mythicMobs_Level_Map = new HashMap<>();

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
    /**JudgmentAction->Name**/
    private static Map<String, SetName> judgment_SetName_Map = new HashMap<>();

    /**JudgmentAction2->Action2**/
    private static Map<String, Action2> judgment2_Action2_Map = new HashMap<>();
    /**Action2->JudgmentAction2**/
    private static Map<String, JudgmentAction2> action2_Judgment2_Map = new HashMap<>();

    /**ProtocolManager**/
    public static ProtocolManager protocolManager;




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
    /**MMOcore插件的Spell-ActionBar顯示**/
    public static Map<UUID, String> getMmocore_ActionBar_Spell_Map() {
        return mmocore_ActionBar_Spell_Map;
    }
    /**MMOcore插件的Stats-ActionBar顯示**/
    public static Map<UUID, String> getMmocore_ActionBar_Stats_Map() {
        return mmocore_ActionBar_Stats_Map;
    }

    /**MythicMobs插件的Mod_Level顯示**/
    public static Map<UUID, String> getMythicMobs_Level_Map() {
        return mythicMobs_Level_Map;
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
    /**JudgmentAction->Name**/
    public static Map<String, SetName> getJudgment_SetName_Map() {
        return judgment_SetName_Map;
    }


    /**JudgmentAction2->Action2**/
    public static Map<String, Action2> getJudgment2_Action2_Map() {
        return judgment2_Action2_Map;
    }
    /**Action2->JudgmentAction2**/
    public static Map<String, JudgmentAction2> getAction2_Judgment2_Map() {
        return action2_Judgment2_Map;
    }
}
