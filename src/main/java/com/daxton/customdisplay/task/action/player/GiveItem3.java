package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.PlayerEquipment2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class GiveItem3 {

    public GiveItem3(){

    }

    public static void setItem(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //獲得物品的ID
        String itemID = actionMapHandle.getString(new String[]{"itemid","iid"},null);

        //獲得物品的數量
        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        boolean remove = actionMapHandle.getBoolean(new String[]{"remove"},false);

        int cmd = actionMapHandle.getInt(new String[]{"custommodeldata","cmd"},0);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

//        if(cmd != 0){
//            if(self instanceof Player){
//                Player player2 = (Player) self;
//                ItemStack itemStack = player2.getInventory().getItemInMainHand();
//
//                ItemMeta itemMeta = itemStack.getItemMeta();
//                itemMeta.setCustomModelData(cmd);
//
//                itemStack.setItemMeta(itemMeta);
//                player2.getInventory().setItemInMainHand(itemStack);
//
//            }
//
//        }

        livingEntityList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                if(itemID != null){
                    if(remove){
                        removeItem(player, target, itemID, amount);
                    }else {
                        giveItem(player, target, itemID, amount);
                    }

                }
            }
        });



    }

    public static void removeItem(Player player, LivingEntity target,String inputItemID,int amount){
        Inventory inventory = player.getInventory();

        ItemStack itemStack = CustomItem2.valueOf(player, target, inputItemID,amount);
        inventory.removeItem(itemStack);
        PlayerEquipment2.itemList(player);
    }

    public static void giveItem(Player player, LivingEntity target,String itemID,int amount){

        ItemStack itemStack = CustomItem2.valueOf(player, target, itemID,amount);

        player.getInventory().addItem(itemStack);


    }




}
