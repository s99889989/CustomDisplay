package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.api.action.SetActionMap;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerDataMethod {

    //初始化魔量
    public static double setDefaultMana(Player player) {

        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");

        double valueMana;

        try {
            String valueStringMana = ConversionMain.valueOf(player, null, coreConfig.getString("CoreAttribute.Max_Mana.formula"));
            valueMana = Double.parseDouble(valueStringMana);
        } catch (Exception exception) {
            valueMana = 0;
        }

        return valueMana;
    }
    //初始化回魔量
    public static double setDefaultManaReg(Player player) {
        FileConfiguration coreConfig = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
        double valueManaReg;

        try {
            String valueStringMana = ConversionMain.valueOf(player, null, coreConfig.getString("CoreAttribute.Mana_Regeneration.formula"));
            valueManaReg = Double.parseDouble(valueStringMana);
        } catch (Exception exception) {
            valueManaReg = 0;
        }


        return valueManaReg;
    }

    //設定技能動作
    public static void setSkillActionList(Player player, FileConfiguration playerConfig, List<Map<String, String>> player_Skill_Action_List_Map) {
        String uuidString = player.getUniqueId().toString();
        if(playerConfig.contains(uuidString+".Skills")){
            player_Skill_Action_List_Map.clear();
            Objects.requireNonNull(playerConfig.getConfigurationSection(uuidString + ".Skills")).getKeys(false).forEach(s -> {
                int skillLevel = playerConfig.getInt(uuidString+".Skills."+s+".level");
                if(skillLevel > 0){
                    String[] skillNameArray = s.split("\\|");
                    if(skillNameArray.length == 2){
                        if(ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml") != null){
                            FileConfiguration skillConig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+skillNameArray[0]+".yml");
                            boolean passiveSkill = skillConig.getBoolean("Skills."+skillNameArray[1]+".PassiveSkill");
                            if(passiveSkill){
                                List<String> skillActionList = skillConig.getStringList("Skills."+skillNameArray[1]+".Action");
                                if(skillActionList.size() > 0){
                                    //player.sendMessage(skillNameArray[1]);
                                    skillActionList.forEach(s1 -> player_Skill_Action_List_Map.add(SetActionMap.setClassAction(s1)));

                                }

                            }
                        }
                    }


                }

            });
        }

    }

}
