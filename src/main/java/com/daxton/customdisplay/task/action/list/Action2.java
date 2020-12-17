package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class Action2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private double damageNumber = 0;
    private String messageTarge = "self";
    private String action = "";
    private List<String> actionList = new ArrayList<>();
    private String mark = "";
    private boolean stop = false;

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
        messageTarge = "self";
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("messagetarge=") || string.toLowerCase().contains("mt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
                }
            }
        }

        for(String allString : new StringFind().getStringList(firstString)){
            if(allString.toLowerCase().contains("action=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    action = strings[1];
                    actionList = new ConfigFind().getActionKeyList(action);
                }
            }
            if(allString.toLowerCase().contains("mark=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    if(messageTarge.toLowerCase().contains("target")){
                        mark = new StringConversion("Character",strings[1],target).getResultString();
                    }else {
                        mark = new StringConversion("Character",strings[1],self).getResultString();
                    }
                    taskID = mark;
                }
            }

            if(allString.toLowerCase().contains("stop=") || allString.toLowerCase().contains("s=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    stop = Boolean.valueOf(strings[1]);
                }
            }
        }


        if(stop){
            bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    new ClearAction().clearPlayer(self,taskID);
                }
            };
            bukkitRunnable.runTaskLater(cd,1);

        }else {

            startAction();
        }

    }

    public void startAction(){
        new ClearAction().clearPlayer(self,taskID);
        if(actionList.size() > 0){
            for(String actionString : actionList){
                cd.getLogger().info("開始"+actionString);
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }

                if(ActionManager.getAction_Judgment_Map().get(taskID) == null){
                    ActionManager.getAction2_Judgment2_Map().put(taskID,new JudgmentAction2());
                }
                if(ActionManager.getAction_Judgment_Map().get(taskID) != null){
                    ActionManager.getAction2_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
                }

            }
        }

    }

    public boolean condition(String actionString){
        boolean b = false;
        if(self instanceof Player){
            if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
                ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
            }
            if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
                ConditionManager.getAction_Condition_Map().get(taskID).setCondition(self,target,actionString,damageNumber,taskID);
                b = ConditionManager.getAction_Condition_Map().get(taskID).getResult2();
            }
        }
        return b;
    }

}
