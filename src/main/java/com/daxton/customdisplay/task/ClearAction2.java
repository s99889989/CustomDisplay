package com.daxton.customdisplay.task;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager2;

public class ClearAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction2(){

    }

    public void clear(String taskID){

        if(ActionManager2.judgment_Loop_Map.get(taskID) != null){
            if(!ActionManager2.judgment_Loop_Map.get(taskID).isCancelled()){
                ActionManager2.judgment_Loop_Map.get(taskID).cancel();
            }
            ActionManager2.judgment_Loop_Map.remove(taskID);
        }

        if(ActionManager2.judgment_Holographic_Map.get(taskID) != null){
            if(ActionManager2.judgment_Holographic_Map.get(taskID).getHologram() != null){
                ActionManager2.judgment_Holographic_Map.get(taskID).getHologram().delete();
            }
            ActionManager2.judgment_Holographic_Map.remove(taskID);
        }


        if(ActionManager2.judgment_SendBossBar_Map.get(taskID) != null){
            if(ActionManager2.judgment_SendBossBar_Map.get(taskID).getBossBar() != null){
                ActionManager2.judgment_SendBossBar_Map.get(taskID).getBossBar().removeAll();
                //cd.getLogger().info("刪除BossBar");
            }
            ActionManager2.judgment_SendBossBar_Map.remove(taskID);
        }


        /**--------------------------------------------------------------**/

        if(ActionManager2.trigger_Judgment_Map.get(taskID) != null){
            ActionManager2.trigger_Judgment_Map.remove(taskID);
        }

        if(ActionManager2.loop_Judgment_Map.get(taskID) != null){
            ActionManager2.loop_Judgment_Map.remove(taskID);
        }


    }

}
