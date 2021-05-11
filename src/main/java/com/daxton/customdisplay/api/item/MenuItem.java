package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.CustomDisplay;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
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

public class MenuItem {

    public MenuItem(){

    }

    public static ItemStack valueOf(FileConfiguration itemConfig, String itemID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        /**物品材質**/
        String itemMaterial = itemConfig.getString(itemID+".Material");

        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
        ItemStack itemStack = new ItemStack(material);

        /**物品損壞值**/
        int itemData = itemConfig.getInt(itemID+".Data");
        ItemMeta itemMeta0 = itemStack.getItemMeta();
        ((Damageable) itemMeta0).setDamage(itemData);
        itemStack.setItemMeta(itemMeta0);

        ItemMeta itemMeta = itemStack.getItemMeta();

        /**物品名稱**/
        String displayName = itemConfig.getString(itemID+".DisplayName");
        itemMeta.setDisplayName(displayName);

        /**物品CustomModelData**/
        int cmd = itemConfig.getInt(itemID+".CustomModelData");
        itemMeta.setCustomModelData(cmd);
        itemMeta.getPersistentDataContainer();



        /**物品附魔**/
        List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
        itemEnchantment.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 2){

                try {
                    //cd.getLogger().info(displayName+" : "+strings[0]);
                    NamespacedKey key = NamespacedKey.minecraft(strings[0]);
                    Enchantment enchant = Enchantment.getByKey(key);

                    //Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft("power"));

                    //Enchantment enchantment1 = Enchantment.getByName(strings[0]);

                    //Enchantment enchantment1 = Enchantment.getByKey(new NamespacedKey(cd,strings[0]));

                    itemMeta.addEnchant(enchant,Integer.valueOf(strings[1]),false);
                }catch (Exception exception){
                    //exception.printStackTrace();
                    //cd.getLogger().info("附魔錯誤");
                }

            }
        });




        /**物品屬性**/
        try {
            //List<String> attrList = new ArrayList<>(itemConfig.getConfigurationSection(itemID+".Attributes").getKeys(false));
            List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
            attrList.forEach(s -> {
                String[] strings = s.split(":");
                if(strings.length == 4){

                    String equipmentSlot = strings[0];
                    String inherit = strings[1];
                    String operation = strings[2];
                    double attrAmount = Double.parseDouble(strings[3]);
                    if(inherit != null && operation != null && attrAmount != 0 && equipmentSlot != null){
                        if(equipmentSlot.toLowerCase().contains("all")) {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation)));
                        }else {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation), Enum.valueOf(EquipmentSlot.class,equipmentSlot.toUpperCase())));
                        }
                    }
                }
            });
//            if(attrList != null){
//                attrList.forEach(attributesKey -> {
//                    //cd.getLogger().info(attributesKey);
//                    String inherit = itemConfig.getString(itemID+".Attributes."+attributesKey+".Inherit");
//                    String operation = itemConfig.getString(itemID+".Attributes."+attributesKey+".Operation");
//                    double attrAmount = itemConfig.getDouble(itemID+".Attributes."+attributesKey+".Amount");
//                    String equipmentSlot = itemConfig.getString(itemID+".Attributes."+attributesKey+".EquipmentSlot");
//                    if(inherit != null && operation != null && attrAmount != 0 && equipmentSlot != null){
//                        if(equipmentSlot.toLowerCase().contains("all")) {
//                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation)));
//                        }else {
//                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation), Enum.valueOf(EquipmentSlot.class,equipmentSlot.toUpperCase())));
//                        }
//                    }
//
//
//                });
//            }
        }catch (Exception exception){

        }

        /**設置無法破壞**/
        itemMeta.setUnbreakable(itemConfig.getBoolean(itemID+".Unbreakable"));

        /**設置物品隱藏顯示**/
        boolean hideAttributes = itemConfig.getBoolean(itemID+".ItemFlags.HideAttributes");
        boolean hideDestroys = itemConfig.getBoolean(itemID+".ItemFlags.HideDestroys");
        boolean hideDye = itemConfig.getBoolean(itemID+".ItemFlags.HideDye");
        boolean hideEnchants = itemConfig.getBoolean(itemID+".ItemFlags.HideEnchants");
        boolean hidePlacedOn = itemConfig.getBoolean(itemID+".ItemFlags.HidePlacedOn");
        boolean hidePotionEffects = itemConfig.getBoolean(itemID+".ItemFlags.HidePotionEffects");
        boolean hideUnbreakable = itemConfig.getBoolean(itemID+".ItemFlags.HideUnbreakable");
        if(hideAttributes){
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        if(hideDestroys){
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        }
        if(hideDye){
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        }
        if(hideEnchants){
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if(hidePlacedOn){
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        }
        if(hidePotionEffects){
            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }
        if(hideUnbreakable){
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        int coolDown = itemConfig.getInt(itemID+".CoolDown.RightClick");
        if(coolDown > 0){
            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            NamespacedKey xd = new NamespacedKey(cd, "CoolDownRightClick");
            data.set(xd , PersistentDataType.STRING, String.valueOf(coolDown));
        }

        /**物品Lore**/
        List<String> itemLore = itemConfig.getStringList(itemID+".Lore");
        List<String> nextItemLore = new ArrayList<>();
        itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
        itemMeta.setLore(itemLore);

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



        itemStack.setItemMeta(itemMeta);

//        String ss = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "CoolDown"), PersistentDataType.STRING);
//        if(ss != null){
//            cd.getLogger().info(ss);
//        }


        if(itemMaterial.contains("PLAYER_HEAD")){
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            String headValue = itemConfig.getString(itemID+".HeadValue");
            if(headValue != null){
                if(headValue.length() < 50){
                    //headValue = new ConversionMain().valueOf(self,target,headValue);
                    OfflinePlayer targetPlayer = cd.getServer().getOfflinePlayer(headValue);
                    skullMeta.setOwningPlayer(targetPlayer);
                    itemStack.setItemMeta(skullMeta);
                }else {
                    try {
                        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
                        playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
                        skullMeta.setPlayerProfile(playerProfile);
                        itemStack.setItemMeta(skullMeta);
                    }catch (Exception exception){
                        cd.getLogger().info("頭的值只能在paper伺服器使用。");
                        cd.getLogger().info("The value of the header can only be used on the paper server.");
                    }
                }

            }
        }

        return itemStack;
    }



}
