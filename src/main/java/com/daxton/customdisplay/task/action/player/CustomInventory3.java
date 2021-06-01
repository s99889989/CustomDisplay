package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.gui.CustomGuiSet;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.player.EditorGUIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


import java.util.*;

public class CustomInventory3 {

    public CustomInventory3(){


    }

    public static void setInventory(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        //CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //獲得功能
        String function = actionMapHandle.getString(new String[]{"function","fc"},"");
        //獲得GUIID
        String GuiID = actionMapHandle.getString(new String[]{"guiid"},"Default");
        //獲得數量
        int amount =  actionMapHandle.getInt(new String[]{"amount","a"},27);
        //獲得內容
        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListSelf();

        targetList.forEach(livingEntity -> {

            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;

                if(function.toLowerCase().contains("gui")){
                    openGui(player, target, GuiID);
                }else if(function.toLowerCase().contains("close")){
                    player.closeInventory();
                }else {
                    openInventory(player,amount,message,taskID);
                }
            }

        });




    }


    //打開臨時背包
    public static void openInventory(Player player,int amount,String message,String taskID){

        if(ActionManager.taskID_Inventory_Map.get(taskID) == null){
            Inventory inventory = Bukkit.createInventory(null, amount , message);
            ActionManager.taskID_Inventory_Map.put(taskID, inventory);
        }
        if(ActionManager.taskID_Inventory_Map.get(taskID) != null){
            Inventory inventory = ActionManager.taskID_Inventory_Map.get(taskID);
            player.openInventory(inventory);
        }

    }
    //打開GUI
    public static void openGui(Player player, LivingEntity target,String guiID){
        String uuidString = player.getUniqueId().toString();
        Inventory inventory = CustomGuiSet.setInventory(player, target, guiID);

        if(inventory != null){
            player.openInventory(inventory);
            EditorGUIManager.custom_Inventory_Boolean_Map.put(uuidString, true);
            EditorGUIManager.custom_Inventory_GuiID_Map.put(uuidString, guiID);
        }

    }


}
