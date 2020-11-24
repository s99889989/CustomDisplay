package com.daxton.customdisplay.api.action;

import com.daxton.customdisplay.api.action.list.HolographicNew;
import com.daxton.customdisplay.api.action.list.Loop;
import com.daxton.customdisplay.manager.player.TriggerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class JudgmentAction {

    public JudgmentAction(){

    }
    /**丟入整個動作 返回動作第一個關鍵字**/
    public String getAction(String string){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[,] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        String[] strings = stringList.toArray(new String[stringList.size()]);
        return strings[0];
    }

    public void execute(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        /**動作第一個關鍵字**/
        String judgMent = new JudgmentAction().getAction(firstString);
        /**HolographicDisplays的相關判斷**/
        if(judgMent.toLowerCase().contains("createhd") || judgMent.toLowerCase().contains("addlinehd") || judgMent.toLowerCase().contains("removelinehd") || judgMent.toLowerCase().contains("teleporthd") || judgMent.toLowerCase().contains("deletehd")){
            //new HolographicNew().setHD(player,target,firstString,damageNumber);
            if(TriggerManager.getHolographicTaskMap().get(taskID) == null){
                TriggerManager.getHolographicTaskMap().put(taskID,new HolographicNew());
            }
            if(TriggerManager.getHolographicTaskMap().get(taskID) != null){
                TriggerManager.getHolographicTaskMap().get(taskID).setHD(player,target,firstString,damageNumber);
            }
        }
        /**Loop的相關判斷**/
        if(judgMent.toLowerCase().contains("loop")){
            new Loop(player,target,firstString,damageNumber,taskID);
        }

    }

}
