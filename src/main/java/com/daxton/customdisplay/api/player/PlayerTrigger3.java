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

public class PlayerTrigger3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;


    /**觸發的動作列表**/
    private Map<String, List<CustomLineConfig>> action_Trigger_Map = new HashMap<>();
    private Map<String, List<CustomLineConfig>> action_Item_Trigger_Map = new HashMap<>();

    public PlayerTrigger3(Player player){
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);

        if(playerData != null){
            action_Trigger_Map = playerData.getAction_Trigger_Map2();
            action_Item_Trigger_Map = playerData.getAction_Item_Trigger_Map();
        }
    }
    /**當攻擊時**/
    public void onAttack(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onattack") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onattack")){

                runExecute(actionString);
            }
        }
    }
    /**當爆擊時**/
    public void onCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~oncrit") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~oncrit")){

                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊時**/
    public void onMagic(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmagic") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmagic")){

                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊爆擊時**/
    public void onMCrit(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmcrit") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmcrit")){

                runExecute(actionString);
            }
        }
    }
    /**當魔法攻擊爆擊時**/
    public void onAtkMiss(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onatkmiss") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onatkmiss")){

                runExecute(actionString);
            }
        }
    }
    /**被攻擊時**/
    public void onDamaged(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondamaged") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~ondamaged")){

                runExecute(actionString);
            }
        }
    }
    /**被攻擊時**/
    public void onDamagedMiss(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~ondamagedmiss") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~ondamagedmiss")){

                runExecute(actionString);
            }
        }
    }
    /**當回血時**/
    public void onRegainHealth(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onregainhealth") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onregainhealth")){

                runExecute(actionString);
            }
        }
    }
    /**當登入時**/
    public void onJoin(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onjoin") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onjoin")){

                runExecute(actionString);
            }
        }
    }
    /**當登出時**/
    public void onQuit(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onquit") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onquit")){

                runExecute(actionString);
            }
        }
    }
    /**移動時**/
    public void onMove(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onmove") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmove")){

                runExecute(actionString);
            }
        }
    }
    /**蹲下時**/
    public void onSneak(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onsneak") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onsneak")){

                runExecute(actionString);
            }
        }
    }
    /**站起來時**/
    public void onStandup(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onstandup") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onstandup")){

                runExecute(actionString);
            }
        }
    }
    /**當死亡時**/
    public void onDeath(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~ondeath") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~ondeath")){

                runExecute(actionString);
            }
        }
    }
    /**當按下案件F時，一開始會觸發為ON，登出重新計算**/
    public void onKeyFON(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkeyfon") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkeyfon")){

                runExecute(actionString);
            }
        }
    }
    /**再按下案件F時，觸發為OFF，登出重新計算**/
    public void onKeyFOFF(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onkeyfoff") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkeyfoff")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄1時**/
    public void onKey1(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey1") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey1")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄2時**/
    public void onKey2(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey2") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey2")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄3時**/
    public void onKey3(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey3") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey3")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄4時**/
    public void onKey4(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey4") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey4")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄5時**/
    public void onKey5(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey5") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey5")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄6時**/
    public void onKey6(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey6") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey6")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄7時**/
    public void onKey7(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey7") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey7")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄8時**/
    public void onKey8(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey8") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey8")){

                runExecute(actionString);
            }
        }
    }
    /**當切換到物品欄9時**/
    public void onKey9(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onkey9") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onkey9")){

                runExecute(actionString);
            }
        }
    }
    /**當說話時**/
    public void onChat(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onchat") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onchat")){

                runExecute(actionString);
            }
        }
    }
    /**當輸入指令時**/
    public void onCommand(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~oncommand") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~oncommand")){

                runExecute(actionString);
            }
        }
    }
    /**當等級提升時**/
    public void onLevelUp(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onlevelup") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onlevelup")){

                runExecute(actionString);
            }
        }
    }
    /**當等級降低時**/
    public void onLevelDown(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onleveldown") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onleveldown")){

                runExecute(actionString);
            }
        }
    }
    /**當獲得經驗值時**/
    public void onExpUp(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onexpup") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onexpup")){


                runExecute(actionString);
            }
        }
    }
    /**當失去經驗值時**/
    public void onExpDown(LivingEntity self){
        this.self = self;
        if(action_Trigger_Map.get("~onexpdown") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onexpdown")){

                runExecute(actionString);
            }
        }
    }

    /**當怪物死亡時.(死亡原因必須是該玩家)**/
    public void onMobDeath(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmobdeath") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmobdeath")){

                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs怪物死亡時.(死亡原因必須是該玩家)**/
    public void onMythicMobDeath(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdeath") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmmobdeath")){

                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落經驗**/
    public void onMMobDropExp(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropexp") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmmobdropexp")){

                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落金錢**/
    public void onMMobDropMoney(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropmoney") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmmobdropmoney")){

                runExecute(actionString);
            }
        }
    }
    /**當MythicMobs的怪物掉落物品**/
    public void onMMobDropItem(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~onmmobdropitem") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~onmmobdropitem")){

                runExecute(actionString);
            }
        }
    }
    /**當左鍵點擊時**/
    public void onLeftClickAir(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~leftclickair") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~leftclickair")){

                runExecute(actionString);
            }
        }
    }
    /**當左鍵點擊方塊時**/
    public void onLeftClickBlock(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~leftclickblock") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~leftclickblock")){

                runExecute(actionString);
            }
        }
    }
    /**當右鍵點擊時**/
    public void onRightClickAir(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~rightclickair") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~rightclickair")){

                runExecute(actionString);
            }
        }
        if(action_Item_Trigger_Map.get("~rightclickair") != null){
            for(CustomLineConfig actionString : action_Item_Trigger_Map.get("~rightclickair")){

                runExecute(actionString);
            }
        }
    }
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
    /**當右鍵點擊方塊時**/
    public void onRightClickBlock(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~rightclickblock") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~rightclickblock")){

                runExecute(actionString);
            }
        }

    }
    /**當踩到壓力板時**/
    public void onPressurePlate(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map.get("~pressureplate") != null){
            for(CustomLineConfig actionString : action_Trigger_Map.get("~pressureplate")){

                runExecute(actionString);
            }
        }
    }
    /**當裝備檢查時**/
    public void onEqmCheck(LivingEntity self,LivingEntity target){
        this.self = self;
        this.target = target;
        if(action_Trigger_Map != null){
            if(action_Trigger_Map.get("~eqmcheck") != null){
                for(CustomLineConfig actionString : action_Trigger_Map.get("~eqmcheck")){

                    runExecute(actionString);
                }
            }
        }

    }

    /**使用Gui動作**/
    public void onGuiClick(LivingEntity self,List<CustomLineConfig> action){
        this.self = self;
        if(action.size() > 0){
            for(CustomLineConfig actionString : action){
                runExecute(actionString);
            }
        }
    }
    /**使用技能**/
    public void onSkill(LivingEntity self,LivingEntity target,List<CustomLineConfig> action){
        this.self = self;
        this.target = target;
        if(action.size() > 0){
            for(CustomLineConfig actionString : action){
                runExecute(actionString);
            }
        }
    }
    /**使用單個技能**/
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

        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        for(LivingEntity livingEntity : livingEntityList){
            String taskID = customLineConfig.getString(new String[]{"mark","m"},String.valueOf((int)(Math.random()*100000)),self,livingEntity);

            boolean stop = customLineConfig.getBoolean(new String[]{"stop","s"}, false,self, livingEntity);

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

                    ActionManager2.trigger_Judgment_Map.get(taskID).execute(self,livingEntity,customLineConfig,taskID);

                }
            }

        }

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
