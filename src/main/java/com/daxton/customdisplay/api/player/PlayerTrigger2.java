package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.task.ClearAction2;
import com.daxton.customdisplay.task.JudgmentAction2;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTrigger2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;


    /**觸發的動作列表**/
    private Map<String, List<CustomLineConfig>> action_Trigger_Map = new HashMap<>();
    private Map<String, List<CustomLineConfig>> action_Item_Trigger_Map = new HashMap<>();

    public PlayerTrigger2(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);

        if(playerData != null){
            action_Trigger_Map = playerData.getAction_Trigger_Map2();
            action_Item_Trigger_Map = playerData.getAction_Item_Trigger_Map();
        }
    }

    /**觸發動作**/
    public void onTwo(LivingEntity self,LivingEntity target, String triggerName){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get(triggerName) != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get(triggerName)){

                runExecute(actionString);
            }
        }
        if(action_Item_Trigger_Map.get(triggerName) != null){
            for(CustomLineConfig actionString : action_Item_Trigger_Map.get(triggerName)){

                runExecute(actionString);
            }
        }
    }

    /**直接使用動作列表**/
    public void onSkill(LivingEntity self,LivingEntity target,List<CustomLineConfig> action){
        this.self = self;
        this.target = target;
        if(action.size() > 0){
            for(CustomLineConfig actionString : action){
                runExecute(actionString);
            }
        }
    }

    /**直接使用單個動作**/
    public void onSkill2(LivingEntity self,LivingEntity target,CustomLineConfig actionString){
        this.self = self;
        this.target = target;

        runExecute(actionString);

    }


    public void runExecute(CustomLineConfig customLineConfig){

//        if(target != null){
//            getAttr(self, target);
//        }


        /**確認觸發者權限**/
        if(checkPermission(self, customLineConfig)){
            return;
        }

        //List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        //for(LivingEntity livingEntity : livingEntityList){
            String taskID = customLineConfig.getString(new String[]{"mark","m"},String.valueOf((int)(Math.random()*100000)),self,target);

            boolean stop = customLineConfig.getBoolean(new String[]{"stop","s"}, false,self, target);

            if(stop){
                if(ActionManager2.trigger_Judgment_Map.get(taskID) != null){
                    new ClearAction2().clear(taskID);

                    ActionManager2.trigger_Judgment_Map.remove(taskID);
                }
            }else{
                if(ActionManager2.trigger_Judgment_Map.get(taskID) != null){
                    new ClearAction2().clear(taskID);

                    ActionManager2.trigger_Judgment_Map.remove(taskID);
                }
                if(ActionManager2.trigger_Judgment_Map.get(taskID) == null){
                    ActionManager2.trigger_Judgment_Map.put(taskID,new JudgmentAction2());

                    ActionManager2.trigger_Judgment_Map.get(taskID).execute(self,target,customLineConfig,taskID);

                }
            }

        //}

    }

    public boolean checkPermission(LivingEntity livingEntity, CustomLineConfig customLineConfig){
        boolean bb = false;
        if(customLineConfig.getPermission() == null){
            return false;
        }


        FileConfiguration configuration = ConfigMapManager.getFileConfigurationMap().get("config.yml");
        boolean setting = configuration.getBoolean("Permission.fastUse");
        if(setting){
            if(livingEntity instanceof Player){
                Player player = (Player) self;
                String pp = customLineConfig.getPermission();
                if(!player.hasPermission(pp)){
                    bb = true;
                }
            }
        }

        return bb;
    }

    public void getAttr(LivingEntity self, LivingEntity target){

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
    }

    public void attrr(LivingEntity livingEntity, String inherit){
        AttributeInstance attributeInstance = livingEntity.getAttribute(Enum.valueOf(Attribute.class,inherit));
        cd.getLogger().info(livingEntity.getName()+" : "+inherit+"屬性值"+attributeInstance.getValue());
    }


}
