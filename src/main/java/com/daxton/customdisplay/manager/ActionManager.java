package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.task.JudgmentAction;
import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.action.Loop2;
import com.daxton.customdisplay.task.action.location.Guise;
import com.daxton.customdisplay.task.action.location.Holographic2;
import com.daxton.customdisplay.task.action.orbital.LocGuise;
import com.daxton.customdisplay.task.action.orbital.LocationHolographic2;
import com.daxton.customdisplay.task.action.player.ActionBar2;
import com.daxton.customdisplay.task.action.player.OpenInventory2;
import com.daxton.customdisplay.task.action.player.SendBossBar;
import com.daxton.customdisplay.task.action2.location.CDModelEngine;
import com.daxton.customdisplay.task.action2.location.Guise3;
import com.daxton.customdisplay.task.action2.location.Holographic3;
import com.daxton.customdisplay.task.action2.meta.Loop3;
import com.daxton.customdisplay.task.action2.orbital.LocGuise3;
import com.daxton.customdisplay.task.action2.orbital.LocModelEngine;
import com.daxton.customdisplay.task.action2.orbital.LocationHolographic3;
import com.daxton.customdisplay.task.action2.player.OpenInventory3;
import com.daxton.customdisplay.task.action2.player.SendBossBar3;
import com.daxton.customdisplay.task.condition.Condition2;
import com.daxton.customdisplay.task.condition.list.Health;
import com.daxton.customdisplay.task.condition2.HealthChange2;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionManager {

    /**ProtocolManager**/
    public static ProtocolManager protocolManager;

    public static Map<String, List<Map<String, String>>> class_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> permission_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> item_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> action_SubAction_Map = new HashMap<>();

    /**--------------------------------------------------------------**/

    public static Map<String, Loop2> judgment_Loop_Map = new HashMap<>();

    public static Map<String, Loop3> judgment_Loop_Map2 = new HashMap<>();

    public static Map<String, ActionBar2> judgment_ActionBar_Map = new HashMap<>();

    public static Map<String, Holographic2> judgment_Holographic_Map = new HashMap<>();

    public static Map<String, Holographic3> judgment_Holographic_Map2 = new HashMap<>();

    public static Map<String, SendBossBar> judgment_SendBossBar_Map = new HashMap<>();

    public static Map<String, SendBossBar3> judgment_SendBossBar_Map2 = new HashMap<>();

    public static Map<String, OpenInventory2> judgment_Inventory_Map = new HashMap<>();

    public static Map<String, OpenInventory3> judgment_Inventory_Map2 = new HashMap<>();

    public static Map<String, Guise> judgment_Guise_Map = new HashMap<>();

    public static Map<String, Guise3> judgment_Guise_Map2 = new HashMap<>();

    public static Map<String, CDModelEngine> judgment_ModelEngine_Map = new HashMap<>();

    public static Map<String, LocModelEngine> judgment_LocModelEngine_Map = new HashMap<>();

    /**短時間屬性地圖**/
    public static Map<String, Boolean> setAttribute_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> setAttribute_Run_Map = new HashMap<>();

    /**--------------------------------------------------------------**/

    public static Map<String, JudgmentAction> trigger_Judgment_Map = new HashMap<>();

    public static Map<String, JudgmentAction> loop_Judgment_Map = new HashMap<>();

    public static Map<String, JudgmentAction2> trigger_Judgment_Map2 = new HashMap<>();

    public static Map<String, JudgmentAction2> loop_Judgment_Map2 = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, Condition2> action_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> loop_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> orbital_Condition_Map = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, String> playerUUID_taskID_Map = new HashMap<>();

    public static Map<String , Inventory> taskID_Inventory_Map = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, LocationHolographic2> judgment_LocHolographic_Map = new HashMap<>();

    public static Map<String, LocationHolographic3> judgment_LocHolographic_Map2 = new HashMap<>();

    public static Map<String, LocGuise> judgment_LocItemEntity_Map = new HashMap<>();

    public static Map<String, LocGuise3> judgment_LocItemEntity_Map2 = new HashMap<>();

    /**---------------------------------------------------------------**/

    /**Condition->Health**/
    public static Map<String, HealthChange2> condition_HealthChange_Map = new HashMap<>();

}
