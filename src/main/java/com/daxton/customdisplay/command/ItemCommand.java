package com.daxton.customdisplay.command;

import com.daxton.customdisplay.api.item.gui.ItemMenuMain;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand {

    public ItemCommand(){

    }

    public static boolean main(CommandSender sender, Command cmd, String label, String[] args){

        if(args[0].equalsIgnoreCase("items")) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                //打開物品清單
                if(args[1].contains("menu")){
                    openMenu(player);
                }
            }

        }
        return true;
    }

    public static void openMenu(Player player){
        String uuidString = player.getUniqueId().toString();
        if(PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString) == null){
            PlayerManager.menu_Inventory_ItemMenu_Map.put(uuidString, new ItemMenuMain());
        }
        if(PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString) != null){
            PlayerManager.menu_Inventory_ItemMenu_Map.get(uuidString).openMenu(player);
        }

    }


}
