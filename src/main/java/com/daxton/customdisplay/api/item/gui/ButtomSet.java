package com.daxton.customdisplay.api.item.gui;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.action.SetActionMap;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ButtomSet {

    public ButtomSet(){

    }

    //動作類型顯示
    public static ItemStack actionType(String showName){
        ItemStack newItemStack = new ItemStack(Material.BOOK);

        ItemMeta itemMeta = newItemStack.getItemMeta();

        itemMeta.setDisplayName(showName);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);

        newItemStack.setItemMeta(itemMeta);

        return newItemStack;
    }

    //動作類型顯示
    public static ItemStack action(String showName, String actionType){
        ItemStack newItemStack = new ItemStack(Material.BOOK);

        ItemMeta itemMeta = newItemStack.getItemMeta();

        itemMeta.setDisplayName(showName);


        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Actions_"+actionType+".yml");
        List<String> actionLL = fileConfiguration.getStringList(showName+".Action");
//        Map<String, String> player_Item_Action_Map = SetActionMap.setClassAction(content);
        List<String> lore = new ArrayList<>();
        actionLL.forEach(s -> {
            lore.add("§f"+s);
        });
        itemMeta.setLore(lore);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);

        newItemStack.setItemMeta(itemMeta);

        return newItemStack;
    }

    public static ItemStack filters(String showName){
        ItemStack newItemStack = new ItemStack(Material.BOOK);

        ItemMeta itemMeta = newItemStack.getItemMeta();

        itemMeta.setDisplayName("§e"+showName);

        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Character_System_TargetFilters.yml");
        List<String> actionLL = fileConfiguration.getStringList(showName+".TargetFilters");
        List<String> lore = new ArrayList<>();
        actionLL.forEach(s -> {
            lore.add("§f"+s);
        });
        itemMeta.setLore(lore);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);

        newItemStack.setItemMeta(itemMeta);

        return newItemStack;
    }

    public static ItemStack getActionButtom(String buttomPath, int order, Map<String, String> player_Item_Action_Map){
        FileConfiguration itemMenuLanguageConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+ Config.getUseLanguage() +"_gui_itemedit.yml");
        //初始物品
        ItemStack customItem = setMaterial(itemMenuLanguageConfig, buttomPath);

        ItemMeta itemMeta = customItem.getItemMeta();

        String buttomDisplayName = itemMenuLanguageConfig.getString("Language."+buttomPath);
        List<String> buttomInfo = itemMenuLanguageConfig.getStringList("Language."+buttomPath+"Info");

        buttomDisplayName = buttomDisplayName.replace("{value}",String.valueOf(order+1));

        itemMeta.setDisplayName(buttomDisplayName);

        List<String> itemInfo = new ArrayList<>();

        if(player_Item_Action_Map == null){
            buttomInfo.forEach(s -> {
                itemInfo.add(s);
            });
        }else {
            ActionMapHandle actionMapHandle = new ActionMapHandle(player_Item_Action_Map);

            String actionShowName = actionMapHandle.getString(new String[]{"actiontype"}, "null");
            String[] actionShowNameArray = actionShowName.split(":");

            String useAction = actionMapHandle.getString(new String[]{"Action","a"}, "null");

            String count = actionMapHandle.getString(new String[]{"Count","c"}, "1");

            String countCp = actionMapHandle.getString(new String[]{"CountPeriod","cp"}, "10");

            String mark = actionMapHandle.getString(new String[]{"Mark","m"}, "null");

            String stop = actionMapHandle.getString(new String[]{"stop","s"}, "false");

            String targetkey = actionMapHandle.getString(new String[]{"targetkey"}, "null");

            String triggerkey = actionMapHandle.getString(new String[]{"triggerkey"}, "null");

            buttomInfo.forEach(s -> {
                s = s.replace("{actionName}",actionShowNameArray[0]).replace("{action}",useAction).replace("{count}",count).replace("{countperiod}",countCp);
                s = s.replace("{mark}",mark).replace("{stop}",stop).replace("{targetkey}",targetkey).replace("{triggerkey}",triggerkey);
                itemInfo.add(s);
            });
        }

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

    //基礎按鈕設定
    public static ItemStack getItemButtom(String buttomPath, String addValue){

        FileConfiguration itemMenuLanguageConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+ Config.getUseLanguage() +"_gui_itemedit.yml");

        //初始物品
        ItemStack customItem = setMaterial(itemMenuLanguageConfig, buttomPath);
        //物品損壞值
        customItem = setData(itemMenuLanguageConfig, buttomPath, customItem);
        //頭值
        customItem = setHeadValue(itemMenuLanguageConfig, buttomPath, customItem);

        ItemMeta im = customItem.getItemMeta();

        //移除物品的Flags
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        im.addItemFlags(ItemFlag.HIDE_DYE);

        String buttomDisplayName = itemMenuLanguageConfig.getString("Language."+buttomPath);
        List<String> buttomInfo = itemMenuLanguageConfig.getStringList("Language."+buttomPath+"Info");
        List<String> loreInfo = new ArrayList<>();
        buttomInfo.forEach(s -> {
            loreInfo.add(s.replace("{value}", addValue));
        });


        //物品的showName
        if(buttomDisplayName != null){
            im.setDisplayName(buttomDisplayName.replace("{value}", addValue));
        }

        //物品的Lore
        if(loreInfo.size() > 0){
            im.setLore(loreInfo);
        }

        customItem.setItemMeta(im);

        return customItem;
    }

    //初始物品
    public static ItemStack setMaterial(FileConfiguration itemMenuConfig, String buttomPath){
        String displayMaterial = itemMenuConfig.getString("Language."+buttomPath +"DM");
        Material material = Material.STONE;
        ItemStack customItem = new ItemStack(material);
        if(displayMaterial != null){
            String[] displayMaterialArray = displayMaterial.split(":");

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

            customItem = new ItemStack(material);

            ItemMeta im = customItem.getItemMeta();

            //物品的customModelData
            im.setCustomModelData(customModelData);
            customItem.setItemMeta(im);
        }



        return customItem;
    }

    //頭值
    public static ItemStack setHeadValue(FileConfiguration itemMenuConfig, String buttomPath, ItemStack itemStack){
        try {

            String headValue = itemMenuConfig.getString("Language."+buttomPath +"HeadValue");
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
    public static ItemStack setData(FileConfiguration itemMenuConfig, String buttomPath, ItemStack itemStack){
        int itemData = itemMenuConfig.getInt("Language."+buttomPath +"Data");
        if(itemData > 0){
            ItemMeta itemMeta0 = itemStack.getItemMeta();
            ((Damageable) itemMeta0).setDamage(itemData);
            itemStack.setItemMeta(itemMeta0);
        }
        return itemStack;
    }



}
