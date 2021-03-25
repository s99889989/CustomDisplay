package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class MythicMobTypeList {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public MythicMobTypeList(){

    }

    public boolean valueOf(LivingEntity self, LivingEntity target, String firstString, String taskID){
        boolean b = false;
        String aims = new SetValue(self,target,firstString,"[];","self","@=").getString();
        String mythicTypeListString = new SetValue(self,target,firstString,"[];","","mythictypelist=").getString();
        List<String> mythicTypeList = ConfigMapManager.getFileConfigurationMap().get("Character_System_MythicTypeList.yml").getStringList(mythicTypeListString+".mythicTypeList");

        if(aims.toLowerCase().contains("target")){
            String uuidString = target.getUniqueId().toString();
            if(MobManager.mythicMobs_mobID_Map.get(uuidString) != null){
                String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);
                if(mythicTypeList.contains(mobID)){
                    b = true;
                }
            }

        }else {
            String uuidString = self.getUniqueId().toString();
            if(MobManager.mythicMobs_mobID_Map.get(uuidString) != null){
                String mobID = MobManager.mythicMobs_mobID_Map.get(uuidString);
                if(mythicTypeList.contains(mobID)){
                    b = true;
                }
            }
        }

        return b;
    }


}
