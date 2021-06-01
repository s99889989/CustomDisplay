package com.daxton.customdisplay.task;

import com.daxton.customdisplay.api.entity.GuiseEntity;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;


import com.daxton.customdisplay.task.action.meta.run.FixedPoint3;
import com.daxton.customdisplay.task.action.meta.run.LocPng3;
import com.daxton.customdisplay.task.action.meta.run.Loop3;
import com.daxton.customdisplay.task.action.meta.run.OrbitalAction3;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;

import org.bukkit.entity.Entity;

public class ClearAction {


    public ClearAction(){


    }

    public static void all(){
        //***************************新********************************/

        //清除Loop
        for(Loop3 loop3 : ActionManager.judgment_Loop_Map2.values()){
            if(!loop3.isCancelled()){
                loop3.cancel();
            }
        }
        ActionManager.judgment_Loop_Map2.clear();
        //清除OrbitalAction
        for(OrbitalAction3 orbitalAction3 : ActionManager.judgment_OrbitalAction_Map2.values()){
            if(!orbitalAction3.isCancelled()){
                orbitalAction3.cancel();
            }
        }
        ActionManager.judgment_OrbitalAction_Map2.clear();
        //清除FixedPoint
        for(FixedPoint3 fixedPoint3 : ActionManager.judgment_FixedPoint_Map2.values()){
            if(!fixedPoint3.isCancelled()){
                fixedPoint3.cancel();
            }
        }
        ActionManager.judgment_FixedPoint_Map2.clear();
        //清除LocPng
        for(LocPng3 locPng3 : ActionManager.judgment_LocPng_Map2.values()){
            if(!locPng3.isCancelled()){
                locPng3.cancel();
            }
        }
        ActionManager.judgment_LocPng_Map2.clear();

        //清除Hologram
        ActionManager.hologram_Map.values().forEach(Hologram::delete);
        ActionManager.hologram_Map.clear();
        //清除BossBar
        ActionManager.bossBar_Map.values().forEach(BossBar::removeAll);
        ActionManager.bossBar_Map.clear();
        //清除GuiseEntity
        ActionManager.guise_Map.values().forEach(GuiseEntity::delete);
        ActionManager.guise_Map.clear();


        //**---------------------------------------------------------------**/

        ActionManager.action_Condition_Map.clear();

        ActionManager.loop_Condition_Map.clear();

        ActionManager.orbital_Condition_Map.clear();

        //**---------------------------------------------------------------**/

        ActionManager.taskID_Inventory_Map.clear();

        //**---------------------------------------------------------------**/

        //***************************舊********************************/

        //Particles的function
        if(!(PlaceholderManager.particles_function.isEmpty())){
            PlaceholderManager.particles_function.clear();
        }
    }

    public static void taskID(String taskID){
        //清除Loop
        if(ActionManager.judgment_Loop_Map2.get(taskID) != null){
            ActionManager.judgment_Loop_Map2.get(taskID).cancel();
            ActionManager.judgment_Loop_Map2.remove(taskID);
        }
        //清除OrbitalAction
        if(ActionManager.judgment_OrbitalAction_Map2.get(taskID) != null){
            ActionManager.judgment_OrbitalAction_Map2.get(taskID).cancel();
            ActionManager.judgment_OrbitalAction_Map2.remove(taskID);
        }
        //清除FixedPoint
        if(ActionManager.judgment_FixedPoint_Map2.get(taskID) != null){
            ActionManager.judgment_FixedPoint_Map2.get(taskID).cancel();
            ActionManager.judgment_FixedPoint_Map2.remove(taskID);
        }
        //清除LocPng
        if(ActionManager.judgment_LocPng_Map2.get(taskID) != null){
            ActionManager.judgment_LocPng_Map2.get(taskID).cancel();
            ActionManager.judgment_LocPng_Map2.remove(taskID);
        }
        //清除Hologram
        for(String s : ActionManager.hologram_Map.keySet()){
            if(s.contains(taskID)){
                ActionManager.hologram_Map.get(s).delete();
                ActionManager.hologram_Map.remove(s);
            }
        }

        //清除GuiseEntity
        for(String s : ActionManager.guise_Map.keySet()){
            if(s.contains(taskID)){
                ActionManager.guise_Map.get(s).delete();
                ActionManager.guise_Map.remove(s);
            }
        }
        //清除ModelEntity
        if (Bukkit.getServer().getPluginManager().getPlugin("ModelEngine") != null){
            for(String s : ActionManager.modelEngine_Map.keySet()){
                if(s.contains(taskID)){

                    ActionManager.modelEngine_Map.remove(s);
                    Entity entity = ActionManager.modelEngine_Entity_Map.get(s);
                    entity.remove();
                    ActionManager.modelEngine_Entity_Map.remove(s);
                    ActionManager.modelEngine_Modelid_Map.remove(s);
                    ActionManager.modelEngine_Stateid_Map.remove(s);
                    ActionManager.modelEngine_Location_Map.remove(s);

                }
            }
        }
        //清除BossBar
        for(String s : ActionManager.bossBar_Map.keySet()){
            if(s.contains(taskID)){
                ActionManager.bossBar_Map.get(s).removeAll();
                if(ActionManager.bossBar_Map.get(s) != null){
                    ActionManager.bossBar_Map.remove(s);
                }
            }
        }


    }

}
