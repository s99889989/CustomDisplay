package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.task.action.list.Condition;
import com.daxton.customdisplay.task.action.list.HolographicNew;
import com.daxton.customdisplay.task.action.list.Loop;
import com.daxton.customdisplay.manager.player.TriggerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class JudgmentAction {

    public JudgmentAction(){

    }


    public void execute(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        /**動作第一個關鍵字**/
        String judgMent = new JudgmentAction().getAction(firstString);


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

    /**丟入整個動作 返回動作第一個關鍵字**/
    public String getAction(String string){
        String lastString = "";
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        if(stringList.size() > 0){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            lastString = strings[0];
        }
        return lastString;
    }


}
