package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.manager.TriggerManager;
import org.bukkit.entity.Player;

public class ClearAction {

    public ClearAction(){

    }
    /**清除當下的任務**/
    public void clearPlayer(Player player,String taskID){


        if(TriggerManager.getJudgment_Loop_Map().get(taskID) != null){
            TriggerManager.getJudgment_Loop_Map().get(taskID).cancel();
            TriggerManager.getJudgment_Loop_Map().remove(taskID);
        }
        if(TriggerManager.getJudgment_Holographic_Map().get(taskID) != null){
            TriggerManager.getJudgment_Holographic_Map().get(taskID).deleteHD();
            TriggerManager.getJudgment_Holographic_Map().remove(taskID);
        }

        if(TriggerManager.getJudgment_Action_Map().get(taskID) != null){
            TriggerManager.getJudgment_Action_Map().remove(taskID);
        }
        if(TriggerManager.getLoop_Judgment_Map().get(taskID) != null){
            TriggerManager.getLoop_Judgment_Map().get(taskID).getBukkitRunnable().cancel();
            TriggerManager.getLoop_Judgment_Map().remove(taskID);
        }
        if(TriggerManager.getAction_Judgment_Map().get(taskID) != null){
            TriggerManager.getAction_Judgment_Map().get(taskID).getBukkitRunnable().cancel();
            TriggerManager.getAction_Judgment_Map().remove(taskID);
        }
        if(TriggerManager.getJudgment_BossBar_Map().get(taskID) != null){
            TriggerManager.getJudgment_BossBar_Map().remove(taskID);
        }
        if(TriggerManager.getBossBar_Map().get(taskID) != null){
            TriggerManager.getBossBar_Map().get(taskID).removePlayer(player);
            TriggerManager.getBossBar_Map().remove(taskID);
        }
    }

}
