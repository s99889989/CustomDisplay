package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
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

public class GiveItem {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private String function = "";
    private String message = "";
    private String ItemID = "";
    private int amount = 27;

    public GiveItem(){



    }

    public void setItem(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        cd.getLogger().info(firstString);
        setOther();
        if(self instanceof Player){
            Player player = (Player) self;
            giveItem(player);
        }

    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("function=") || allString.toLowerCase().contains("fc=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    function = strings[1];

                }
            }

            if(allString.toLowerCase().contains("itemid=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    ItemID = strings[1];
                }
            }


            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("amount=內只能放整數數字");
                    }
                }
            }
        }
    }

    public void giveItem(Player player){
        File file = new File(cd.getDataFolder(),"Items/Default.yml");
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(file);
        /**物品材質**/
        String itemMaterial = itemConfig.getString(ItemID+".Material");

        Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        /**物品名稱**/
        String itemName = itemConfig.getString(ItemID+".DisplayName");
        itemMeta.setDisplayName(itemName);

        /**物品Lore**/
        List<String> itemLore = itemConfig.getStringList(ItemID+".Lore");
        List<String> nextItemLore = new ArrayList<>();
        itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
        List<String> lastItemLore = new ArrayList<>();
        nextItemLore.forEach((line) -> { lastItemLore.add(ChatColor.GRAY + new StringConversion(self,target,line,"Character").valueConv()); });
        itemMeta.setLore(lastItemLore);

        /**物品屬性**/
        String attrInheritString = itemConfig.getString(ItemID+".Attributes.Inherit");
        String attributesName = itemConfig.getString(ItemID+".Attributes.Name");
        double attributesAmount = itemConfig.getDouble(ItemID+".Attributes.Amount");
        String attrOpString = itemConfig.getString(ItemID+".Attributes.Operation");
        String attrEqString = itemConfig.getString(ItemID+".Attributes.EquipmentSlot");
        if(attrInheritString != null && attributesName != null && attributesAmount != 0 && attrOpString != null && attrEqString != null){
            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrInheritString),new AttributeModifier(UUID.randomUUID(), attributesName, attributesAmount, Enum.valueOf(AttributeModifier.Operation.class,attrOpString), Enum.valueOf(EquipmentSlot.class,attrEqString)));
        }

        boolean flag = itemConfig.getBoolean(ItemID+".HideItemFlags");
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

        player.getInventory().addItem(itemStack);
    }




}
