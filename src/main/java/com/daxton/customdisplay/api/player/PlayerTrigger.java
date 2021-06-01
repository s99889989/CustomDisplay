package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.daxton.customdisplay.task.JudgmentAction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerTrigger {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public PlayerTrigger(){

    }

    //玩家觸發動作
    public static void onPlayer(Player player,LivingEntity target, String triggerName){

        String uuidString = player.getUniqueId().toString();

        PlayerData2 playerData2 = PlayerManager.player_Data_Map.get(uuidString);
        //CustomDisplay.getCustomDisplay().getLogger().info(triggerName);

        if(playerData2 != null){
            List<Map<String, String>> player_Action_List_Map = playerData2.player_Action_List_Map;
            player_Action_List_Map.forEach(stringStringMap -> {
                if(stringStringMap.get("triggerkey") != null && stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
                    runExecute(stringStringMap, player, target);
                }
            });

            List<Map<String, String>> player_Item_Action_List_Map = playerData2.player_Item_Action_List_Map;
            player_Item_Action_List_Map.forEach(stringStringMap2 -> {
                if(stringStringMap2.get("triggerkey") != null && stringStringMap2.get("triggerkey").toLowerCase().equals(triggerName)){
                    runExecute(stringStringMap2, player, target);
                }
            });

            playerData2.player_Skill_Action_List_Map.forEach(stringStringMap3 -> {
                if(stringStringMap3.get("triggerkey") != null && stringStringMap3.get("triggerkey").toLowerCase().equals(triggerName)){
                    //CustomDisplay.getCustomDisplay().getLogger().info("技能觸發"+stringStringMap3.get("triggerkey"));
                    runExecute(stringStringMap3, player, target);
                }
            });

            ActionManager.permission_Action_Map.forEach((s, maps) -> {
                if(player.hasPermission("customdisplay.permission."+s.toLowerCase())){
                    maps.forEach(stringStringMap -> {
                        if(stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
                            runExecute(stringStringMap, player, target);
                        }
                    });
                }
            });

        }

    }

    //直接使用動作列表
    public static void onSkillList(LivingEntity self, LivingEntity target, List<Map<String, String>> action){
        if(action.size() > 0){
            for(Map<String, String> actionString : action){
                runExecute(actionString, self, target);
            }
        }
    }

    public static void runExecute(Map<String, String> action_Map, LivingEntity self, LivingEntity target){

        JudgmentAction.execute(self, target, action_Map, String.valueOf((int)(Math.random()*100000)), null);

    }



//    public void getAttr(LivingEntity self, LivingEntity target){
//
//        attrr(target,"GENERIC_MAX_HEALTH");
//        attrr(target,"GENERIC_FOLLOW_RANGE");
//        attrr(target,"GENERIC_KNOCKBACK_RESISTANCE");
//        attrr(target,"GENERIC_MOVEMENT_SPEED");
//        attrr(target,"GENERIC_FLYING_SPEED");
//        attrr(target,"GENERIC_ATTACK_DAMAGE");
//        attrr(target,"GENERIC_ATTACK_KNOCKBACK");
//        attrr(target,"GENERIC_ATTACK_SPEED");
//        attrr(target,"GENERIC_ARMOR");
//        attrr(target,"GENERIC_ARMOR_TOUGHNESS");
//        attrr(self,"GENERIC_LUCK");
//        attrr(target,"HORSE_JUMP_STRENGTH");
//        attrr(target,"ZOMBIE_SPAWN_REINFORCEMENTS");
//    }

//    public void attrr(LivingEntity livingEntity, String inherit){
//        AttributeInstance attributeInstance = livingEntity.getAttribute(Enum.valueOf(Attribute.class,inherit));
//        cd.getLogger().info(livingEntity.getName()+" : "+inherit+"屬性值"+attributeInstance.getValue());
//    }


}
