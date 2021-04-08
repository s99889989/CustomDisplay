package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class OpenInventoryBindGui2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public OpenInventoryBindGui2(){

    }

    /**設定綁定**/
    public void setBind(Player player,int first,String skillNowName,String taskID){
        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            String uuidString = player.getUniqueId().toString();

            if(!skillNowName.isEmpty() && !playerData.binds_Map.values().contains(skillNowName)){
                int use = Integer.valueOf(playerData.skills_Map.get(skillNowName+"_use"));
                int key = first - 26;

                playerData.binds_Map.put(key+"_skillName",skillNowName);
                playerData.binds_Map.put(key+"_use",String.valueOf(use));

                Inventory inventory = ActionManager.taskID_Inventory_Map.get(taskID);
                ItemStack customItem = inventory.getItem(13);
                ItemMeta bindMeta = customItem.getItemMeta();
                String itemName = bindMeta.getDisplayName().replace("(綁定1)","").replace("(綁定2)","").replace("(綁定3)","").replace("(綁定4)","").replace("(綁定5)","").replace("(綁定6)","").replace("(綁定7)","").replace("(綁定8)","");

                bindMeta.setDisplayName(itemName+"(綁定"+key+")");
                customItem.setItemMeta(bindMeta);
                inventory.setItem(first,customItem);
            }

        }

    }

    /**移除綁定**/
    public void removeBind(Player player,int first,String taskID){
        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            String uuidString = player.getUniqueId().toString();

            int key = first - 26;

            playerData.binds_Map.put(key+"_skillName","null");
            playerData.binds_Map.put(key+"_use","0");

            Inventory inventory = ActionManager.taskID_Inventory_Map.get(taskID);
            ItemStack bindItem = new ItemStack(Material.BOOK);
            ItemMeta bindMeta = bindItem.getItemMeta();
            bindMeta.setDisplayName("§e綁定"+key);
            bindItem.setItemMeta(bindMeta);
            inventory.setItem(first,bindItem);

            //PlayerDataMap.skill_Key_Map.remove(uuidString+"."+key);

        }
    }

    /**打開綁定Gui**/
    public String openBindGui(Player player, LivingEntity target,String taskID,int next){
        String skillNowName = "";

        FileConfiguration guiConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Bind_Gui.yml");
        /**標題**/
        String title = guiConfig.getString("Skill_Bind_Gui.Gui_Title");

        ActionManager.taskID_Inventory_Map.put(taskID, Bukkit.createInventory(null,45 , title));
        ActionManager.playerUUID_taskID_Map.put(player.getUniqueId().toString(),taskID);
        Inventory inventory = ActionManager.taskID_Inventory_Map.get(taskID);
        String uuidString = player.getUniqueId().toString();

        UUID uuid = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        if(playerData != null){
            List<String> skillList = new ArrayList<>();
            Iterator skillKeyList = playerData.skills_Map.keySet().iterator();
            while(skillKeyList.hasNext()){
                String skillKey = (String) skillKeyList.next();
                if(skillKey.contains("_use")){
                    String skill = skillKey.replace("_use","");
                    if(!playerData.skills_Map.get(skillKey).equals("0")){
                        skillList.add(skill);
                    }
                }
            }
            if(skillList.size() > 0){
                int count = 0;
                for(int i = 9; i <= 16 ; i++){
                    count = next+(i-9);
                    while (count >= skillList.size()){
                        count = count - skillList.size();
                    }
                    while (count < 0){
                        count = count + skillList.size();
                    }
                    String skillName = skillList.get(count);
                    if(i == 13){
                        skillNowName = skillName;
                    }
                    try {
                        File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+skillName+".yml");
                        FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                        String itemMaterial = skillConfig.getString(skillName+".Material");
                        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                        List<String> itemLore = skillConfig.getStringList(skillName+".Lore");
                        List<String> lastItemLore = new ArrayList<>();
                        itemLore.forEach((line) -> {
                            lastItemLore.add(new ConversionMain().valueOf(player,target,line));
                        });
                        int cmd = skillConfig.getInt(skillName+".CustomModelData");
                        String itemName = skillConfig.getString(skillName+".Name");
                        itemName = new ConversionMain().valueOf(player,target,itemName);
                        ItemStack customItem = new ItemStack(material);
                        ItemMeta im = customItem.getItemMeta();
                        im.setDisplayName(itemName);
                        im.setCustomModelData(cmd);
                        im.setLore(lastItemLore);
                        customItem.setItemMeta(im);
                        inventory.setItem(i,customItem);
                    }catch (Exception exception){

                    }

                }
            }


            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.setOwningPlayer(player);
            playerHead.setItemMeta(skullMeta);

            ItemMeta headMeta = playerHead.getItemMeta();
            headMeta.setDisplayName("以下為目標");
            playerHead.setItemMeta(headMeta);

            inventory.setItem(4,playerHead);

            ItemStack selectItem = new ItemStack(Material.COAL);
            ItemMeta selectMeta = selectItem.getItemMeta();
            selectMeta.setDisplayName("§e選擇");
            List<String> selectLoreList = new ArrayList<>();
            selectLoreList.add("");
            selectLoreList.add("§e左鍵往左");
            selectLoreList.add("§e右鍵往右");
            selectMeta.setLore(selectLoreList);
            selectItem.setItemMeta(selectMeta);
            inventory.setItem(17,selectItem);

            for(int i = 1 ; i < 9 ; i++){
                String bind = playerData.binds_Map.get(i+"_skillName");
                if(bind != null){
                    if(bind.contains("null")){
                        ItemStack bindItem = new ItemStack(Material.BOOK);
                        ItemMeta bindMeta = bindItem.getItemMeta();
                        bindMeta.setDisplayName("§e綁定"+i);
                        bindItem.setItemMeta(bindMeta);
                        inventory.setItem(i+26,bindItem);
                    }else{
                        try{
                            File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+bind+".yml");
                            FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                            String itemMaterial = skillConfig.getString(bind+".Material");
                            Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                            List<String> itemLore = skillConfig.getStringList(bind+".Lore");
                            List<String> lastItemLore = new ArrayList<>();
                            itemLore.forEach((line) -> {
                                lastItemLore.add(new ConversionMain().valueOf(player,target,line));
                            });
                            int cmd = skillConfig.getInt(bind+".CustomModelData");
                            String itemName = skillConfig.getString(bind+".Name");
                            itemName = new ConversionMain().valueOf(player,target,itemName);
                            ItemStack bindItem = new ItemStack(material);
                            ItemMeta bindMeta = bindItem.getItemMeta();
                            bindMeta.setDisplayName(itemName+"(綁定"+i+")");
                            bindMeta.setCustomModelData(cmd);
                            bindMeta.setLore(lastItemLore);
                            bindItem.setItemMeta(bindMeta);
                            inventory.setItem(i+26,bindItem);
                        }catch (Exception exception){
                            ItemStack bindItem = new ItemStack(Material.BOOK);
                            ItemMeta bindMeta = bindItem.getItemMeta();
                            bindMeta.setDisplayName("§e綁定"+i);
                            bindItem.setItemMeta(bindMeta);
                            inventory.setItem(i+26,bindItem);
                        }


                    }
                }

            }
        }


        player.openInventory(inventory);
        return skillNowName;
    }

}
