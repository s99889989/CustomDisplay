package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public GiveItem2(){

    }

    public void setItem(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        cd.getLogger().info("物品");
        /**獲得物品的ID**/
        String itemID = customLineConfig.getString(new String[]{"itemid"},"",self,target);

        /**獲得物品的數量**/
        int amount = customLineConfig.getInt(new String[]{"amount","a"},1,self,target);

        if(self instanceof Player){
            Player player = (Player) self;
            giveItem(player, target, itemID, amount);
        }


    }

    public void giveItem(Player player, LivingEntity target,String itemID,int amount){

        ItemStack itemStack = CustomItem.valueOf(player, target, itemID,amount);

        player.getInventory().addItem(itemStack);


    }




}
