package com.daxton.customdisplay.api.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomButtomSet {

    public static ItemStack setSkillButtom(LivingEntity self, LivingEntity target, String key){
        ItemStack customItem = null;
        String[] keyArray = key.split("\\|");

        if(keyArray.length == 2){

            String fileName = keyArray[0];
            FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_"+fileName+".yml");
            if(skillConfig != null){

                String skilName = keyArray[1];

                String itemMaterial = skillConfig.getString("Skills."+skilName+".Material");
                if(itemMaterial != null){
                    try {
                        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                        customItem = new ItemStack(material);
                    }catch (Exception exception){
                        customItem = new ItemStack(Material.STONE);
                    }
                }


                int cmd = skillConfig.getInt("Skills."+skilName+".CustomModelData");

                String itemName = skillConfig.getString("Skills."+skilName+".Name");

                itemName = ConversionMain.valueOf(self, target, itemName);

                List<String> itemLore = skillConfig.getStringList("Skills."+skilName+".Lore");

                List<String> itemLore2 = new ArrayList<>();

                itemLore.forEach(s -> itemLore2.add(ConversionMain.valueOf(self, target, s)));

                if(customItem != null){
                    ItemMeta im = customItem.getItemMeta();

                    im.setDisplayName(itemName);

                    im.setCustomModelData(cmd);

                    im.setLore(itemLore2);

                    customItem.setItemMeta(im);
                }


            }

        }


        return customItem;
    }

    public static ItemStack setButtom(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String key){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ItemStack customItem = null;

        if(key.contains("|")){
            String[] strings = key.split("\\|");
            if(strings.length == 2){
                if(ConfigMapManager.getFileConfigurationMap().get("Gui_Buttom_"+strings[0]+".yml") != null){
                    FileConfiguration buttomConfig = ConfigMapManager.getFileConfigurationMap().get("Gui_Buttom_"+strings[0]+".yml");
                    if(buttomConfig.contains("Buttons."+strings[1]+".Material")){
                        //cd.getLogger().info(strings[0]+" : "+strings[1]);
                        customItem = setButtom2(self, target, buttomConfig, strings[1]);
                    }
                }
            }
        }else {
            if(itemConfig.contains("Buttons."+key+".Material")){
                customItem = setButtom2(self, target, itemConfig, key);
            }
        }

        return customItem;

    }

    public static ItemStack setButtom2(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String key){
        ItemStack customItem;
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        int cmd = itemConfig.getInt("Buttons."+key+".CustomModelData");
        boolean flag = itemConfig.getBoolean("Buttons."+key+".RemoveItemFlags");
        String itemMaterial = itemConfig.getString("Buttons."+key+".Material");
        String itemName = itemConfig.getString("Buttons."+key+".Name");
        itemName = ConversionMain.valueOf(self, target, itemName);

        List<String> itemLore = itemConfig.getStringList("Buttons."+key+".Lore");
        List<String> nextItemLore = new ArrayList<>();
        itemLore.forEach((line) -> nextItemLore.add(ChatColor.GRAY + line));
        List<String> lastItemLore = new ArrayList<>();
        nextItemLore.forEach((line) -> lastItemLore.add(ChatColor.GRAY + ConversionMain.valueOf(self, target, line)));

        Material material = Material.STONE;
        if (itemMaterial != null) {
            try {
                material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
            }catch (Exception exception){
                //
            }

        }

        customItem = new ItemStack(material);

        if (itemMaterial != null && itemMaterial.contains("PLAYER_HEAD")) {
            SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
            String headValue = itemConfig.getString("Buttons." + key + ".HeadValue");
            if (headValue != null) {
                if (headValue.length() < 50) {
                    headValue = ConversionMain.valueOf(self, target, headValue);
                    OfflinePlayer targetPlayer = self.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                    customItem.setItemMeta(skullMeta);
                } else {
                    try {
                        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                        playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                        skullMeta.setPlayerProfile(playerProfile);
                        customItem.setItemMeta(skullMeta);
                    } catch (Exception exception) {
                        cd.getLogger().info("頭的值只能在paper伺服器使用。");
                        cd.getLogger().info("The value of the header can only be used on the paper server.");
                    }
                }

            }
        }


        ItemMeta im = customItem.getItemMeta();
        im.setDisplayName(itemName);
        im.setLore(lastItemLore);
        im.setCustomModelData(cmd);



        if(flag){
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            im.addItemFlags(ItemFlag.HIDE_DESTROYS);
            im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            im.addItemFlags(ItemFlag.HIDE_DYE);
        }



        customItem.setItemMeta(im);
        return customItem;
    }

}
