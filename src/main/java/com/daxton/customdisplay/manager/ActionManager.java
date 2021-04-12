package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.task.JudgmentAction;
import com.daxton.customdisplay.task.action.Loop2;
import com.daxton.customdisplay.task.action.location.Guise;
import com.daxton.customdisplay.task.action.location.Holographic2;
import com.daxton.customdisplay.task.action.orbital.LocGuise;
import com.daxton.customdisplay.task.action.orbital.LocationHolographic2;
import com.daxton.customdisplay.task.action.player.ActionBar2;
import com.daxton.customdisplay.task.action.player.OpenInventory2;
import com.daxton.customdisplay.task.action.player.SendBossBar;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    /**ProtocolManager**/
    public static ProtocolManager protocolManager;

    /**--------------------------------------------------------------**/

    public static Map<String, Loop2> judgment_Loop_Map = new HashMap<>();

    public static Map<String, ActionBar2> judgment_ActionBar_Map = new HashMap<>();

    public static Map<String, Holographic2> judgment_Holographic_Map = new HashMap<>();

    public static Map<String, SendBossBar> judgment_SendBossBar_Map = new HashMap<>();

    public static Map<String, OpenInventory2> judgment_Inventory_Map = new HashMap<>();

    public static Map<String, Guise> judgment_Guise_Map = new HashMap<>();

    /**短時間屬性地圖**/
    public static Map<String, Boolean> setAttribute_Boolean_Map = new HashMap<>();
    public static Map<String, BukkitRunnable> setAttribute_Run_Map = new HashMap<>();

    /**--------------------------------------------------------------**/

    public static Map<String, JudgmentAction> trigger_Judgment_Map = new HashMap<>();

    public static Map<String, JudgmentAction> loop_Judgment_Map = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, Condition2> action_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> loop_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> orbital_Condition_Map = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, String> playerUUID_taskID_Map = new HashMap<>();

    public static Map<String , Inventory> taskID_Inventory_Map = new HashMap<>();

    /**---------------------------------------------------------------**/

    public static Map<String, LocationHolographic2> judgment_LocHolographic_Map = new HashMap<>();

    public static Map<String, LocGuise> judgment_LocItemEntity_Map = new HashMap<>();

}
