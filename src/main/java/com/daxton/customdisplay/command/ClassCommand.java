package com.daxton.customdisplay.command;


import com.daxton.customdisplay.CustomDisplay;
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

    /**OP玩家用指令**/
    public static boolean commandOpPlayer(CommandSender sender, Command cmd, String label, String[] args){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
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
                        PlayerChangeClass.changeClass(playerMap.get(args[1]),args[2]);
                        cd.getLogger().info(args[1]+" change class to "+args[2]);
                        return true;
                    }
                }
            }
            //轉生指令
            if(args[0].equalsIgnoreCase("rebirth")) {
                if(onLineplayerNameList.contains(args[1])){
                    if(classNameList.contains(args[2])){
                        PlayerRebirth.rebirth(playerMap.get(args[1]),args[2]);
                        cd.getLogger().info(args[1]+" rebirth to "+args[2]);
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
