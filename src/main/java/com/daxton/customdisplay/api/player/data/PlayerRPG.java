package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.player.CoreAttribute;
import com.daxton.customdisplay.api.player.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class PlayerRPG {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerRPG(){

    }

    public void setPlayerRPG(Player player){
        String uuidString = player.getUniqueId().toString();
        new PlayerBukkitAttribute().setDefault(player);
        PlayerDataMap.getCore_Attribute_Map().put(uuidString,new CoreAttribute(player));
        PlayerDataMap.getCore_Attribute_Map().get(uuidString).setDefault();

        File customCoreFile = new File(cd.getDataFolder(),"Class/CustomCore.yml");
        FileConfiguration customCoreConfig = YamlConfiguration.loadConfiguration(customCoreFile);
        String attackSpeedString = customCoreConfig.getString("Formula.Attack_Speed.Speed");
        attackSpeedString = new ConversionMain().valueOf(player,null,attackSpeedString);
        int  attackSpeed = 10;
        try {
            double number = Arithmetic.eval(attackSpeedString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            attackSpeed = Integer.valueOf(numberDec);
        }catch (Exception exception){
            attackSpeed = 10;
        }
        PlayerDataMap.attack_Count_Map.put(uuidString,attackSpeed);
        PlayerDataMap.cost_Count_Map.put(uuidString,0);
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
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
