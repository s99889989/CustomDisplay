package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SetAttribute {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SetAttribute(){

    }

    public void set(LivingEntity self, LivingEntity target, String firstString, String taskID){

        /**屬性名稱**/
        String attributes = new StringFind().getKeyValue2(self,target,firstString,"[];","GENERIC_MAX_HEALTH","attributes=","attr=");

        String type = new StringFind().getKeyValue2(self,target,firstString,"[];","default","type=");

        /**持續時間**/
        int duration = 200;
        try{
            duration = Integer.valueOf(new StringFind().getKeyValue2(self,target,firstString,"[];","200","duration=","dt="));
        }catch (NumberFormatException exception){
            duration = 200;

        }

        /**量**/
        double amount = 1;
        try{
            amount = Double.valueOf(new StringFind().getKeyValue2(self,target,firstString,"[];","1","amount=","a="));
        }catch (NumberFormatException exception){
            amount = 1;

        }

        /**目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);

        for(LivingEntity livingEntity : targetList){
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                giveAttr(player,attributes,type,amount,duration);
            }
        }


    }

    public void giveAttr(Player player, String attributes,String type , Double amount, int duration){
        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();


        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);



        ActionManager.setAttribute_Boolean_Map.put(uuidString+attributes,true);

        if(ActionManager.setAttribute_Run_Map.get(uuidString+attributes) == null){

            if(type.toLowerCase().equals("status")){
                if(playerData != null){
                    Map<String,String> attributes_EquipmentStats_Map = playerData.attributes_Stats_Map2;
                    attributes_EquipmentStats_Map.put(attributes,String.valueOf(amount));
                }
            }else if(type.toLowerCase().equals("equipment")){
                if(playerData != null){
                    Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                    attributes_EquipmentStats_Map.put(attributes,String.valueOf(amount));

                }
            }else {
                new PlayerBukkitAttribute().addAttribute(player,attributes.toUpperCase(),"ADD_NUMBER",amount,"");
            }


            ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
                @Override
                public void run() {

                    if(type.toLowerCase().equals("status")){
                        if(playerData != null){
                            Map<String,String> attributes_EquipmentStats_Map = playerData.attributes_Stats_Map2;
                            attributes_EquipmentStats_Map.put(attributes,"0");
                        }
                    }else if(type.toLowerCase().equals("equipment")){
                        if(playerData != null){
                            Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                            attributes_EquipmentStats_Map.put(attributes,"0");

                        }

                    }else {
                        new PlayerBukkitAttribute().removeAttribute(player,attributes.toUpperCase());
                    }


                    ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);
                    return;

                }
            });

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);

        }else {

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).cancel();

            if(type.toLowerCase().equals("status")){
                if(playerData != null){
                    Map<String,String> attributes_EquipmentStats_Map = playerData.attributes_Stats_Map2;
                    attributes_EquipmentStats_Map.put(attributes,"0");
                }
            }else if(type.toLowerCase().equals("equipment")){
                if(playerData != null){
                    Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                    attributes_EquipmentStats_Map.put(attributes,String.valueOf(amount));
                }
            }else {
                new PlayerBukkitAttribute().addAttribute(player,attributes.toUpperCase(),"ADD_NUMBER",amount,"");
            }




            ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
                @Override
                public void run() {


                    if(type.toLowerCase().equals("status")){

                    }else if(type.toLowerCase().equals("equipment")){
                        if(playerData != null){
                            Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                            attributes_EquipmentStats_Map.put(attributes,"0");
                        }
                    }else {
                        new PlayerBukkitAttribute().removeAttribute(player,attributes.toUpperCase());
                    }


                    ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);
                    return;

                }
            });

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);


        }






    }


}
