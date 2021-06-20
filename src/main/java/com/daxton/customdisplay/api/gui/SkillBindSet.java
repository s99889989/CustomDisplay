package com.daxton.customdisplay.api.gui;

import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillBindSet {


    public static void setBindInventory(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, List<String> sort, Inventory inventory){

        String uuidString = self.getUniqueId().toString();

        if(ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml") != null && PlayerManager.player_Data_Map.get(uuidString) != null){
            FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
            PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
            List<String> skillNameList = new ArrayList<>(playerConfig.getConfigurationSection(uuidString+".Skills").getKeys(false));
            List<String> useSkillList = new ArrayList<>();

            skillNameList.forEach(skillName -> {
                if(playerConfig.getInt(uuidString+".Skills."+skillName+".use") > 0){
                    useSkillList.add(skillName);
                }

            });

            //CustomDisplay.getCustomDisplay().getLogger().info("大小: "+skillNameList.size());
            List<Integer> pass = new ArrayList<>();
            pass.add(10);pass.add(11);pass.add(12);pass.add(13);pass.add(14);pass.add(15);pass.add(16);
            pass.add(19);pass.add(20);pass.add(21);pass.add(22);pass.add(23);pass.add(24);pass.add(25);
            pass.add(28);pass.add(29);pass.add(30);pass.add(31);pass.add(32);pass.add(33);pass.add(34);
            pass.add(37);pass.add(38);pass.add(39);pass.add(40);pass.add(41);pass.add(42);pass.add(43);

            List<Integer> pass2 = new ArrayList<>();
            pass2.add(45);pass2.add(46);pass2.add(47);pass2.add(48);pass2.add(49);pass2.add(50);pass2.add(51);pass2.add(52);

            int skillCount = 0;
            int skillBindCount = 0;
            for(int s = 0 ; s < 6 ; s++){

                String[] so = sort.get(s).split("\\.");
                int count = s * 9;

                for(int o = 0  ; o < 9 ; o++){
                    int rawslot = count + o;

                    if(rawslot == 4){
                        continue;
                    }

                    if(pass2.contains(rawslot)){
                        skillBindCount++;
                        String bindN = playerData.getBindName(String.valueOf(skillBindCount));
                        if(!bindN.equals("null")){
                            ItemStack itemStack = CustomButtomSet.setSkillButtom(self, target, bindN);
                            inventory.setItem(rawslot, itemStack);
                            continue;
                        }

                    }

                    if(pass.contains(rawslot)){

                        if(useSkillList.size() > 0 && skillCount < useSkillList.size() && skillCount < 44){
                            //CustomDisplay.getCustomDisplay().getLogger().info(""+rawslot+" : "+skillCount+" : "+skillNameList.size());
                            ItemStack itemStack = CustomButtomSet.setSkillButtom(self, target, useSkillList.get(skillCount));
                            if(itemStack != null){
                                inventory.setItem(rawslot, itemStack);
                                playerData.skill_Bind_Map.put(rawslot, useSkillList.get(skillCount));
                            }
                            skillCount++;
                        }

                        continue;
                    }

                    if(!so[o].equals(" ") && !so[o].isEmpty()){

                        ItemStack itemStack = CustomButtomSet.setButtom(self, target, itemConfig, so[o]);
                        if(itemStack != null){
                            inventory.setItem(rawslot, itemStack);
                        }
                    }
                }


            }
        }

    }

}
