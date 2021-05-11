package com.daxton.customdisplay.command;

import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerCommand {

    public PlayerCommand(){

    }

    /**所有玩家用指令**/
    public static boolean commandPlayer(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        String uuidString = player.getUniqueId().toString();
        if(args.length == 2){

            if(args[0].equalsIgnoreCase("cast")) {
                if(!(args[1].isEmpty())){
                    if(sender instanceof Player){

                        new PlaceholderManager().getCd_Placeholder_Map().put(uuidString+"<cd_cast_command>",args[1]);

                        PlayerTrigger.onPlayer(player, null, "~oncommand");
                    }
                    return true;
                }
            }

//            if(args[0].equalsIgnoreCase("bind")) {
//                if(!(args[1].isEmpty())){
//                    ItemStack item = ((Player) sender).getItemInHand();
//                    if (item == null || item.getType() == Material.AIR){
//                        sender.sendMessage("You have no items in your hands.");
//                        return true;
//                    }
//                    return true;
//                }
//            }

        }

        return false;
    }

}
