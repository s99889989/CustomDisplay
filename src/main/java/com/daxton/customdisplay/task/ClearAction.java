package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;


import com.daxton.customdisplay.task.action2.meta.Loop3;
import com.daxton.customdisplay.task.action2.location.Guise3;
import com.daxton.customdisplay.task.action2.location.Holographic3;
import com.daxton.customdisplay.task.action2.orbital.LocGuise3;
import com.daxton.customdisplay.task.action2.orbital.LocHolographic3;
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
            if(!bossBar2.getBossBar_Map().isEmpty()){
                bossBar2.getBossBar_Map().forEach((s, bossBar) -> bossBar.removeAll());
                bossBar2.getBossBar_Map().clear();
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

        for(LocHolographic3 holographic2 : ActionManager.judgment_LocHolographic_Map2.values()){
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








        /**---------------------------------------------------------------**/


        /**---------------------------------------------------------------**/

        ActionManager.action_Condition_Map.clear();

        ActionManager.loop_Condition_Map.clear();

        ActionManager.orbital_Condition_Map.clear();

        /**---------------------------------------------------------------**/

        ActionManager.playerUUID_taskID_Map.clear();

        ActionManager.taskID_Inventory_Map.clear();

        /**---------------------------------------------------------------**/




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
            if(!ActionManager.judgment_SendBossBar_Map2.get(taskID).getBossBar_Map().isEmpty()){
                ActionManager.judgment_SendBossBar_Map2.get(taskID).getBossBar_Map().forEach((s, bossBar) -> bossBar.removeAll());
                ActionManager.judgment_SendBossBar_Map2.get(taskID).getBossBar_Map().clear();
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



        /**--------------------------------------------------------------**/



    }


}
