package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.manager.TriggerManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Action {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID;
    private Player player;
    private LivingEntity target;
    private double damageNumber;
    private String firstString;

    private String action = "";
    private List<String> actionList = new ArrayList<>();
    private String mark = "";
    private boolean stop = false;

    public Action(){

    }

    public void setAction(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        this.firstString = firstString;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        this.taskID = taskID;
        stringSetting(firstString);
    }
    /**沒有攻擊**/
    public void setAction(Player player, LivingEntity target, String firstString, String taskID){
        this.firstString = firstString;
        this.player = player;
        this.target = target;
        this.taskID = taskID;
        stringSetting(firstString);
    }
    /**只有玩家**/
    public void setAction(Player player, String firstString,String taskID){
        this.firstString = firstString;
        this.player = player;
        this.taskID = taskID;
        stringSetting(firstString);
    }

    public void stringSetting(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String allString : stringList){
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
                    mark = new StringConversion().getString("Character",strings[1],player);
                    if(target != null){
                        mark = mark.replace("{target_uuid}",target.getUniqueId().toString());
                    }
                    this.taskID = mark;
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
            new ClearAction().clearPlayer(this.player,this.taskID);
        }else {
            if(target == null){
                startActionOne();
            }else {
                startAction();
            }
        }
    }

    /**只有玩家**/
    public void startActionOne(){
        if(actionList.size() > 0){
            for(String actionString : actionList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(TriggerManager.getAction_Judgment_Map().get(taskID) == null){
                    TriggerManager.getAction_Judgment_Map().put(taskID,new JudgmentAction());
                }
                if(TriggerManager.getAction_Judgment_Map().get(taskID) != null){
                    TriggerManager.getAction_Judgment_Map().get(taskID).execute(player,actionString,taskID);
                }

            }
        }

    }

    public void startAction(){
        new ClearAction().clearPlayer(player,taskID);
        if(actionList.size() > 0){
            for(String actionString : actionList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(TriggerManager.getAction_Judgment_Map().get(taskID) == null){
                    TriggerManager.getTarget_getPlayer_Map().put(target.getUniqueId(),player);
                    TriggerManager.getAction_Judgment_Map().put(taskID,new JudgmentAction());
                }
                if(TriggerManager.getAction_Judgment_Map().get(taskID) != null){
                    TriggerManager.getAction_Judgment_Map().get(taskID).execute(player,target,actionString,damageNumber,taskID);
                }

            }
        }

    }

    public boolean condition(String actionString){
        boolean b = false;
        if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
            ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
        }
        if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
            if(target == null){
                ConditionManager.getAction_Condition_Map().get(taskID).setCondition(player,actionString,taskID);
            }else {
                ConditionManager.getAction_Condition_Map().get(taskID).setCondition(player,target,actionString,taskID);
            }
            b = ConditionManager.getAction_Condition_Map().get(taskID).getResult();
        }
        return b;
    }

}
