package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.util.List;


public class MainCommand implements CommandExecutor {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args){

        String nowLanguage = ConfigMapManager.getFileConfigurationMap().get("config.yml").getString("Language");
        FileConfiguration commandConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+nowLanguage+"_command.yml");
        String isOp = commandConfig.getString("Language.Command.isOp");
        String helpDescription = commandConfig.getString("Language.Command.help.Description");
        List<String> helpInfo = commandConfig.getStringList("Language.Command.help.info");

        if(sender instanceof Player){

            if(args.length > 0){

                //使用動作 綁定 指令
                if(args[0].equalsIgnoreCase("cast") || args[0].equalsIgnoreCase("bind")) {
                    return PlayerCommand.commandPlayer(sender, cmd, label, args);
                }

                if(!sender.isOp() && isOp != null){
                    sender.sendMessage(isOp);
                    return true;
                }

                //重新讀取 幫助 指令
                if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("help")){
                    return OpCommand.commandAll(sender, cmd, label, args);
                }

                //物品 指令
                if(args[0].equalsIgnoreCase("items")) {
                    return ItemCommand.main(sender, cmd, label, args);
                }

                //職業 指令
                if(args[0].equalsIgnoreCase("changeclass") || args[0].equalsIgnoreCase("rebirth")){
                    return ClassCommand.commandOpPlayer(sender, cmd, label, args);
                }

                //指令都失敗時
                if(helpDescription != null){
                    sender.sendMessage(helpDescription);
                }
                for(String msg : helpInfo){
                    sender.sendMessage(msg);
                }

            }

            //指令都失敗時
            if(helpDescription != null){
                sender.sendMessage(helpDescription);
            }
            for(String msg : helpInfo){
                sender.sendMessage(msg);
            }

        }else {

            if(args.length > 0){

                //重新讀取 幫助 指令
                if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("help")){
                    return OpCommand.commandAll(sender, cmd, label, args);
                }

                //職業 指令
                if(args[0].equalsIgnoreCase("changeclass") || args[0].equalsIgnoreCase("rebirth")){
                    return ClassCommand.commandOpPlayer(sender, cmd, label, args);
                }

                //指令都失敗時
                if(helpDescription != null){
                    sender.sendMessage(helpDescription);
                }
                for(String msg : helpInfo){
                    sender.sendMessage(msg);
                }

            }

            //指令都失敗時
            if(helpDescription != null){
                sender.sendMessage(helpDescription);
            }
            for(String msg : helpInfo){
                sender.sendMessage(msg);
            }
        }
        return true;


    }


}
