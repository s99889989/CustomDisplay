package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.manager.ConfigMapManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MenuSet {

    public MenuSet(){

    }


    //從ItemMenu.yml獲取物品清單名稱
    public static String[] getItemMenuButtomNameArray(FileConfiguration itemMenuConfig, String buttomType){
        Set<String> set1 = itemMenuConfig.getConfigurationSection(buttomType).getKeys(false);
        String[] array1 = set1.toArray(new String[set1.size()]);
        return array1;
    }

    //從ItemMenu.yml獲取Menu標題
    public static String getMenuTitle(String menuType){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String buttomMessage = itemMenuConfig.getString(menuType+".MenuName");
        return buttomMessage;
    }

    //從ItemMenu.yml獲取按鈕訊息
    public static String getItemMenuMessage(String buttomType, String buttomName, String message){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String buttomMessage = itemMenuConfig.getString(buttomType+"."+ buttomName +"."+message);
        return buttomMessage;
    }

    //從ItemMenu.yml獲取按鈕顯示設定
    public static ItemStack getItemMenuButtom(String buttomType, String buttomName){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String displayMaterial = itemMenuConfig.getString(buttomType+"."+ buttomName +".DisplayMaterial");

        String[] displayMaterialArray = displayMaterial.split(":");

        Material material = Material.STONE;

        int customModelData = 0;
        if(displayMaterialArray.length == 2){
            try {
                material = Enum.valueOf(Material.class,displayMaterialArray[0].toUpperCase());
            }catch (Exception exception){

            }
            try {
                customModelData = Integer.parseInt(displayMaterialArray[1]);
            }catch (NumberFormatException exception){

            }
        }

        ItemStack customItem = new ItemStack(material);


        //物品損壞值
        int itemData = itemMenuConfig.getInt(buttomType+"."+ buttomName +".Data");
        if(itemData > 0){
            ItemMeta itemMeta0 = customItem.getItemMeta();
            ((Damageable) itemMeta0).setDamage(itemData);
            customItem.setItemMeta(itemMeta0);
        }

        try {

            String headValue = itemMenuConfig.getString(buttomType+"."+ buttomName +".HeadValue");
            if(headValue != null){
                SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
                if(headValue.length() < 50){

                    OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                }else {
                    PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                    playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                    skullMeta.setPlayerProfile(playerProfile);

                }
                customItem.setItemMeta(skullMeta);
            }

        }catch (Exception exception){

        }


        ItemMeta im = customItem.getItemMeta();

        //物品的customModelData
        im.setCustomModelData(customModelData);

        //物品的showName
        String showName = itemMenuConfig.getString(buttomType+"."+ buttomName +".ShowName");
        im.setDisplayName(showName);

        //物品的Lore
        List<String> itemLore = itemMenuConfig.getStringList(buttomType+"."+ buttomName +".Lore");
        im.setLore(itemLore);

        //移除物品的Flags
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.addItemFlags(ItemFlag.HIDE_DYE);

        customItem.setItemMeta(im);

        return customItem;
    }

    //從ItemMenu.yml獲取按鈕顯示設定
    public static ItemStack getItemMenuButtom2(String buttomType, String buttomName, String addValue){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        String displayMaterial = itemMenuConfig.getString(buttomType+"."+ buttomName +".DisplayMaterial");

        String[] displayMaterialArray = displayMaterial.split(":");

        Material material = Material.STONE;

        int customModelData = 0;
        if(displayMaterialArray.length == 2){
            try {
                material = Enum.valueOf(Material.class,displayMaterialArray[0].toUpperCase());
            }catch (Exception exception){

            }
            try {
                customModelData = Integer.parseInt(displayMaterialArray[1]);
            }catch (NumberFormatException exception){

            }
        }

        ItemStack customItem = new ItemStack(material);


        //物品損壞值
        int itemData = itemMenuConfig.getInt(buttomType+"."+ buttomName +".Data");
        if(itemData > 0){
            ItemMeta itemMeta0 = customItem.getItemMeta();
            ((Damageable) itemMeta0).setDamage(itemData);
            customItem.setItemMeta(itemMeta0);
        }

        try {

            String headValue = itemMenuConfig.getString(buttomType+"."+ buttomName +".HeadValue");
            if(headValue != null){
                SkullMeta skullMeta = (SkullMeta) customItem.getItemMeta();
                if(headValue.length() < 50){

                    OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                }else {
                    PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                    playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                    skullMeta.setPlayerProfile(playerProfile);

                }
                customItem.setItemMeta(skullMeta);
            }

        }catch (Exception exception){

        }


        ItemMeta im = customItem.getItemMeta();

        //物品的
        im.setCustomModelData(customModelData);

        //物品的
        String showName = itemMenuConfig.getString(buttomType+"."+ buttomName +".ShowName");
        showName = showName.replace("{value}", addValue);
        im.setDisplayName(showName);

        //物品的
        List<String> itemLore = itemMenuConfig.getStringList(buttomType+"."+ buttomName +".Lore");
        im.setLore(itemLore);

        //物品的
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.addItemFlags(ItemFlag.HIDE_DYE);

        customItem.setItemMeta(im);

        return customItem;
    }

}
