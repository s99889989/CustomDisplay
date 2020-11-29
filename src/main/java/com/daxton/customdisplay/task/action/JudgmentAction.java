package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.task.action.list.HolographicNew;
import com.daxton.customdisplay.task.action.list.Loop;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.list.LoopOne;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class JudgmentAction {

    public JudgmentAction(){

    }


    public void execute(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        /**動作第一個關鍵字**/
        String judgMent = new StringFind().getAction(firstString);

        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("createhd") || judgMent.toLowerCase().contains("addlinehd") || judgMent.toLowerCase().contains("removelinehd") || judgMent.toLowerCase().contains("teleporthd") || judgMent.toLowerCase().contains("deletehd")){
            if(TriggerManager.getHolographicTaskMap().get(taskID) == null){
                TriggerManager.getHolographicTaskMap().put(taskID,new HolographicNew());
            }
            if(TriggerManager.getHolographicTaskMap().get(taskID) != null){
                TriggerManager.getHolographicTaskMap().get(taskID).setHD(player,target,firstString,damageNumber,taskID);
            }
        }
        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){
            new Loop().onLoop(player,target,firstString,damageNumber,taskID);
//            if(TriggerManager.getJudgmentActionTaskMap().get(taskID) == null){
//                TriggerManager.getJudgmentActionTaskMap().put(taskID,new Loop());
//                TriggerManager.getJudgmentActionTaskMap().get(taskID).onLoop(player,target,firstString,damageNumber,taskID);
//            }
//            if(TriggerManager.getJudgmentActionTaskMap().get(taskID) != null){
//                TriggerManager.getJudgmentActionTaskMap().get(taskID).onLoop(player,target,firstString,damageNumber,taskID);
//            }

        }

    }

    public void executeOne(Player player, String firstString,String taskID){

        /**動作第一個關鍵字**/
        String judgMent = new StringFind().getAction(firstString);

        /**Action的相關判斷**/
        if(judgMent.toLowerCase().contains("action")){
            List<String> actionList = new ConfigFind().getActionList(firstString);
            for(String string : actionList){
                new JudgmentAction().executeOne(player,string,taskID);
            }
        }

        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){

            new LoopOne().onLoop(player,firstString,taskID);

        }

    }


}
