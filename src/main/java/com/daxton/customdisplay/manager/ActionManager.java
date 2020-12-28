package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActionManager {

    /**ProtocolManager**/
    public static ProtocolManager protocolManager;

    /**自訂背包**/
    private static Map<String , Inventory> inventory_Map = new HashMap<>();
    /**根據玩家UUID找尋目前使用的背包**/
    private static Map<UUID,  String> inventory_name_Map = new HashMap<>();

    /**judgment2->Action2**/
    private static Map<String , Action> judgment2_Action2_Map = new HashMap<>();
    /**judgment2->Holographic2**/
    private static Map<String , Holographic> judgment2_Holographic2_Map = new HashMap<>();
    /**judgment2->Loop2**/
    private static Map<String , Loop> judgment2_Loop2_Map = new HashMap<>();
    /**judgment2->ActionBar2**/
    private static Map<String , ActionBar> judgment2_ActionBar2_Map = new HashMap<>();
    /**judgment2->SendBossBar2**/
    private static Map<String , SendBossBar> judgment2_SendBossBar2_Map = new HashMap<>();
    /**judgment2->SetName2**/
    private static Map<String , SetName> judgment2_SetName2_Map = new HashMap<>();
    /**Judgment2->SendParticles**/
    private static Map<String ,SendParticles> judgment2_SendParticles_Map = new HashMap<>();
    /**Judgment2->OpenInventory**/
    private static Map<String, OpenInventory> judgment2_OpenInventory_Map = new HashMap<>();

    /**Other->judgment2**/
    private static Map<String , JudgmentAction> other_Judgment2_Map = new HashMap<>();

    /**SendParticles->ProtocolManager**/
    private static Map<String, ProtocolManager> sendParticles_ProtocolManager_Map = new HashMap<>();

    /**---------------------------------------------------------------------------------------------------------------**/

    /**自訂背包**/
    public static Map<String, Inventory> getInventory_Map() {
        return inventory_Map;
    }
    /**根據玩家UUID找尋目前使用的背包**/
    public static Map<UUID, String> getInventory_name_Map() {
        return inventory_name_Map;
    }

    /**judgment2->Action2**/
    public static Map<String, Action> getJudgment2_Action2_Map() {
        return judgment2_Action2_Map;
    }
    /**judgment2->Holographic2**/
    public static Map<String, Holographic> getJudgment2_Holographic2_Map() {
        return judgment2_Holographic2_Map;
    }
    /**judgment2->Loop2**/
    public static Map<String, Loop> getJudgment2_Loop2_Map() {
        return judgment2_Loop2_Map;
    }
    /**judgment2->ActionBar2**/
    public static Map<String, ActionBar> getJudgment2_ActionBar2_Map() {
        return judgment2_ActionBar2_Map;
    }
    /**judgment2->SendBossBar2**/
    public static Map<String, SendBossBar> getJudgment2_SendBossBar2_Map() {
        return judgment2_SendBossBar2_Map;
    }
    /**judgment2->SetName2**/
    public static Map<String, SetName> getJudgment2_SetName2_Map() {
        return judgment2_SetName2_Map;
    }
    /**Judgment2->SendParticles**/
    public static Map<String, SendParticles> getJudgment2_SendParticles_Map() {
        return judgment2_SendParticles_Map;
    }
    /**Judgment2->OpenInventory**/
    public static Map<String, OpenInventory> getJudgment2_OpenInventory_Map() {
        return judgment2_OpenInventory_Map;
    }

    /**Other->judgment2**/
    public static Map<String, JudgmentAction> getOther_Judgment2_Map() {
        return other_Judgment2_Map;
    }

    /**SendParticles->ProtocolManager**/
    public static Map<String, ProtocolManager> getSendParticles_ProtocolManager_Map() {
        return sendParticles_ProtocolManager_Map;
    }
}
