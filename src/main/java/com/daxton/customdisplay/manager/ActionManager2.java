package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import com.daxton.customdisplay.task.action.list.*;

import java.util.HashMap;
import java.util.Map;

public class ActionManager2 {

    /**ProtocolManager**/
    public static ProtocolManager protocolManager;

    /**PlayerTrigger->judgment2**/
    private static Map<String , JudgmentAction2> playerTrigger_Judgment2_Map = new HashMap<>();

    /**judgment2->Action2**/
    private static Map<String , Action2> judgment2_Action2_Map = new HashMap<>();
    /**judgment2->Holographic2**/
    private static Map<String , Holographic2> judgment2_Holographic2_Map = new HashMap<>();
    /**judgment2->Loop2**/
    private static Map<String , Loop2> judgment2_Loop2_Map = new HashMap<>();
    /**judgment2->ActionBar2**/
    private static Map<String , ActionBar2> judgment2_ActionBar2_Map = new HashMap<>();
    /**judgment2->SendBossBar2**/
    private static Map<String , SendBossBar2> judgment2_SendBossBar2_Map = new HashMap<>();
    /**judgment2->SetName2**/
    private static Map<String , SetName2> judgment2_SetName2_Map = new HashMap<>();
    /**Judgment2->SendParticles**/
    private static Map<String ,SendParticles> judgment2_SendParticles_Map = new HashMap<>();

    /**action2->judgment2**/
    private static Map<String , JudgmentAction2> action2_Judgment2_Map = new HashMap<>();
    /**loop2->judgment2**/
    private static Map<String , JudgmentAction2> loop2_Judgment2_Map = new HashMap<>();

    /**SendParticles->ProtocolManager**/
    private static Map<String, ProtocolManager> sendParticles_ProtocolManager_Map = new HashMap<>();

    /**---------------------------------------------------------------------------------------------------------------**/

    /**PlayerTrigger->judgment2**/
    public static Map<String, JudgmentAction2> getPlayerTrigger_Judgment2_Map() {
        return playerTrigger_Judgment2_Map;
    }

    /**judgment2->Action2**/
    public static Map<String, Action2> getJudgment2_Action2_Map() {
        return judgment2_Action2_Map;
    }
    /**judgment2->Holographic2**/
    public static Map<String, Holographic2> getJudgment2_Holographic2_Map() {
        return judgment2_Holographic2_Map;
    }
    /**judgment2->Loop2**/
    public static Map<String, Loop2> getJudgment2_Loop2_Map() {
        return judgment2_Loop2_Map;
    }
    /**judgment2->ActionBar2**/
    public static Map<String, ActionBar2> getJudgment2_ActionBar2_Map() {
        return judgment2_ActionBar2_Map;
    }
    /**judgment2->SendBossBar2**/
    public static Map<String, SendBossBar2> getJudgment2_SendBossBar2_Map() {
        return judgment2_SendBossBar2_Map;
    }
    /**judgment2->SetName2**/
    public static Map<String, SetName2> getJudgment2_SetName2_Map() {
        return judgment2_SetName2_Map;
    }
    /**Judgment2->SendParticles**/
    public static Map<String, SendParticles> getJudgment2_SendParticles_Map() {
        return judgment2_SendParticles_Map;
    }

    /**action2->judgment2**/
    public static Map<String, JudgmentAction2> getAction2_Judgment2_Map() {
        return action2_Judgment2_Map;
    }
    /**loop2->judgment2**/
    public static Map<String, JudgmentAction2> getLoop2_Judgment2_Map() {
        return loop2_Judgment2_Map;
    }

    /**SendParticles->ProtocolManager**/
    public static Map<String, ProtocolManager> getSendParticles_ProtocolManager_Map() {
        return sendParticles_ProtocolManager_Map;
    }
}
