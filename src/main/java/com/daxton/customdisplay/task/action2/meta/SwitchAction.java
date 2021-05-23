package com.daxton.customdisplay.task.action2.meta;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.JudgmentAction2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class SwitchAction {

    public static void setAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
//        CustomDisplay cd = CustomDisplay.getCustomDisplay();
//        cd.getLogger().info(taskID);

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //判斷值
        String conditionContent = actionMapHandle.getString(new String[]{"ConditionContent","cc"},null);

        String conditionType = actionMapHandle.getString(new String[]{"conditionType","ct"},"null");

        //判斷選項
        String[] caseArray = actionMapHandle.getStringListLong(new String[]{"case","c"}, "\\|");

        //動作
        String[] caseAction = actionMapHandle.getStringListLong(new String[]{"action","a"}, "\\|");

        if(caseArray != null && caseArray.length > 0 && caseArray.length == caseAction.length){
            switch (conditionType.toLowerCase()){
                case "compare":
                    double left;
                    double right;
                    try {
                        left = Double.parseDouble(conditionContent);
                    }catch (NumberFormatException exception){
                        break;
                    }
                    if(conditionContent.endsWith(">") || conditionContent.endsWith("<") || conditionContent.endsWith("~")){

                        switch (conditionContent.substring(conditionContent.length()-1)){

                            case ">":

                                for(int i = 0 ; i < caseArray.length ; i++){
                                    try {
                                        right = Double.parseDouble(caseArray[i]);
                                    }catch (NumberFormatException exception){
                                        continue;
                                    }
                                    if(left > right){
                                        if(ActionManager.action_SubAction_Map.get(caseAction[i]) != null){
                                            List<Map<String, String>> action_Map_List = ActionManager.action_SubAction_Map.get(caseAction[i]);
                                            startAction(action_Map_List, taskID, self , target);

                                        }
                                    }

                                }
                                break;
                            case "<":
                                for(int i = 0 ; i < caseArray.length ; i++){
                                    try {
                                        right = Double.parseDouble(caseArray[i]);
                                    }catch (NumberFormatException exception){
                                        continue;
                                    }
                                    if(left < right){
                                        if(ActionManager.action_SubAction_Map.get(caseAction[i]) != null){
                                            List<Map<String, String>> action_Map_List = ActionManager.action_SubAction_Map.get(caseAction[i]);
                                            startAction(action_Map_List, taskID, self , target);

                                        }
                                    }

                                }
                                break;
                            case "~":
                                for(int i = 0 ; i < caseArray.length ; i++){
                                    try {
                                        right = Double.parseDouble(caseArray[i]);
                                    }catch (NumberFormatException exception){
                                        continue;
                                    }
                                    if(left == right){
                                        if(ActionManager.action_SubAction_Map.get(caseAction[i]) != null){
                                            List<Map<String, String>> action_Map_List = ActionManager.action_SubAction_Map.get(caseAction[i]);
                                            startAction(action_Map_List, taskID, self , target);

                                        }
                                    }

                                }
                        }
                    }else {
                        break;
                    }


                    break;
                case "contains":

                    for(int i = 0 ; i < caseArray.length ; i++){
                        if(conditionContent.contains(caseArray[i])){
                            if(ActionManager.action_SubAction_Map.get(caseAction[i]) != null){
                                List<Map<String, String>> action_Map_List = ActionManager.action_SubAction_Map.get(caseAction[i]);
                                startAction(action_Map_List, taskID, self , target);

                            }
                        }
                    }

                    break;

                default:


                    for(int i = 0 ; i < caseArray.length ; i++){
                        if(conditionContent.equals(caseArray[i])){
                            if(ActionManager.action_SubAction_Map.get(caseAction[i]) != null){
                                List<Map<String, String>> action_Map_List = ActionManager.action_SubAction_Map.get(caseAction[i]);
                                startAction(action_Map_List, taskID, self , target);

                            }
                        }
                    }


            }
        }






    }

    public static void startAction(List<Map<String, String>> action_Map_List, String taskID, LivingEntity self, LivingEntity livingEntity){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        new ClearAction().taskID2(taskID);
        int delay = 0;

        for(Map<String, String> stringStringMap : action_Map_List){

            ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, self, livingEntity);
            String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

            if(judgMent.toLowerCase().contains("break")){
                if(!Break.valueOf(self, livingEntity, stringStringMap, taskID)){
                    return;
                }
            }

            if(judgMent.toLowerCase().contains("delay")){
                int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                delay = delay + delayTicks;
            }

            if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new JudgmentAction2().execute(self, livingEntity, stringStringMap, taskID);
                    }
                }.runTaskLater(cd,delay);

            }
        }

    }

}
