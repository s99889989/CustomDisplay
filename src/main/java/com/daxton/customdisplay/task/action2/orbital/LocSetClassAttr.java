package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.MobData;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class LocSetClassAttr {





    public LocSetClassAttr(){

    }

    public static void set(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //屬性名稱
        String attributes = actionMapHandle.getString(new String[]{"attributes","attr"},"GENERIC_MAX_HEALTH");

        //屬性類型
        String type = actionMapHandle.getString(new String[]{"type"},"default");

        //屬性類型
        String label = actionMapHandle.getString(new String[]{"label"},"");

        //持續時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},200);

        //量
        String amount = actionMapHandle.getString(new String[]{"amount","a"},"1");

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        livingEntityList.forEach(livingEntity -> giveAttr(target, attributes, label, type, amount, duration) );


    }

    public static void giveAttr(LivingEntity livingEntity, String attributes, String label, String type , String amount, int duration){
        String uuidString = livingEntity.getUniqueId().toString();



        //如果原本技能效果還存在
        if(ActionManager.setAttribute_Run_Map.get(uuidString+attributes) == null){

            setAttr(livingEntity, type, attributes, amount, label, duration);


        }
//        //如果原本技能效果不存在
//        else {
//
//            ActionManager.setAttribute_Run_Map.get(uuidString+attributes).cancel();
//
//            setAttr(livingEntity, type, attributes, amount, label, duration);
//
//        }


    }

    public static void setAttr(LivingEntity livingEntity, String type, String attributes, String amount, String label, int duration){

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String uuidString = livingEntity.getUniqueId().toString();

        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
        if(playerData != null){
            switch (type.toLowerCase()){
                case "equipment":
                    playerData.setSnapEqmState(attributes, label, amount);
                    break;
                case "point":
                    playerData.setSnapAttrPoint(attributes, label, amount);
                    break;
                default:
                    playerData.setSnapState(attributes, label, amount);
                    break;
            }

            if(duration > 0){
                ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
                    @Override
                    public void run() {

                        switch (type.toLowerCase()){
                            case "equipment":
                                playerData.setSnapEqmState(attributes, label, null);
                                break;
                            case "point":
                                playerData.setSnapAttrPoint(attributes, label, null);
                                break;
                            default:
                                playerData.setSnapState(attributes, label, null);
                                break;
                        }

                        ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);

                    }
                });

                ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);
            }
        }else {

            if(MobManager.mythicMobs_mobID_Map.get(uuidString) != null){
                String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);

                MobData mobData = MobManager.mob_Data_Map.get(mobID);

                mobData.setCustomState(attributes, label, amount);

                if(duration > 0){
                    ActionManager.setAttribute_Run_Map.put(uuidString + attributes, new BukkitRunnable() {
                        @Override
                        public void run() {

                            mobData.setCustomState(attributes, label, null);

                            ActionManager.setAttribute_Run_Map.remove(uuidString+attributes);

                        }
                    });

                    ActionManager.setAttribute_Run_Map.get(uuidString+attributes).runTaskLater(cd,duration);
                }


            }


        }


    }


}
