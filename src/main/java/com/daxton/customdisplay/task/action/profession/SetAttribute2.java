package com.daxton.customdisplay.task.action.profession;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

        ActionManager.setAttribute_Boolean_Map.put(uuidString+attributes,true);

        /**如果原本技能效果還存在**/
        if(ActionManager.setAttribute_Run_Map.get(uuidString+attributes) == null){

            setAttr(player, type, attributes, amount, label, duration);

        /**如果原本技能效果不存在**/
        }else {

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).cancel();

            setAttr(player, type, attributes, amount, label, duration);

        }


    }

    public void setAttr(Player player, String type, String attributes, Double amount, String label, int duration){

        String uuidString = player.getUniqueId().toString();
        UUID playerUUID = player.getUniqueId();

        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);

        switch (type.toLowerCase()){
            case "status":
                if(playerData != null){
                    Map<String,String> attributes_Stats_Map = playerData.attributes_Stats_Map2;
                    attributes_Stats_Map.put(attributes,String.valueOf(amount));
                }
                break;
            case "equipment":
                if(playerData != null){
                    Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                    attributes_EquipmentStats_Map.put(attributes,String.valueOf(amount));

                }
                break;
            case "point":
                if(playerData != null){
                    Map<String,String> attributes_Point_Map = playerData.attributes_Point_Map2;
                    attributes_Point_Map.put(attributes,String.valueOf(amount));
                }
                break;
            default:
                new PlayerBukkitAttribute().addAttribute(player,attributes.toUpperCase(),"ADD_NUMBER",amount,label);
                break;
        }



        if(duration > 0){
            ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
                @Override
                public void run() {

                    switch (type.toLowerCase()){
                        case "status":
                            if(playerData != null){
                                Map<String,String> attributes_Stats_Map = playerData.attributes_Stats_Map2;
                                attributes_Stats_Map.put(attributes,"0");
                            }
                            break;
                        case "equipment":
                            if(playerData != null){
                                Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map2;
                                attributes_EquipmentStats_Map.put(attributes,"0");

                            }
                            break;
                        case "point":
                            if(playerData != null){
                                if(playerData != null){
                                    Map<String,String> attributes_Point_Map = playerData.attributes_Point_Map2;
                                    attributes_Point_Map.put(attributes,String.valueOf(amount));
                                }
                            }
                            break;
                        default:
                            new PlayerBukkitAttribute().removeAttribute(player,attributes.toUpperCase());
                            break;
                    }


                    ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);
                    return;

                }
            });

            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);
        }

    }


}
