package com.daxton.customdisplay.command;


import com.daxton.customdisplay.api.player.config.PlayerChangeClass;
import com.daxton.customdisplay.api.player.config.PlayerRebirth;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ClassCommand {

    public ClassCommand(){

    }
    public void main(){

    }


    /**OP玩家用指令**/
    public static boolean commandOpPlayer(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        String uuidString = player.getUniqueId().toString();
        if(args.length == 3){
            //所有線上玩家名單
            List<String> onLineplayerNameList = TabCommand.getPlayerNameList();

            //所有線上玩家
            Map<String,Player> playerMap = TabCommand.getPlayerNameMap();

            //所有職業名單
            List<String> classNameList = TabCommand.getClassNameList();

            //轉職指令
            if(args[0].equalsIgnoreCase("changeclass")) {
                if(onLineplayerNameList.contains(args[1])){
                    if(classNameList.contains(args[2])){
                        //new SaveConfig().setConfig(player);
                        PlayerChangeClass.changeClass(playerMap.get(args[1]),args[2]);
                        return true;
                    }
                }
            }
            //轉生指令
            if(args[0].equalsIgnoreCase("rebirth")) {
                if(onLineplayerNameList.contains(args[1])){
                    if(classNameList.contains(args[2])){
                        PlayerRebirth.rebirth(playerMap.get(args[1]),args[2]);
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
