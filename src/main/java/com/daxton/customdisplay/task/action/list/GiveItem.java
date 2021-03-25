package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.other.SetValue;
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

        /**獲得物品的ID**/
        String itemID = new SetValue(self,target,firstString,"[];","","itemid=").getString();

        /**獲得物品的數量**/
        int amount = new SetValue(self,target,firstString,"[];","1","amount=","a=").getInt(1);


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


    }




}
