package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class setMana3 {

    public setMana3(){

    }

    public void setMana(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**補量**/
        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},10);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityList();

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    giveMana(player,amount);
                }
            }

        }


    }

    public void giveMana(Player player,double amount){
        String uuidString = player.getUniqueId().toString();
        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(uuid);
//        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
//        String maxManaString = fileConfiguration.getString("CoreAttribute.Max_Mana.formula");
//        String maxManaString2 = new ConversionMain().valueOf(player,null,maxManaString);
        double maxMana = playerData.maxmana;
//        try {
//            maxMana = Double.valueOf(maxManaString2);
//        }catch (NumberFormatException exception){
//
//        }
        double nowMana = PlayerManager.player_nowMana.get(uuidString);

        double giveMana = nowMana+amount;


        if(giveMana > maxMana){
            giveMana = maxMana;
        }
        if(giveMana < 0){
            giveMana = 0;
        }

        PlayerManager.player_nowMana.put(uuidString, giveMana);
    }

}
