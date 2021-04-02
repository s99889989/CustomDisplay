package com.daxton.customdisplay.task.action2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SetAttribute2 {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SetAttribute2(){

    }

    public void set(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**屬性名稱**/
        String attributes = customLineConfig.getString(new String[]{"attributes","attr"},"GENERIC_MAX_HEALTH",self,target);

        /**屬性類型**/
        String type = customLineConfig.getString(new String[]{"type"},"default",self,target);

        /**屬性類型**/
        String label = customLineConfig.getString(new String[]{"label"},"",self,target);

        /**持續時間**/
        int duration = customLineConfig.getInt(new String[]{"duration","dt"},200,self,target);

        /**量**/
        double amount = customLineConfig.getDouble(new String[]{"amount","a"},1,self,target);


        if(self instanceof Player){
            Player player = (Player) self;
            giveAttr(player,attributes, label,type,amount,duration);
        }



    }

    public void giveAttr(Player player, String attributes, String label, String type , Double amount, int duration){
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
                new PlayerBukkitAttribute().addAttribute(player,attributes.toUpperCase(),"ADD_NUMBER",amount,label);
            }

            if(duration > 0){
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
            }



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
                new PlayerBukkitAttribute().addAttribute(player,attributes.toUpperCase(),"ADD_NUMBER",amount,label);
            }


            if(duration > 0){
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


}
