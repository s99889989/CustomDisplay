package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.action2.Loop2;
import com.daxton.customdisplay.task.action2.location.Guise;
import com.daxton.customdisplay.task.action2.location.Holographic2;
import com.daxton.customdisplay.task.action2.orbital.LocGuise;
import com.daxton.customdisplay.task.action2.orbital.LocationHolographic2;
import com.daxton.customdisplay.task.action2.player.ActionBar2;
import com.daxton.customdisplay.task.action2.player.OpenInventory2;
import com.daxton.customdisplay.task.action2.player.SendBossBar2;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class ActionManager2 {

    public static Map<String, Loop2> judgment_Loop_Map = new HashMap<>();

    public static Map<String, ActionBar2> judgment_ActionBar_Map = new HashMap<>();

    public static Map<String, Holographic2> judgment_Holographic_Map = new HashMap<>();

    public static Map<String, SendBossBar2> judgment_SendBossBar_Map = new HashMap<>();

    public static Map<String, OpenInventory2> judgment_Inventory_Map = new HashMap<>();

    public static Map<String, Guise> judgment_Guise_Map = new HashMap<>();

    /**--------------------------------------------------------------**/

    public static Map<String, JudgmentAction2> trigger_Judgment_Map = new HashMap<>();

    public static Map<String, JudgmentAction2> loop_Judgment_Map = new HashMap<>();

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
