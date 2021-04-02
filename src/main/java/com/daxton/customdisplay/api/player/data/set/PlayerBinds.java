package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerBinds {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerBinds(){

    }
    /**把綁定技能存在Map**/
    public void setMap(Player player, Map<String,String> point_Map,FileConfiguration playerConfig){
        String uuidString = player.getUniqueId().toString();


        List<String> attrStatsList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Binds").getKeys(false));
        if(attrStatsList.size() > 0){
            for(String attrName : attrStatsList){
                String skillName = playerConfig.getString(uuidString+".Binds."+attrName+".SkillName");

                int use = playerConfig.getInt(uuidString+".Binds."+attrName+".UseLevel");
                point_Map.put(attrName+"_skillName",skillName);
                point_Map.put(attrName+"_use",String.valueOf(use));
            }
        }

    }

    /**設置綁定技能動作**/
    public void setAction(Player player){
        String uuidString = player.getUniqueId().toString();

        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        for (int i = 1 ;i < 9 ;i++){
            String bindSkill = playerConfig.getString(uuidString+".Binds."+i+".SkillName");
            if(!(bindSkill.equals("null"))){
                int key = i -1;
                File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+bindSkill+".yml");
                FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                List<String> skillAction = skillConfig.getStringList(bindSkill+".Action");
                PlayerDataMap.skill_Key_Map.put(uuidString+"."+key,skillAction);
            }

        }
    }

}
