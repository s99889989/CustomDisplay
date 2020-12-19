package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class Action2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private List<String> actionList = new ArrayList<>();
    private BukkitRunnable bukkitRunnable;

    public Action2(){

    }

    public void setAction(LivingEntity self, LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        stringSetting();
    }

    public void stringSetting(){
        for(String allString : new StringFind().getStringList(firstString)){
            if(allString.toLowerCase().contains("action=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    actionList = new ConfigFind().getActionKeyList(strings[1]);
                }
            }
        }

        startAction();

    }

    public void startAction(){

        if(actionList.size() > 0){
            int delay = 0;
            for(String actionString : actionList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(firstString.toLowerCase().contains("delay")){
                    String[] strings = firstString.toLowerCase().split(" ");
                    if(strings.length == 2){
                        try{
                            delay = delay + Integer.valueOf(strings[1]);
                        }catch (NumberFormatException exception){

                        }
                        //cd.getLogger().info("延遲"+delay);
                    }
                }
                bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        new JudgmentAction2().execute(self,target,actionString,taskID);
                    }
                };
                bukkitRunnable.runTaskLater(cd,delay);
//                if(ActionManager2.getOther_Judgment2_Map().get(taskID) == null){
//                    ActionManager2.getOther_Judgment2_Map().put(taskID,new JudgmentAction2());
//                    ActionManager2.getOther_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
//                }else {
//                    ActionManager2.getOther_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
//                }

            }

            if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
                ConditionManager.getAction_Condition_Map().remove(taskID);
            }
        }

    }

    public boolean condition(String actionString){
        boolean b = false;
        if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
            ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
        }
        if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
            ConditionManager.getAction_Condition_Map().get(taskID).setCondition(self,target,actionString,taskID);
            b = ConditionManager.getAction_Condition_Map().get(taskID).getResult2();
        }
        return b;
    }

}
