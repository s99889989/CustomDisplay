package com.daxton.customdisplay.command;

import com.daxton.customdisplay.gui.item.OpenMenuGUI;
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
                if(args[1].equalsIgnoreCase("edit")){
                    OpenMenuGUI.ItemCategorySelection(player);
                }
            }

        }
        return true;
    }

}
