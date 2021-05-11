package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.JudgmentAction2;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerTrigger {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

//    private LivingEntity self = null;
//    private LivingEntity target = null;
//
//
//    /**觸發的動作列表**/
//    private static List<Map<String, String>> player_Action_List_Map = new ArrayList<>();
//    private static List<Map<String, String>> player_Item_Action_List_Map = new ArrayList<>();

    public PlayerTrigger(){

    }

//    public PlayerTrigger(Player player){
//        UUID playerUUID = player.getUniqueId();
//        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
//
//        if(playerData != null){
//
//            player_Action_List_Map = playerData.getPlayer_Action_List_Map();
//            player_Item_Action_List_Map = playerData.player_Item_Action_List_Map;
//        }
//    }

    //玩家觸發動作
    public static void onPlayer(Player player,LivingEntity target, String triggerName){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);

        if(playerData != null){
            List<Map<String, String>> player_Action_List_Map = playerData.getPlayer_Action_List_Map();
            List<Map<String, String>> player_Item_Action_List_Map = playerData.player_Item_Action_List_Map;

            player_Action_List_Map.forEach(stringStringMap -> {
                if(stringStringMap.get("triggerkey") != null && stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
                    runExecute(stringStringMap, player, target);
                }
            });

            player_Item_Action_List_Map.forEach(stringStringMap2 -> {
                if(stringStringMap2.get("triggerkey") != null && stringStringMap2.get("triggerkey").toLowerCase().equals(triggerName)){
                    runExecute(stringStringMap2, player, target);
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

//    /**觸發動作**/
//    public void onTwo(LivingEntity self,LivingEntity target, String triggerName){
//        this.self = self;
//        this.target = target;
//
//        this.player_Action_List_Map.forEach(stringStringMap -> {
//            if(stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
//                runExecute(stringStringMap, self, target);
//            }
//        });
//
//        this.player_Item_Action_List_Map.forEach(stringStringMap -> {
//            if(stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
//                runExecute(stringStringMap, self, target);
//            }
//        });
//
//        if(self instanceof Player){
//            Player player = (Player) self;
//            ActionManager.permission_Action_Map.forEach((s, maps) -> {
//                if(player.hasPermission("customdisplay.permission."+s.toLowerCase())){
//                        maps.forEach(stringStringMap -> {
//                            if(stringStringMap.get("triggerkey").toLowerCase().equals(triggerName)){
//                                runExecute(stringStringMap, self, target);
//                            }
//                        });
//                }
//            });
//        }
//
//
//    }

    /**直接使用動作列表**/
    public static void onSkill(LivingEntity self,LivingEntity target,List<Map<String, String>> action){
        if(action.size() > 0){
            for(Map<String, String> actionString : action){
                runExecute(actionString, self, target);
            }
        }
    }

    public static void runExecute(Map<String, String> action_Map, LivingEntity self, LivingEntity target){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String taskID = actionMapHandle.getString(new String[]{"mark","m"}, String.valueOf((int)(Math.random()*100000)));

        boolean stop = actionMapHandle.getBoolean(new String[]{"stop","s"}, false);

        if(stop){
            new ClearAction().taskID2(taskID);
        }else{
            new ClearAction().taskID2(taskID);

            ActionManager.trigger_Judgment_Map2.put(taskID,new JudgmentAction2());

            ActionManager.trigger_Judgment_Map2.get(taskID).execute(self, target, action_Map, taskID);


        }

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
