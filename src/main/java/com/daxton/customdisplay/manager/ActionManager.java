package com.daxton.customdisplay.manager;

import com.comphenix.protocol.ProtocolManager;
import com.daxton.customdisplay.api.entity.GuiseEntity;
import com.daxton.customdisplay.task.action.meta.run.FixedPoint3;
import com.daxton.customdisplay.task.action.meta.run.LocPng3;
import com.daxton.customdisplay.task.action.meta.run.Loop3;
import com.daxton.customdisplay.task.action.meta.run.OrbitalAction3;
import com.daxton.customdisplay.task.condition.Condition2;
import com.daxton.customdisplay.task.condition2.HealthChange2;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionManager {

    //**ProtocolManager**/
    public static ProtocolManager protocolManager;

    public static Map<String, List<Map<String, String>>> class_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> permission_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> item_Action_Map = new HashMap<>();

    public static Map<String, List<Map<String, String>>> action_SubAction_Map = new HashMap<>();

    //**--------------------------------------------------------------**/
    //Loop
    public static Map<String, Loop3> judgment_Loop_Map2 = new HashMap<>();
    //OrbitalAction
    public static Map<String, OrbitalAction3> judgment_OrbitalAction_Map2 = new HashMap<>();
    //FixedPoint
    public static Map<String, FixedPoint3> judgment_FixedPoint_Map2 = new HashMap<>();
    //LocPng
    public static Map<String, LocPng3> judgment_LocPng_Map2 = new HashMap<>();

    //Hologram
    public static Map<String, Hologram> hologram_Map = new HashMap<>();
    //GuiseEntity
    public static Map<String, GuiseEntity> guise_Map = new HashMap<>();
    //ModelEntity
    public static Map<String, ModeledEntity> modelEngine_Map = new HashMap<>();
    public static Map<String, Entity> modelEngine_Entity_Map = new HashMap<>();
    public static Map<String, String> modelEngine_Modelid_Map = new HashMap<>();
    public static Map<String, String> modelEngine_Stateid_Map = new HashMap<>();
    public static Map<String, Location> modelEngine_Location_Map = new HashMap<>();
    //BossBar
    public static Map<String, BossBar> bossBar_Map = new HashMap<>();
    //Inventroy
    public static Map<String , Inventory> taskID_Inventory_Map = new HashMap<>();
    //短時間屬性地圖
    public static Map<String, BukkitRunnable> setAttribute_Run_Map = new HashMap<>();

    //**---------------------------------------------------------------**/

    public static Map<String, Condition2> action_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> loop_Condition_Map = new HashMap<>();

    public static Map<String, Condition2> orbital_Condition_Map = new HashMap<>();

    //**---------------------------------------------------------------**/

    //Condition->Health
    public static Map<String, HealthChange2> condition_HealthChange_Map = new HashMap<>();

}
