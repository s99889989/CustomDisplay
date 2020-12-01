package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


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
        String targetUUID = target.getUniqueId().toString();

        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
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
                    mark = mark.replace("{target_uuid}",targetUUID);
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
            removeOldAction();
        }else {
            startAction();
        }


    }
    /**只有玩家**/
    public void setAction(Player player, String firstString,String taskID){
        this.firstString = firstString;
        this.player = player;
        this.taskID = taskID;

        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
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
            removeOldAction();
        }else {
            startActionOne();
        }

    }

    public void setActionOneTwo(Player player,LivingEntity target, String firstString,String taskID){
        this.firstString = firstString;
        this.player = player;
        this.taskID = taskID;
        String targetUUID = target.getUniqueId().toString();

        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
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
                    mark = mark.replace("{target_uuid}",targetUUID);
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
            removeOldAction();
        }else {
            startAction();
        }


    }
    /**只有玩家**/
    public void startActionOne(){
        //removeOldAction();
        if(actionList.size() > 0){
            for(String actionString : actionList){
//                if(new Condition().getResuult(actionString,target,player,taskID)){
//                    break;
//                }
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
        removeOldAction();
        if(actionList.size() > 0){
            for(String actionString : actionList){
                if(new Condition().getResuult(actionString,target,player,taskID)){
                    break;
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

    public void removeOldAction(){

        if(TriggerManager.getJudgment_Loop_Map().get(taskID) != null){
            TriggerManager.getJudgment_Loop_Map().get(taskID).cancel();
            TriggerManager.getJudgment_Loop_Map().remove(taskID);
        }

        if(TriggerManager.getJudgment_LoopOne_Map().get(taskID) != null){
            TriggerManager.getJudgment_LoopOne_Map().get(taskID).cancel();
            TriggerManager.getJudgment_LoopOne_Map().remove(taskID);
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
