package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Holographic {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private String firstString = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private Hologram hologram;

    private Location createLocation = new Location(Bukkit.getWorld("world"),0,0,0);

    private String function = "";
    private String message = "";
    private String aims = "";
    private double x = 0;
    private double y = 0;
    private double z = 0;


    public Holographic(){

    }

    public void setHD(LivingEntity self, LivingEntity target, String firstString,String taskID) {
        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.firstString = firstString;
        if(target == null){
            this.target = (LivingEntity) new EntityFind().getTarget(self,10);

        }
        if(setOther()){
            return;
        }

    }

    public boolean setOther(){
        boolean ret = false;
        aims = "";
        function = "";
        message = "";
        x = 0;
        y = 0;
        z = 0;
        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("message=") || string.toLowerCase().contains("m=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    message = new ConversionMain().valueOf(self,target,strings[1]);
                }
            }
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("x=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            x = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            x = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("x不是數字");
                    }
                }
            }

            if(string.toLowerCase().contains("y=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            y = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            y = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("y不是數字"+y);
                    }
                }
            }

            if(string.toLowerCase().contains("z=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        if(strings[1].contains("&")){
                            z = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                        }else {
                            z = Double.valueOf(strings[1]);
                        }
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("z不是數字");
                    }
                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            if(target == null){
                ret = true;
            }else {
                createLocation = target.getLocation().add(x,y,z);
            }
        }else if(aims.toLowerCase().contains("self")){
            createLocation = self.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            createLocation = new Location(self.getWorld(),x,y,z);
        }else {
            createLocation = createLocation.add(x,y,z);
        }
        if(!(ret)){
            if(function.toLowerCase().contains("create") && hologram == null){
                createHD();
            }
            if(function.toLowerCase().contains("addtextline") && hologram != null){
                addLineHD();
            }
            if(function.toLowerCase().contains("additemline") && hologram != null){
                addItemHD();
            }
            if(function.toLowerCase().contains("removetextline") && hologram != null){
                removeLineHD();
            }
            if(function.toLowerCase().contains("teleport") && hologram != null){
                teleportHD();
            }
            if(function.toLowerCase().contains("delete") && hologram != null){
                deleteHD();
            }
        }

        return ret;
    }


    public void createHD(){
        hologram = HologramsAPI.createHologram(cd, createLocation);
        hologram.appendTextLine(message);
    }

    public void addLineHD(){
        hologram.appendTextLine(message);
    }

    public void addItemHD(){
        if(self instanceof Player){
            Player player = (Player) self;
            ItemStack newItemStack = giveItem(player,target, "LawTown");
            hologram.appendItemLine(newItemStack);
        }


    }

    public void removeLineHD(){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            cd.getLogger().info("移除內容錯誤");
        }
    }

    public void teleportHD(){
        hologram.teleport(createLocation);
    }

    public void deleteHD(){
        new ClearAction(taskID);
        if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
            ActionManager.getJudgment2_Holographic2_Map().remove(taskID);
        }
        hologram.delete();
    }

    public Hologram getHologram() {
        return hologram;
    }


    public ItemStack giveItem(Player player,LivingEntity target, String itemID){
        ItemStack newItemStack = new ItemStack(Material.SPONGE);

       for(String configString : ConfigMapManager.getFileConfigurationNameMap().values()){
           if(configString.contains("Items_")){
               FileConfiguration itemConfig = ConfigMapManager.getFileConfigurationMap().get(configString);
               for (String itemKey : itemConfig.getKeys(false)){
                   if(itemKey.equals(itemID)){

                       /**物品材質**/
                       String itemMaterial = itemConfig.getString(itemID+".Material");

                       Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
                       ItemStack itemStack = new ItemStack(material);

                       ItemMeta itemMeta = itemStack.getItemMeta();

                       /**物品名稱**/
                       String itemName = itemConfig.getString(itemID+".DisplayName");
                       itemName = new ConversionMain().valueOf(player,target,itemName);
                       itemMeta.setDisplayName(itemName);




                       /**物品CustomModelData**/
                       int cmd = itemConfig.getInt(itemID+".CustomModelData");
                       itemMeta.setCustomModelData(cmd);

                       /**物品Lore**/
                       List<String> itemLore = itemConfig.getStringList(itemID+".Lore");
                       List<String> nextItemLore = new ArrayList<>();
                       itemLore.forEach((line) -> { nextItemLore.add(ChatColor.GRAY + line); });
                       List<String> lastItemLore = new ArrayList<>();
                       nextItemLore.forEach((line) -> { lastItemLore.add(ChatColor.GRAY + new ConversionMain().valueOf(self,target,line)); });
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
                               attrList.forEach(s -> {
                                   itemConfig.getStringList(itemID+".Attributes."+s).forEach(s1 -> {
                                       String[] attrValues = s1.split(":");
                                       if(attrValues.length == 2){
                                           if(s.toLowerCase().contains("all")){
                                               itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER")));
                                           }else {
                                               itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,attrValues[0].toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), Double.valueOf(attrValues[1]), Enum.valueOf(AttributeModifier.Operation.class,"ADD_NUMBER"), Enum.valueOf(EquipmentSlot.class,s.toUpperCase())));
                                           }

                                       }
                                   });
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


                       itemStack.setItemMeta(itemMeta);

                       if(itemMaterial.contains("PLAYER_HEAD")){
                           SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                           String headValue = itemConfig.getString(itemID+".HeadValue");
                           if(headValue != null){
                               if(headValue.length() < 50){
                                   headValue = new ConversionMain().valueOf(player,target,headValue);
                                   OfflinePlayer targetPlayer = player.getServer().getOfflinePlayer(headValue);
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


                       newItemStack = itemStack;

                   }
               }

           }
       }




        return newItemStack;

    }


}
