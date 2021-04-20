package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class OpCommand {

    public OpCommand(){

    }

    /**不限使用者**/
    public static boolean commandAll(CommandSender sender, Command cmd, String label, String[] args){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ConfigManager configManager = cd.getConfigManager();

        String nowLanguage = cd.getConfigManager().config.getString("Language");
        FileConfiguration commandConfig = ConfigMapManager.getFileConfigurationMap().get("Language_"+nowLanguage+"_command.yml");
        String reload = commandConfig.getString("Language.Command.reload");
        String helpDescription = commandConfig.getString("Language.Command.help.Description");
        List<String> helpInfo = commandConfig.getStringList("Language.Command.help.info");

        if(args.length == 1){

            if(args[0].equalsIgnoreCase("reload")){

                cd.load();
                sender.sendMessage(reload);
                return true;
            }
            if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(helpDescription);
                for(String msg : helpInfo){
                    sender.sendMessage(msg);
                }
                return true;
            }
        }


        return false;
    }

}
