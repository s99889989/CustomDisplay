package com.daxton.customdisplay.api.item;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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

public class CustomItem {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public CustomItem(){

    }

    public static ItemStack valueOf(LivingEntity self, LivingEntity target, String itemInputID, int amount){

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        ItemStack newItemStack = new ItemStack(Material.SPONGE);
        if(itemInputID.contains(".")){
            String[] itemArray = itemInputID.split("\\.");
            if(itemArray.length == 2){

                String itemType = itemArray[0];
                String itemID = itemArray[1];
                //cd.getLogger().info(itemType+" : "+itemID);
                if(ConfigMapManager.getFileConfigurationMap().get("Items_item_"+itemType+".yml") != null){
                    FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get("Items_item_"+itemType+".yml");


                    /**物品材質**/
                    String itemMaterial = itemConfig.getString(itemID+".Material");

                    Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                    ItemStack itemStack = new ItemStack(material,amount);

                    /**物品損壞值**/
                    int itemData = itemConfig.getInt(itemID+".Data");
                    ItemMeta itemMeta0 = itemStack.getItemMeta();
                    ((Damageable) itemMeta0).setDamage(itemData);
                    itemStack.setItemMeta(itemMeta0);

                    ItemMeta itemMeta = itemStack.getItemMeta();

                    /**物品名稱**/
                    String itemName = itemConfig.getString(itemID+".DisplayName");
                    itemName = ConversionMain.valueOf(self,target,itemName);
                    itemMeta.setDisplayName(itemName);




                    /**物品CustomModelData**/
                    int cmd = itemConfig.getInt(itemID+".CustomModelData");
                    itemMeta.setCustomModelData(cmd);
                    itemMeta.getPersistentDataContainer();
                    /**物品Lore**/
                    List<String> itemLore = itemConfig.getStringList(itemID+".Lore");
                    List<String> nextItemLore = new ArrayList<>();
                    itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
                    List<String> lastItemLore = new ArrayList<>();
                    nextItemLore.forEach((line) -> { lastItemLore.add(ChatColor.GRAY + ConversionMain.valueOf(self,target,line)); });
                    itemMeta.setLore(lastItemLore);

                    /**物品附魔**/
                    List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
                    itemEnchantment.forEach(s -> {
                        String[] strings = s.split(":");
                        if(strings.length == 2){
                            Enchantment enchantment1 = Enchantment.getByName(strings[0]);
                            //Enchantment enchantment1 = Enchantment.getByKey(new NamespacedKey(cd,strings[0]));
                            itemMeta.addEnchant(enchantment1,Integer.valueOf(strings[1]),false);
                        }
                    });


                    /**物品屬性**/
                    try {
                        List<String> attrList = new ArrayList<>(itemConfig.getConfigurationSection(itemID+".Attributes").getKeys(false));
                        if(attrList != null){
                            attrList.forEach(attributesKey -> {
                                //cd.getLogger().info(attributesKey);
                                String inherit = itemConfig.getString(itemID+".Attributes."+attributesKey+".Inherit");
                                String operation = itemConfig.getString(itemID+".Attributes."+attributesKey+".Operation");
                                double attrAmount = itemConfig.getDouble(itemID+".Attributes."+attributesKey+".Amount");
                                String equipmentSlot = itemConfig.getString(itemID+".Attributes."+attributesKey+".EquipmentSlot");

                                if(equipmentSlot.toLowerCase().contains("all")) {
                                    itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation)));
                                }else {
                                    itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation), Enum.valueOf(EquipmentSlot.class,equipmentSlot.toUpperCase())));
                                }

                                //itemConfig.getStringList(itemID+".Attributes."+s).forEach(s1 -> {


//                                        String[] attrValues = s1.split(":");
//                                        if(attrValues.length == 2){
//                                            if(s.toLowerCase().contains("all")){
//                                                itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER")));
//                                            }else {
//                                                itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER"), Enum.valueOf(EquipmentSlot.class,s.toUpperCase())));
//                                            }
//
//                                        }

                                //});
                            });
                        }
                    }catch (Exception exception){

                    }

                    /**設置無法破壞**/
                    itemMeta.setUnbreakable(itemConfig.getBoolean(itemID+".Unbreakable"));

                    /**設置無法附魔**/
                    boolean flag = itemConfig.getBoolean(itemID+".HideItemFlags");
                    if(flag){
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
                        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
                    }

                    int coolDown = itemConfig.getInt(itemID+".CoolDown.RightClick");
                    if(coolDown > 0){

                        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                        NamespacedKey xd = new NamespacedKey(cd, "CoolDownRightClick");
                        data.set(xd , PersistentDataType.STRING, String.valueOf(coolDown));
                    }

                    List<String> actionList = itemConfig.getStringList(itemID+".Action");
                    if(!actionList.isEmpty()){
                        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                        int i = 0;
                        for(String action : actionList){
                            i++;
                            NamespacedKey xd = new NamespacedKey(cd, "Action"+i);
                            data.set(xd , PersistentDataType.STRING, action);
                        }


                    }



                    itemStack.setItemMeta(itemMeta);

                    if(itemMaterial.contains("PLAYER_HEAD")){
                        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                        String headValue = itemConfig.getString(itemID+".HeadValue");
                        if(headValue != null){
                            if(headValue.length() < 50){
                                headValue = ConversionMain.valueOf(self,target,headValue);
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

//                        if(self instanceof Player){
//                            Player player = (Player) self;
//                            player.sendMessage("You have recieved 1x TeleportScroll");
//                            cd.getLogger().info(data.get(xd, PersistentDataType.STRING));
//                        }
                    //String ss = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(cd, "data"), PersistentDataType.STRING);
                    //cd.getLogger().info("職: "+ss);
                    newItemStack = itemStack;


                }
            }
        }
//        for(String configString : ConfigMapManager.getFileConfigurationNameMap().values()){
//            if(configString.contains("Items_")){
//                FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get(configString);
//                for (String itemKey : itemConfig.getKeys(false)){
//                    if(itemKey.equals(itemID)){
//
//
//                    }
//                }
//
//            }
//        }

        return newItemStack;
    }



}
