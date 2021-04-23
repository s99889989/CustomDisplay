package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GiveItem3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public GiveItem3(){

    }

    public void setItem(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**獲得物品的ID**/
        String itemID = actionMapHandle.getString(new String[]{"itemid"},"");

        /**獲得物品的數量**/
        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

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
