package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.Config;
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


import java.util.*;

public class MenuSet {

    public MenuSet(){

    }

    public static String getGuiTitle(String titleKey){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        String nowLanguage = cd.getConfigManager().config.getString("Language");
        FileConfiguration itemMenuLanguageConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+nowLanguage+"_gui_itemedit.yml");
        String titleName = itemMenuLanguageConfig.getString("Language.GuiTitle."+titleKey);
        if(titleName == null){
            titleName = "";
        }
        return titleName;
    }

    //從ItemMenu.yml獲取物品清單名稱
    public static String[] getItemMenuButtomNameArray(){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        Set<String> set1 = itemMenuConfig.getConfigurationSection("Items.Type").getKeys(false);
        String[] array1 = set1.toArray(new String[set1.size()]);
        return array1;
    }
    public static String[] getActionList(String typeName){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+ typeName +".yml");
        Set<String> set1 = itemMenuConfig.getConfigurationSection("").getKeys(false);
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
    public static String getItemMenuMessage(String buttomType, String buttomName, String messageType){

        FileConfiguration itemMenuLanguageConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+ Config.getUseLanguage() +"_gui_itemedit.yml");
        String buttomMessage = itemMenuLanguageConfig.getString("Language.Buttom."+buttomType+"."+buttomName+messageType);

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
    public static ItemStack getItemTypeButtom( String headType,String buttomType, String buttomName){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");

        //初始物品
        ItemStack customItem = setMaterial(itemMenuConfig, headType, buttomType, buttomName);
        //物品損壞值
        customItem = setData(itemMenuConfig, headType, buttomType, buttomName, customItem);
        //頭值
        customItem = setHeadValue(itemMenuConfig, headType, buttomType, buttomName, customItem);

        ItemMeta im = customItem.getItemMeta();

        //移除物品的Flags
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.addItemFlags(ItemFlag.HIDE_DYE);


        String buttomDisplayName = itemMenuConfig.getString(headType+"."+buttomType+"."+buttomName+".ShowName");
        List<String> buttomInfo = itemMenuConfig.getStringList(headType+"."+buttomType+"."+buttomName+".Lore");

        //物品的showName
        if(buttomDisplayName != null){
            im.setDisplayName(buttomDisplayName);
        }

        //物品的Lore
        if(buttomInfo.size() > 0){
            im.setLore(buttomInfo);
        }
        customItem.setItemMeta(im);

        return customItem;
    }

    //從ItemMenu.yml獲取Lore按鈕顯示設定
    public static ItemStack getLoreButtom( String headType,String buttomType, String buttomName, int order, String message){
        FileConfiguration itemMenuConfig = ConfigMapManager.getFileConfigurationMap().get("Items_ItemMenu.yml");
        //初始物品
        ItemStack customItem = setMaterial(itemMenuConfig, headType, buttomType, buttomName);

        ItemMeta itemMeta = customItem.getItemMeta();

        FileConfiguration itemMenuLanguageConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+ Config.getUseLanguage() +"_gui_itemedit.yml");
        String buttomDisplayName = itemMenuLanguageConfig.getString("Language.Buttom."+buttomType+"."+buttomName);
        List<String> buttomInfo = itemMenuLanguageConfig.getStringList("Language.Buttom."+buttomType+"."+buttomName+"Info");

        buttomDisplayName = buttomDisplayName.replace("{value}",String.valueOf(order+1));

        itemMeta.setDisplayName(buttomDisplayName);

        List<String> itemInfo = new ArrayList<>();
        buttomInfo.forEach(s -> {
            itemInfo.add(s.replace("{value}",message));
        });

        itemMeta.setLore(itemInfo);

        //移除物品的Flags
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);

        customItem.setItemMeta(itemMeta);

        return customItem;
    }

    //頭值
    public static ItemStack setHeadValue(FileConfiguration itemMenuConfig, String headType, String buttomType, String buttomName, ItemStack itemStack){
        try {

            String headValue = itemMenuConfig.getString(headType+"."+buttomType+"."+ buttomName +".HeadValue");
            if(headValue != null){
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                if(headValue.length() < 50){

                    OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                }else {
                    PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                    playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                    skullMeta.setPlayerProfile(playerProfile);

                }
                itemStack.setItemMeta(skullMeta);
            }

        }catch (Exception exception){

        }

        return itemStack;
    }
    //物品損壞值
    public static ItemStack setData(FileConfiguration itemMenuConfig, String headType, String buttomType, String buttomName, ItemStack itemStack){
        int itemData = itemMenuConfig.getInt(headType+"."+buttomType+"."+ buttomName +".Data");
        if(itemData > 0){
            ItemMeta itemMeta0 = itemStack.getItemMeta();
            ((Damageable) itemMeta0).setDamage(itemData);
            itemStack.setItemMeta(itemMeta0);
        }
        return itemStack;
    }

    //初始物品
    public static ItemStack setMaterial(FileConfiguration itemMenuConfig, String headType, String buttomType, String buttomName){
        String displayMaterial = itemMenuConfig.getString(headType+"."+buttomType+"."+ buttomName +".DisplayMaterial");

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

        ItemMeta im = customItem.getItemMeta();

        //物品的customModelData
        im.setCustomModelData(customModelData);
        customItem.setItemMeta(im);


        return customItem;
    }

}
