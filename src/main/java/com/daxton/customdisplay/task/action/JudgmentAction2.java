package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.list.*;
import org.bukkit.entity.LivingEntity;

public class JudgmentAction2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private double damageNumber = 0;
    private String taskID = "";

    public JudgmentAction2(){

    }

    public void execute(LivingEntity self, LivingEntity target, String firstString, double damageNumber,String taskID){
        this.self = self;
        this.target = target;
        this.damageNumber = damageNumber;
        this.firstString = firstString;
        this.taskID = taskID;
        bukkitRun();
    }

    public void bukkitRun(){


        String judgMent = new StringFind().getAction(firstString);
        /**Action的相關判斷**/
        if (judgMent.toLowerCase().contains("action")) {
            if(ActionManager.getJudgment2_Action2_Map().get(taskID) == null){
                ActionManager.getJudgment2_Action2_Map().put(taskID,new Action2());
            }
            if(ActionManager.getJudgment2_Action2_Map().get(taskID) != null){
                ActionManager.getJudgment2_Action2_Map().get(taskID).setAction(self,target,firstString,damageNumber,taskID);
            }

        }
    }


}
