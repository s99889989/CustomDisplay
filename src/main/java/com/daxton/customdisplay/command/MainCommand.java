package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.config.PlayerChangeClass;
import com.daxton.customdisplay.api.player.config.PlayerRebirth;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainCommand implements CommandExecutor {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private ConfigManager configManager = cd.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        String nowLanguage = cd.getConfigManager().config.getString("Language");
        FileConfiguration commandConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+nowLanguage+"_command.yml");
        String isOp = commandConfig.getString("Language.Command.isOp");
        String helpDescription = commandConfig.getString("Language.Command.help.Description");
        List<String> helpInfo = commandConfig.getStringList("Language.Command.help.info");

        if(sender instanceof Player){


            //使用動作 綁定 指令
            if(args[0].equalsIgnoreCase("cast") || args[0].equalsIgnoreCase("bind")) {
                return PlayerCommand.commandPlayer(sender, cmd, label, args);
            }

            if(!sender.isOp()){
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
            sender.sendMessage(helpDescription);
            for(String msg : helpInfo){
                sender.sendMessage(msg);
            }
            return true;

        }else {

            //重新讀取 幫助 指令
            if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("help")){
                return OpCommand.commandAll(sender, cmd, label, args);
            }

            //職業 指令
            if(args[0].equalsIgnoreCase("changeclass") || args[0].equalsIgnoreCase("rebirth")){
                return ClassCommand.commandOpPlayer(sender, cmd, label, args);
            }

            //指令都失敗時
            sender.sendMessage(helpDescription);
            for(String msg : helpInfo){
                sender.sendMessage(msg);
            }
            return true;
        }



    }


}
