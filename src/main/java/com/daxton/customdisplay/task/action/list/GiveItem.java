package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import jdk.nashorn.internal.objects.annotations.Property;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.enchantments.Enchantment.ARROW_DAMAGE;

public class GiveItem {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";


    public GiveItem(){



    }

    public void setItem(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;

        String book = "";


        /**獲得物品的ID**/
        String itemID = new StringFind().getKeyValue(self,target,firstString,"[];","itemid=");

        /**獲得物品的數量**/
        int amount = 1;
        try {
            String amountString = new StringFind().getKeyValue2(self,target,firstString,"[];","1","amount=","a=");
            if(!(amountString.equals("null"))){
                amount = Integer.valueOf(amountString);
            }
        }catch (NumberFormatException exception){
            cd.getLogger().info("物品的amount必須放整數數字。");
            cd.getLogger().info("The amount of the item must be an integer number.");
        }

        /**獲得目標**/

        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    giveItem(player,itemID,amount);
                }
            }
        }



    }

    public void giveItem(Player player,String itemID,int amount){
        cd.getLogger().info(player.getName()+" : "+itemID);

        ItemStack itemStack = CustomItem.valueOf(player, target, itemID,amount);

        player.getInventory().addItem(itemStack);
//        ConfigMapManager.getFileConfigurationNameMap().values().forEach(st -> {
//            if(st.contains("Items_")){
//                FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get(st);
//                itemConfig.getKeys(false).forEach(st1 -> {
//                    if(st1.equals(itemID)){
//
//
//
//                        /**物品材質**/
//                        String itemMaterial = itemConfig.getString(itemID+".Material");
//
//
//                        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
//                        ItemStack itemStack = new ItemStack(material,80);
//
//
//                        /**物品損壞值**/
//                        int itemData = itemConfig.getInt(itemID+".Data");
//                        ItemMeta itemMeta0 = itemStack.getItemMeta();
//                        ((Damageable) itemMeta0).setDamage(itemData);
//                        itemStack.setItemMeta(itemMeta0);
//
//                        ItemMeta itemMeta = itemStack.getItemMeta();
//
//                        /**物品名稱**/
//                        String itemName = itemConfig.getString(itemID+".DisplayName");
//                        itemName = new ConversionMain().valueOf(self,target,itemName);
//                        itemMeta.setDisplayName(itemName);
//
//
//
//
//                        /**物品CustomModelData**/
//                        int cmd = itemConfig.getInt(itemID+".CustomModelData");
//                        itemMeta.setCustomModelData(cmd);
//
//                        /**物品Lore**/
//                        List<String> itemLore = itemConfig.getStringList(itemID+".Lore");
//                        List<String> nextItemLore = new ArrayList<>();
//                        itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
//                        List<String> lastItemLore = new ArrayList<>();
//                        nextItemLore.forEach((line) -> { lastItemLore.add(ChatColor.GRAY + new ConversionMain().valueOf(self,target,line)); });
//                        itemMeta.setLore(lastItemLore);
//
//                        /**物品附魔**/
//                        List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
//                        itemEnchantment.forEach(s -> {
//                            String[] strings = s.split(":");
//                            if(strings.length == 2){
//                                Enchantment enchantment1 = Enchantment.getByName(strings[0]);
//                                //Enchantment enchantment1 = Enchantment.getByKey(new NamespacedKey(cd,strings[0]));
//                                itemMeta.addEnchant(enchantment1,Integer.valueOf(strings[1]),false);
//                            }
//                        });
//
//
//                        /**物品屬性**/
//                        try {
//                            List<String> attrList = new ArrayList<>(itemConfig.getConfigurationSection(itemID+".Attributes").getKeys(false));
//                            if(attrList != null){
//                                attrList.forEach(s -> {
//                                    itemConfig.getStringList(itemID+".Attributes."+s).forEach(s1 -> {
//                                        String[] attrValues = s1.split(":");
//                                        if(attrValues.length == 2){
//                                            if(s.toLowerCase().contains("all")){
//                                                itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER")));
//                                            }else {
//                                                itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER"), Enum.valueOf(EquipmentSlot.class,s.toUpperCase())));
//                                            }
//
//                                        }
//                                    });
//                                });
//                            }
//                        }catch (Exception exception){
//
//                        }
//
//                        /**設置無法破壞**/
//                        itemMeta.setUnbreakable(itemConfig.getBoolean(itemID+".Unbreakable"));
//
//                        /**設置無法附魔**/
//                        boolean flag = itemConfig.getBoolean(itemID+".HideItemFlags");
//                        if(flag){
//                            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
//                            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
//                        }
//
//
//                        itemStack.setItemMeta(itemMeta);
//
//                        if(itemMaterial.contains("PLAYER_HEAD")){
//                            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
//                            String headValue = itemConfig.getString(itemID+".HeadValue");
//                            if(headValue != null){
//                                if(headValue.length() < 50){
//                                    headValue = new ConversionMain().valueOf(self,target,headValue);
//                                    OfflinePlayer targetPlayer = player.getServer().getOfflinePlayer(headValue);
//                                    skullMeta.setOwningPlayer(targetPlayer);
//                                    itemStack.setItemMeta(skullMeta);
//                                }else {
//                                    try {
//                                        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
//                                        playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
//                                        skullMeta.setPlayerProfile(playerProfile);
//                                        itemStack.setItemMeta(skullMeta);
//                                    }catch (Exception exception){
//                                        cd.getLogger().info("頭的值只能在paper伺服器使用。");
//                                        cd.getLogger().info("The value of the header can only be used on the paper server.");
//                                    }
//                                }
//
//                            }
//                        }
//
//                        List<ItemStack> itemStackList = new ArrayList<>();
//                        for(int i = 0 ; i < amount ;i++){
//                            itemStackList.add(itemStack);
//                        }
//
//                        player.getInventory().addItem(itemStackList.toArray(new ItemStack[itemStackList.size()]));
//
//
//
//
//                        //player.sendMessage(s1);
//                    }
//                });
//            }
//
//        });




    }




}
