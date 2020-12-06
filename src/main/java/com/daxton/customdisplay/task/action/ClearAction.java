package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.entity.Player;

public class ClearAction {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ClearAction(){

    }
    /**清除當下的任務**/
    public void clearPlayer(Player player,String taskID){


        if(ActionManager.getJudgment_Loop_Map().get(taskID) != null){
            ActionManager.getJudgment_Loop_Map().get(taskID).cancel();
            ActionManager.getJudgment_Loop_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_Holographic_Map().get(taskID) != null){
            ActionManager.getJudgment_Holographic_Map().get(taskID).deleteHD();
            ActionManager.getJudgment_Holographic_Map().remove(taskID);
        }

        if(ActionManager.getJudgment_Action_Map().get(taskID) != null){
            ActionManager.getJudgment_Action_Map().remove(taskID);
        }
        if(ActionManager.getLoop_Judgment_Map().get(taskID) != null){
            ActionManager.getLoop_Judgment_Map().get(taskID).getBukkitRunnable().cancel();
            ActionManager.getLoop_Judgment_Map().remove(taskID);
        }
        if(ActionManager.getAction_Judgment_Map().get(taskID) != null){
            ActionManager.getAction_Judgment_Map().get(taskID).getBukkitRunnable().cancel();
            ActionManager.getAction_Judgment_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_BossBar_Map().get(taskID) != null){
            ActionManager.getJudgment_BossBar_Map().remove(taskID);
        }
        if(ActionManager.getBossBar_Map().get(taskID) != null){
            ActionManager.getBossBar_Map().get(taskID).removePlayer(player);
            ActionManager.getBossBar_Map().remove(taskID);
        }
    }

}
