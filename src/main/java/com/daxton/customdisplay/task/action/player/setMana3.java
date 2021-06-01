package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class setMana3 {

    public setMana3(){

    }

    public static void setMana(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**補量**/
        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},10);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    giveMana(player,amount);
                }
            }

        }


    }

    public static void giveMana(Player player,double amount){
        String uuidString = player.getUniqueId().toString();

        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);

        double maxMana = playerData.maxMana;

        double nowMana = playerData.mana;

        double giveMana = nowMana+amount;


        if(giveMana > maxMana){
            giveMana = maxMana;
        }
        if(giveMana < 0){
            giveMana = 0;
        }
        playerData.setMana(giveMana);

    }

}
