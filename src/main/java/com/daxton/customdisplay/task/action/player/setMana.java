package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class setMana {

    public setMana(){

    }

    public void setMana(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**補量**/
        double amount = customLineConfig.getDouble(new String[]{"amount","a"},10,self,target);

        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

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
