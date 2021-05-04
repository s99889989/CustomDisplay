package com.daxton.customdisplay.api.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.Config;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetActionMap {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SetActionMap(){

        setClassAction();
        setPermissionAction();
        setSubAction();
    }

    public void setClassAction(){
        Map<String, FileConfiguration> configTypeMap = Config.getTypeConfigMap("Class_Action_");
        ActionManager.class_Action_Map.clear();
        configTypeMap.forEach((typeString, fileConfiguration) -> {

            //在動作Config裡面抓取ActionListString
            List<String> actionList = fileConfiguration.getStringList("Action");

            List<Map<String, String>> actionMapList = new ArrayList<>();
            //把ActionListString內的字串轉成Map
            actionList.forEach(s -> actionMapList.add(setClassAction(s)));
            //把轉成的Map一照設定檔名稱儲存到Map
            String typeName = typeString.replace("Class_Action_","").replace(".yml","");
            ActionManager.class_Action_Map.put(typeName, actionMapList);
        });
    }

    public void setPermissionAction(){
        Map<String, FileConfiguration> configTypeMap = Config.getTypeConfigMap("Permission_");
        ActionManager.permission_Action_Map.clear();
        configTypeMap.forEach((typeString, fileConfiguration) -> {

            //在動作Config裡面抓取ActionListString
            List<String> actionList = fileConfiguration.getStringList("Action");

            List<Map<String, String>> actionMapList = new ArrayList<>();
            //把ActionListString內的字串轉成Map
            actionList.forEach(s -> actionMapList.add(setClassAction(s)));
            //把轉成的Map一照設定檔名稱儲存到Map
            String typeName = typeString.replace("Permission_","").replace(".yml","");
            ActionManager.permission_Action_Map.put(typeName, actionMapList);
        });
    }

    public void setSubAction(){
        Map<String, FileConfiguration> configTypeMap = Config.getTypeConfigMap("Actions_");
        ActionManager.action_SubAction_Map.clear();
        configTypeMap.forEach((typeString, fileConfiguration) -> {

            //在動作Config裡面抓取ActionListString
            fileConfiguration.getConfigurationSection("").getKeys(false).forEach(actionKey -> {
                List<String> actionList = fileConfiguration.getStringList(actionKey+".Action");

                List<Map<String, String>> actionMapList = new ArrayList<>();
                actionList.forEach(s -> {
                    //cd.getLogger().info(s);
                    actionMapList.add(setClassAction(s));
                });

                ActionManager.action_SubAction_Map.put(actionKey, actionMapList);

            });


        });

//        ActionManager.action_SubAction_Map.get("AttackDisplayUp").forEach(stringStringMap -> {
//            String ss = stringStringMap.get("ActionType");
//            cd.getLogger().info("動作: "+ss);
//        });

    }

    public static Map<String, String> setClassAction(String inputString){
        Map<String, String> actionMap = new HashMap<>();
        if (inputString.contains("[") && inputString.contains("]")) {

            int num1 = NumberUtil.appearNumber(inputString, "\\[");
            int num2 = NumberUtil.appearNumber(inputString, "\\]");
            if (num1 == 1 && num2 == 1) {
                //設定動作類型
                String actionType = inputString.substring(0, inputString.indexOf("[")).trim();
                actionMap.put("actiontype",actionType);
                //cd.getLogger().info("ActionType"+" : "+actionType);

                //從]往後的字串
                String lastSet = inputString.substring(inputString.indexOf("]")+1)+" ";
                //設定目標
                if(lastSet.contains("@")){
                    String targetKey = lastSet.substring(lastSet.indexOf("@"), lastSet.indexOf(" ",lastSet.indexOf("@")));

                    actionMap.put("targetkey",targetKey);
                    //cd.getLogger().info("TargetKey"+" : "+targetKey);
                }
                //設定觸發
                if(lastSet.contains("~")){
                    String triggerKey = lastSet.substring(lastSet.indexOf("~"), lastSet.indexOf(" ",lastSet.indexOf("~")));
                    actionMap.put("triggerkey",triggerKey);
                    //cd.getLogger().info("TriggerKey"+" : "+triggerKey);
                }

                String midSet = inputString.substring(inputString.indexOf("[")+1, inputString.indexOf("]"));

                List<String> midSetList = StringFind.getBlockList(midSet,";");
                midSetList.forEach(midKey -> {
                    String[] midArray = midKey.split("=");
                    if(midArray.length == 2){
                        //cd.getLogger().info(midArray[0]+" : "+midArray[1]);
                        actionMap.put(midArray[0].toLowerCase(),midArray[1]);
                    }
                });
            }
        }

        return actionMap;
    }

    public static Map<String, String> setTargetMap(String inputString){
        Map<String, String> targetMap = new HashMap<>();
        if (inputString.contains("{") && inputString.contains("}")) {

            int num1 = NumberUtil.appearNumber(inputString, "\\{");
            int num2 = NumberUtil.appearNumber(inputString, "\\}");
            if (num1 == 1 && num2 == 1) {

                String targetType = inputString.substring(inputString.indexOf("@")+1, inputString.indexOf("{")).trim();

                targetMap.put("targettype", targetType.toLowerCase());

                String midSet = inputString.substring(inputString.indexOf("{")+1, inputString.indexOf("}"));

                List<String> midSetList = StringFind.getBlockList(midSet,";");
                midSetList.forEach(midKey -> {
                    String[] midArray = midKey.split("=");
                    if(midArray.length == 2){
                        targetMap.put(midArray[0].toLowerCase(), midArray[1]);
                    }
                });
            }
        }else if(!inputString.contains("{") && !inputString.contains("}")){
            targetMap.put("targettype", inputString.replace("@","").trim().toLowerCase());
        }else {
            targetMap.put("targettype", "null");
        }
//        targetMap.forEach((s, s2) -> {
//            CustomDisplay.getCustomDisplay().getLogger().info(s+" : "+s2);
//        });

        return targetMap;
    }

}
