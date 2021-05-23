package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomItem2 {

    public static ItemStack valueOf(LivingEntity self, LivingEntity target, String itemInputID, int amount){
        ItemStack newItemStack = new ItemStack(Material.STONE, amount);
        if(itemInputID.contains(".")){
            String[] strings = itemInputID.split("\\.");
            if(strings.length == 2){
                if(ConfigMapManager.getFileConfigurationMap().containsKey("Items_item_"+strings[0]+".yml")){
                    String itemID = strings[1];
                    FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+strings[0]+".yml");
                    if(itemConfig.contains(itemID+".Material")){
                        //設定物品材質
                        newItemStack = setMaterial(itemConfig, itemID, amount);
                        //物品元數據
                        ItemMeta itemMeta = newItemStack.getItemMeta();
                        //設定物品顯示名稱
                        if(itemConfig.contains(itemID+".DisplayName")){
                            setDisplayName(self, target, itemConfig, itemID, newItemStack);
                        }
                        //設定物品Lore
                        if(itemConfig.contains(itemID+".Lore")){
                            setLore(self, target, itemConfig, itemID, newItemStack);
                        }
                        //設定物品損壞Data
                        if(itemConfig.contains(itemID+".Data")){
                            setData(itemConfig, itemID, newItemStack);
                        }
                        //設定物品CustomModelData
                        if(itemConfig.contains(itemID+".CustomModelData")){
                            setCustomModelData(itemConfig, itemID, newItemStack);
                        }
                        //設定物品附魔
                        if(itemConfig.contains(itemID+".Enchantments")){
                            setEnchantments(itemConfig, itemID, newItemStack);
                        }
                        //設定物品原生屬性
                        if(itemConfig.contains(itemID+".Attributes")){
                            setAttributes(itemConfig, itemID, newItemStack);
                        }
                        //設定物品不會損壞
                        if(itemConfig.contains(itemID+".Unbreakable")){
                            setUnbreakable(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏屬性
                        if(itemConfig.contains(itemID+".ItemFlags.HideAttributes")){
                            setItemFlagsHideAttributes(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏可破壞的方塊
                        if(itemConfig.contains(itemID+".ItemFlags.HideDestroys")){
                            setItemFlagsHideDestroys(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏染料
                        if(itemConfig.contains(itemID+".ItemFlags.HideDye")){
                            setItemFlagsHideDye(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏附魔
                        if(itemConfig.contains(itemID+".ItemFlags.HideEnchants")){
                            setItemFlagsHideEnchants(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏可放置的方塊
                        if(itemConfig.contains(itemID+".ItemFlags.HidePlacedOn")){
                            setItemFlagsHidePlacedOn(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏藥水效果
                        if(itemConfig.contains(itemID+".ItemFlags.HidePotionEffects")){
                            setItemFlagsHidePotionEffects(itemConfig, itemID, newItemStack);
                        }
                        //設定物品隱藏無法破壞
                        if(itemConfig.contains(itemID+".ItemFlags.HideUnbreakable")){
                            setItemFlagsHideUnbreakable(itemConfig, itemID, newItemStack);
                        }
                        //設定物品的動作
                        if(itemConfig.contains(itemID+".Action")){
                            setAction(itemConfig, itemID, newItemStack);
                        }
                        //設定物品的頭值
                        if(itemConfig.contains(itemID+".HeadValue")){
                            setHeadValue(self, target, itemConfig, itemID, newItemStack);
                        }
                        //設定物品的右鍵CD
                        if(itemConfig.contains(itemID+".CoolDown.RightClick")){
                            setCoolDownRightClick(itemConfig, itemID, newItemStack);
                        }
                        //設定物品的左鍵CD
                        if(itemConfig.contains(itemID+".CoolDown.LeftClick")){
                            setCoolDownLeftClick(itemConfig, itemID, newItemStack);
                        }
                        //設定物品禁止攻擊
                        if(itemConfig.contains(itemID+".DisableAttack")){
                            setDisableAttack(itemConfig, itemID, newItemStack);
                        }
                        //設定物品的自訂屬性
                        if(itemConfig.contains(itemID+".CustomAttrs")){
                            setCustomAttrs(itemConfig, itemID, newItemStack);
                        }
                        //設置物品ID
                        setID(itemConfig, strings[0], itemID, newItemStack);
                    }
                }
            }
        }
        return newItemStack;
    }

    //設定物品的ID
    public static void setID(FileConfiguration itemConfig, String patch, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(cd, "itemID");
        data.set(xd , PersistentDataType.STRING, patch+"."+itemID);

        newItemStack.setItemMeta(itemMeta);
    }

    //設定物品附魔
    public static void setCustomAttrs(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemCustomAttrs = itemConfig.getStringList(itemID+".CustomAttrs");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        List<String> itemLore = new ArrayList<>();
        if(itemMeta.getLore() != null){
            itemLore = itemMeta.getLore();
        }

        for(String s : itemCustomAttrs){
            String[] strings = s.split(":");
            if(strings.length == 2){
//                String left = strings[0].trim();
//                String right = strings[1].trim();
                itemLore.add(s);

//                try {
//                    NamespacedKey key = NamespacedKey.minecraft(strings[0]);
//                    Enchantment enchant = Enchantment.getByKey(key);
//                    itemMeta.addEnchant(enchant,Integer.valueOf(strings[1]),false);
//                }catch (Exception exception){
//                    //
//                }

            }
        }
        itemMeta.setLore(itemLore);

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品禁止攻擊
    public static void setDisableAttack(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        boolean disableAttack = itemConfig.getBoolean(itemID+".DisableAttack");
        if(disableAttack){
            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            NamespacedKey xd = new NamespacedKey(cd, "DisableAttack");
            data.set(xd , PersistentDataType.STRING, "true");
        }

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品的左鍵CD
    public static void setCoolDownLeftClick(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String coolDownString = itemConfig.getString(itemID+".CoolDown.LeftClick");
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(cd, "CoolDownLeftClick");
        data.set(xd , PersistentDataType.STRING, coolDownString);

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品的右鍵CD
    public static void setCoolDownRightClick(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        String coolDownString = itemConfig.getString(itemID+".CoolDown.RightClick");
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(cd, "CoolDownRightClick");
        data.set(xd , PersistentDataType.STRING, coolDownString);

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物的頭值
    public static void setHeadValue(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        Material material = newItemStack.getType();

        if(material == Material.PLAYER_HEAD){
            SkullMeta skullMeta = (SkullMeta) newItemStack.getItemMeta();
            String headValue = itemConfig.getString(itemID+".HeadValue");
            if(headValue != null){
                if(headValue.length() < 50){
                    headValue = ConversionMain.valueOf(self,target,headValue);
                    OfflinePlayer targetPlayer = cd.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                    newItemStack.setItemMeta(skullMeta);
                }else {
                    try {
                        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                        playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                        skullMeta.setPlayerProfile(playerProfile);
                        newItemStack.setItemMeta(skullMeta);
                    }catch (Exception exception){
                        cd.getLogger().info("頭的值只能在paper伺服器使用。");
                        cd.getLogger().info("The value of the header can only be used on the paper server.");
                    }
                }

            }
        }

    }
    //設定物品的動作
    public static void setAction(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        List<String> itemLore = new ArrayList<>();
        if(itemMeta.getLore() != null){
            itemLore = itemMeta.getLore();
        }


        List<String> actionList = itemConfig.getStringList(itemID+".Action");
        if(!actionList.isEmpty()){
            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            int i = 0;
            for(String action : actionList){
                i++;
                NamespacedKey xd = new NamespacedKey(cd, "Action"+i);
                String[] actionArray = action.split(":");
                if(actionArray.length == 2){
                    data.set(xd , PersistentDataType.STRING, actionArray[1]);
                    if(!actionArray[0].equals("null")){
                        itemLore.add(actionArray[0]);
                        itemMeta.setLore(itemLore);
                    }
                }
            }
        }

        newItemStack.setItemMeta(itemMeta);
    }

    //設定物品隱藏屬性
    public static void setItemFlagsHideAttributes(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideAttributes = itemConfig.getBoolean(itemID+".ItemFlags.HideAttributes");
        if(itemFlagsHideAttributes){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏可破壞的方塊
    public static void setItemFlagsHideDestroys(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideDestroys = itemConfig.getBoolean(itemID+".ItemFlags.HideDestroys");
        if(itemFlagsHideDestroys){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏染料
    public static void setItemFlagsHideDye(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideDye = itemConfig.getBoolean(itemID+".ItemFlags.HideDye");
        if(itemFlagsHideDye){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏附魔
    public static void setItemFlagsHideEnchants(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideEnchants = itemConfig.getBoolean(itemID+".ItemFlags.HideEnchants");
        if(itemFlagsHideEnchants){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏可放置的方塊
    public static void setItemFlagsHidePlacedOn(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHidePlacedOn = itemConfig.getBoolean(itemID+".ItemFlags.HidePlacedOn");
        if(itemFlagsHidePlacedOn){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏藥水效果
    public static void setItemFlagsHidePotionEffects(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHidePotionEffects = itemConfig.getBoolean(itemID+".ItemFlags.HidePotionEffects");
        if(itemFlagsHidePotionEffects){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏無法破壞
    public static void setItemFlagsHideUnbreakable(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideUnbreakable = itemConfig.getBoolean(itemID+".ItemFlags.HideUnbreakable");
        if(itemFlagsHideUnbreakable){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品不會損壞
    public static void setUnbreakable(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemUnbreakable = itemConfig.getBoolean(itemID+".Unbreakable");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setUnbreakable(itemUnbreakable);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品原生屬性
    public static void setAttributes(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
        attrList.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 4){
                String equipmentSlot = strings[0];
                String inherit = strings[1];
                String operation = strings[2];
                double attrAmount = Double.parseDouble(strings[3]);
                try {
                    if(inherit != null && operation != null && attrAmount != 0 && equipmentSlot != null){
                        if(equipmentSlot.toLowerCase().contains("all")) {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation)));
                        }else {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation), Enum.valueOf(EquipmentSlot.class,equipmentSlot.toUpperCase())));
                        }
                    }
                }catch (Exception exception){
                    //
                }
            }
        });

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品附魔
    public static void setEnchantments(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemEnchantment.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 2){
                try {
                    NamespacedKey key = NamespacedKey.minecraft(strings[0]);
                    Enchantment enchant = Enchantment.getByKey(key);
                    itemMeta.addEnchant(enchant,Integer.valueOf(strings[1]),false);
                }catch (Exception exception){
                    //
                }

            }
        });
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品CustomModelData
    public static void setCustomModelData(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        int itemCustomModelData = itemConfig.getInt(itemID+".CustomModelData");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setCustomModelData(itemCustomModelData);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品損壞Data
    public static void setData(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        int itemData = itemConfig.getInt(itemID+".Data");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        ((Damageable) itemMeta).setDamage(itemData);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品Lore
    public static void setLore(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemDisplayName = itemConfig.getStringList(itemID+".Lore");
        itemDisplayName = StringConversion.getStringList(self, target, itemDisplayName);
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setLore(itemDisplayName);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品顯示名稱
    public static void setDisplayName(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        String itemDisplayName = itemConfig.getString(itemID+".DisplayName");
        itemDisplayName = StringConversion.getString(self, target, itemDisplayName);
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setDisplayName(itemDisplayName);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品材質
    public static ItemStack setMaterial(FileConfiguration itemConfig, String itemID, int amount){
        ItemStack newItemStack;

        String itemMaterial = itemConfig.getString(itemID+".Material");
        try {
            Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
            newItemStack = new ItemStack(material, amount);
        }catch (Exception exception){
            newItemStack = new ItemStack(Material.STONE, amount);
        }
        return newItemStack;
    }

}
