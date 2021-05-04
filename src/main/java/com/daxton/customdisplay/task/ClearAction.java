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
import com.daxton.customdisplay.task.action2.meta.Loop3;
import com.daxton.customdisplay.task.action2.location.Guise3;
import com.daxton.customdisplay.task.action2.location.Holographic3;
import com.daxton.customdisplay.task.action2.orbital.LocGuise3;
import com.daxton.customdisplay.task.action2.orbital.LocationHolographic3;
import com.daxton.customdisplay.task.action2.player.SendBossBar3;

public class ClearAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction(){




    }

    public void all2(){
        /***************************新********************************/


        for(Loop3 loop3 : ActionManager.judgment_Loop_Map2.values()){
            if(!loop3.isCancelled()){
                loop3.cancel();
            }
        }
        ActionManager.judgment_Loop_Map2.clear();

        for(Holographic3 holographic2 : ActionManager.judgment_Holographic_Map2.values()){
            if(holographic2.getHologram_Map() != null){
                holographic2.getHologram_Map().forEach((s, hologram) -> {
                    hologram.delete();
                });
            }
        }
        ActionManager.judgment_Holographic_Map2.clear();

        for(SendBossBar3 bossBar2 : ActionManager.judgment_SendBossBar_Map2.values()){
            if(bossBar2.getBossBar() != null){
                bossBar2.getBossBar().removeAll();

            }
        }
        ActionManager.judgment_SendBossBar_Map2.clear();

        ActionManager.judgment_Inventory_Map2.clear();

        for(Guise3 guise : ActionManager.judgment_Guise_Map2.values()){
            if(guise.getPacketEntity() != null){
                guise.getPacketEntity().delete();
            }
        }
        ActionManager.judgment_Guise_Map2.clear();


        /**---------------------------------------------------------------**/

        ActionManager.trigger_Judgment_Map2.clear();

        ActionManager.loop_Judgment_Map2.clear();

        /**---------------------------------------------------------------**/

        ActionManager.action_Condition_Map.clear();

        ActionManager.loop_Condition_Map.clear();

        ActionManager.orbital_Condition_Map.clear();

        /**---------------------------------------------------------------**/

        ActionManager.playerUUID_taskID_Map.clear();

        ActionManager.taskID_Inventory_Map.clear();

        /**---------------------------------------------------------------**/

        for(LocationHolographic3 holographic2 : ActionManager.judgment_LocHolographic_Map2.values()){
            if(holographic2.getHologram_Map() != null){
                holographic2.getHologram_Map().forEach((s, hologram) -> {
                    hologram.delete();
                });
            }
        }
        ActionManager.judgment_LocHolographic_Map2.clear();

        for(LocGuise3 locGuise : ActionManager.judgment_LocItemEntity_Map2.values()){
            if(locGuise.getPacketEntity() != null){
                locGuise.getPacketEntity().delete();
            }
        }
        ActionManager.judgment_LocItemEntity_Map2.clear();


        /***************************舊********************************/


        if(!(PlaceholderManager.particles_function.isEmpty())){
            PlaceholderManager.particles_function.clear();
        }
    }

    public void all(){
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

    public void taskID2(String taskID){
        if(ActionManager.judgment_Loop_Map2.get(taskID) != null){
            if(!ActionManager.judgment_Loop_Map2.get(taskID).isCancelled()){

                ActionManager.judgment_Loop_Map2.get(taskID).cancel();
            }
            ActionManager.judgment_Loop_Map2.remove(taskID);
        }

        if(ActionManager.judgment_Holographic_Map2.get(taskID) != null){
            if(ActionManager.judgment_Holographic_Map2.get(taskID).getHologram_Map() != null){
                ActionManager.judgment_Holographic_Map2.get(taskID).getHologram_Map().forEach((s, hologram) -> {
                    hologram.delete();
                });
            }
            ActionManager.judgment_Holographic_Map2.remove(taskID);
        }

        if(ActionManager.judgment_Guise_Map2.get(taskID) != null){
            if(ActionManager.judgment_Guise_Map2.get(taskID).getPacketEntity() != null){
                ActionManager.judgment_Guise_Map2.get(taskID).getPacketEntity().delete();
                ActionManager.judgment_Guise_Map2.get(taskID).setPacketEntity(null);
            }
            ActionManager.judgment_Guise_Map2.remove(taskID);
        }

        if(ActionManager.judgment_SendBossBar_Map2.get(taskID) != null){
            if(ActionManager.judgment_SendBossBar_Map2.get(taskID).getBossBar() != null){
                ActionManager.judgment_SendBossBar_Map2.get(taskID).getBossBar().removeAll();
                ActionManager.judgment_SendBossBar_Map2.get(taskID).setBossBar(null);
            }
            ActionManager.judgment_SendBossBar_Map2.remove(taskID);
        }

        /**--------------------------------------------------------------**/

        if(ActionManager.trigger_Judgment_Map2.get(taskID) != null){
            ActionManager.trigger_Judgment_Map2.remove(taskID);
        }

        if(ActionManager.loop_Judgment_Map2.get(taskID) != null){
            ActionManager.loop_Judgment_Map2.remove(taskID);
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

        if(ActionManager.judgment_Guise_Map.get(taskID) != null){
            if(ActionManager.judgment_Guise_Map.get(taskID).getPacketEntity() != null){
                ActionManager.judgment_Guise_Map.get(taskID).getPacketEntity().delete();
                ActionManager.judgment_Guise_Map.get(taskID).setPacketEntity(null);
            }
            ActionManager.judgment_Guise_Map.remove(taskID);
        }

        if(ActionManager.judgment_SendBossBar_Map.get(taskID) != null){
            if(ActionManager.judgment_SendBossBar_Map.get(taskID).getBossBar() != null){
                ActionManager.judgment_SendBossBar_Map.get(taskID).getBossBar().removeAll();
                ActionManager.judgment_SendBossBar_Map.get(taskID).setBossBar(null);
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
