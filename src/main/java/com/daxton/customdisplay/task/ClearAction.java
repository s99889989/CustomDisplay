package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;

import com.daxton.customdisplay.task.action.Loop2;
import com.daxton.customdisplay.task.action.location.Guise;
import com.daxton.customdisplay.task.action.location.Holographic2;
import com.daxton.customdisplay.task.action.orbital.LocGuise;
import com.daxton.customdisplay.task.action.orbital.LocationHolographic2;
import com.daxton.customdisplay.task.action.player.SendBossBar;

public class ClearAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction(){

        /***************************新********************************/


        for(Loop2 loop2 : ActionManager.judgment_Loop_Map.values()){
            if(!loop2.isCancelled()){
                loop2.cancel();
            }
        }
        ActionManager.judgment_Loop_Map.clear();

        ActionManager.judgment_ActionBar_Map.clear();

        for(Holographic2 holographic2 : ActionManager.judgment_Holographic_Map.values()){
            if(holographic2.getHologram() != null){
                holographic2.getHologram().delete();
            }
        }
        ActionManager.judgment_Holographic_Map.clear();

        for(SendBossBar bossBar2 : ActionManager.judgment_SendBossBar_Map.values()){
            if(bossBar2.getBossBar() != null){
                bossBar2.getBossBar().removeAll();
                cd.getLogger().info("清除");
            }
        }
        ActionManager.judgment_SendBossBar_Map.clear();

        ActionManager.judgment_Inventory_Map.clear();

        for(Guise guise : ActionManager.judgment_Guise_Map.values()){
            if(guise.getPacketEntity() != null){
                guise.getPacketEntity().delete();
            }
        }
        ActionManager.judgment_Guise_Map.clear();


        /**---------------------------------------------------------------**/

        ActionManager.trigger_Judgment_Map.clear();

        ActionManager.loop_Judgment_Map.clear();

        /**---------------------------------------------------------------**/

        ActionManager.action_Condition_Map.clear();

        ActionManager.loop_Condition_Map.clear();

        ActionManager.orbital_Condition_Map.clear();

        /**---------------------------------------------------------------**/

        ActionManager.playerUUID_taskID_Map.clear();

        ActionManager.taskID_Inventory_Map.clear();

        /**---------------------------------------------------------------**/

        for(LocationHolographic2 holographic2 : ActionManager.judgment_LocHolographic_Map.values()){
            if(holographic2.getHologram() != null){
                holographic2.getHologram().delete();
            }
        }
        ActionManager.judgment_LocHolographic_Map.clear();

        for(LocGuise locGuise : ActionManager.judgment_LocItemEntity_Map.values()){
            if(locGuise.getPacketEntity() != null){
                locGuise.getPacketEntity().delete();
            }
        }
        ActionManager.judgment_LocItemEntity_Map.clear();


        /***************************舊********************************/







        if(!(PlaceholderManager.particles_function.isEmpty())){
            PlaceholderManager.particles_function.clear();
        }


    }


    public void taskID(String taskID){

        if(ActionManager.judgment_Loop_Map.get(taskID) != null){
            if(!ActionManager.judgment_Loop_Map.get(taskID).isCancelled()){
                ActionManager.judgment_Loop_Map.get(taskID).cancel();
            }
            ActionManager.judgment_Loop_Map.remove(taskID);
        }

        if(ActionManager.judgment_Holographic_Map.get(taskID) != null){
            if(ActionManager.judgment_Holographic_Map.get(taskID).getHologram() != null){
                ActionManager.judgment_Holographic_Map.get(taskID).getHologram().delete();
            }
            ActionManager.judgment_Holographic_Map.remove(taskID);
        }


        if(ActionManager.judgment_SendBossBar_Map.get(taskID) != null){
            if(ActionManager.judgment_SendBossBar_Map.get(taskID).getBossBar() != null){
                ActionManager.judgment_SendBossBar_Map.get(taskID).getBossBar().removeAll();
                //cd.getLogger().info("刪除BossBar");
            }
            ActionManager.judgment_SendBossBar_Map.remove(taskID);
        }

        /**--------------------------------------------------------------**/

        if(ActionManager.trigger_Judgment_Map.get(taskID) != null){
            ActionManager.trigger_Judgment_Map.remove(taskID);
        }

        if(ActionManager.loop_Judgment_Map.get(taskID) != null){
            ActionManager.loop_Judgment_Map.remove(taskID);
        }

    }


}
