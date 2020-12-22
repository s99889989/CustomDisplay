package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomDisplayCommand implements CommandExecutor, TabCompleter{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private ConfigManager configManager = cd.getConfigManager();

    private String[] subCommands = {"help", "reload", "cast"};

    private String[] subCastCommands = {"action"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //sender.sendMessage(args.length > 0 ? args[0] : "nothing");
        if(!sender.isOp()){
            sender.sendMessage(configManager.language.getString("Language.Command.isOp"));
            return false;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                cd.load();
                sender.sendMessage(configManager.language.getString("Language.Command.reload"));
                return true;
            }
            if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(configManager.language.getString("Language.Command.help.Description"));
                for(String msg : cd.getConfigManager().language.getStringList("Language.Command.help.info")){
                    sender.sendMessage(msg);
                }
                return true;
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("cast")) {
                if(!(args[1].isEmpty())){
                    if(sender instanceof Player){
                        Player player = (Player) sender;
                        String uuidString = player.getUniqueId().toString();
                        new PlaceholderManager().getCd_Placeholder_Map().put(uuidString+"<cd_cast_command>",args[1]);
                        //player.sendMessage("Cast: "+args[1]);
                        new PlayerTrigger(player).onCommand(player);
                    }
                    return true;
                }
            }
        }
        sender.sendMessage(configManager.language.getString("Language.Command.help.Description"));
        for(String msg : configManager.language.getStringList("Language.Command.help.info")){
            sender.sendMessage(msg);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        List<String> commandList = new ArrayList<>();

        if (args.length == 1){
            commandList = Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

        }
//        if (args.length == 2 & args[0].contains("cast")){
//            commandList = Arrays.stream(subCastCommands).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
//        }

        return commandList;
    }



}
