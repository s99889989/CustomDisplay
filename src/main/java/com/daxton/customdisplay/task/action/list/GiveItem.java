package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class GiveItem {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";


    public GiveItem(){



    }

    public void setItem(LivingEntity self, LivingEntity target, String firstString, String taskID){

        /**獲得物品的ID**/
        String itemID = new StringFind().getKeyValue(self,target,firstString,"[];","itemid=");

        /**獲得物品的數量**/
        int amount = 1;
        try {
            String amountString = new StringFind().getKeyValue(self,target,firstString,"[];","amount=","a=");
            if(!(amountString.equals("null"))){
                amount = Integer.valueOf(amountString);
            }
        }catch (NumberFormatException exception){
            cd.getLogger().info("物品的amount必須放整數數字。");
            cd.getLogger().info("The amount of the item must be an integer number.");
        }

        /**獲得目標**/
        String aims = new StringFind().getKeyValue(self,target,firstString,"[]; ","@=");

        if(aims.toLowerCase().contains("target")){
            if(target instanceof Player){
                Player player = (Player) self;
                giveItem(player,itemID,amount);
            }
        }else if(aims.toLowerCase().contains("self")){
            if(self instanceof Player){
                Player player = (Player) self;
                giveItem(player,itemID,amount);
            }
        }else {
            if(self instanceof Player){
                Player player = (Player) self;
                giveItem(player,itemID,amount);
            }
        }



    }

    public void giveItem(Player player,String itemID,int amount){
        File file = new File(cd.getDataFolder(),"Items/Default.yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
        /**物品材質**/
        String itemMaterial = itemConfig.getString(itemID+".Material");

        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        /**物品名稱**/
        String itemName = itemConfig.getString(itemID+".DisplayName");
        itemMeta.setDisplayName(itemName);

        /**物品Lore**/
        List<String> itemLore = itemConfig.getStringList(itemID+".Lore");
        List<String> nextItemLore = new ArrayList<>();
        itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
        List<String> lastItemLore = new ArrayList<>();
        nextItemLore.forEach((line) -> { lastItemLore.add(ChatColor.GRAY + new ConversionMain().valueOf(self,target,line)); });
        itemMeta.setLore(lastItemLore);

        /**物品屬性**/
        String attrInheritString = itemConfig.getString(itemID+".Attributes.Inherit");
        String attributesName = itemConfig.getString(itemID+".Attributes.Name");
        double attributesAmount = itemConfig.getDouble(itemID+".Attributes.Amount");
        String attrOpString = itemConfig.getString(itemID+".Attributes.Operation");
        String attrEqString = itemConfig.getString(itemID+".Attributes.EquipmentSlot");
        if(attrInheritString != null && attributesName != null && attributesAmount != 0 && attrOpString != null && attrEqString != null){
            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrInheritString),new AttributeModifier(UUID.randomUUID(), attributesName, attributesAmount, Enum.valueOf(AttributeModifier.Operation.class,attrOpString), Enum.valueOf(EquipmentSlot.class,attrEqString)));
        }

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


        itemStack.setItemMeta(itemMeta);
        List<ItemStack> itemStackList = new ArrayList<>();
        for(int i = 0 ; i < amount ;i++){
            itemStackList.add(itemStack);
        }

        player.getInventory().addItem(itemStackList.toArray(new ItemStack[itemStackList.size()]));

    }




}
