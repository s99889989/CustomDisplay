package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.api.player.config.PlayerChangeClass;
import com.daxton.customdisplay.api.player.config.PlayerRebirth;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomDisplayCommand implements CommandExecutor, TabCompleter {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private ConfigManager configManager = cd.getConfigManager();

    private String[] subCommands = {"help", "reload", "cast", "changeclass", "rebirth"};

    private String[] playerName;

    private String[] classNameList;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player){
            /**玩家用指令**/
            if(commandPlayer(sender, cmd, label, args)){
                return true;
            }
        }

        if(!sender.isOp()){
            sender.sendMessage(configManager.language.getString("Language.Command.isOp"));

        }


        /**不限使用者**/
        if(commandAll(sender, cmd, label, args)){
            return true;
        }

        if(sender instanceof Player){
            /**OP玩家用指令**/
            if(commandOpPlayer(sender, cmd, label, args)){
                return true;
            }

        }

        sender.sendMessage(configManager.language.getString("Language.Command.help.Description"));
        for(String msg : configManager.language.getStringList("Language.Command.help.info")){
            sender.sendMessage(msg);
        }
        return true;
    }

    /**不限使用者**/
    public boolean commandAll(CommandSender sender, Command cmd, String label, String[] args){

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


        return false;
    }

    /**OP玩家用指令**/
    public boolean commandOpPlayer(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        String uuidString = player.getUniqueId().toString();
        if(args.length == 3){
            /**所有線上玩家名單**/
            List<String> onLineplayerNameList = new ArrayList<>();
            /**所有線上玩家**/
            Map<String,Player> playerMap = new HashMap<>();
            Bukkit.getOnlinePlayers().forEach(player2 -> {
                onLineplayerNameList.add(player2.getName());
                playerMap.put(player2.getName(),player2);
            });

            File file = new File(cd.getDataFolder(),"Class/Main");
            /**所有職業名單**/
            List<String> classNameList = new ArrayList<>();
            Arrays.asList(file.list()).forEach(s -> classNameList.add(s.replace(".yml","")));

            /**轉職指令**/
            if(args[0].equalsIgnoreCase("changeclass")) {
                if(onLineplayerNameList.contains(args[1])){
                    if(classNameList.contains(args[2])){
                        new SaveConfig().setConfig(player);
                        new PlayerChangeClass().changeClass(playerMap.get(args[1]),args[2]);
                        return true;
                    }
                }
            }
            /**轉生指令**/
            if(args[0].equalsIgnoreCase("rebirth")) {
                if(onLineplayerNameList.contains(args[1])){
                    if(classNameList.contains(args[2])){
                        new PlayerRebirth().rebirth(playerMap.get(args[1]),args[2]);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**所有玩家用指令**/
    public boolean commandPlayer(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        String uuidString = player.getUniqueId().toString();
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("cast")) {
                if(!(args[1].isEmpty())){
                    if(sender instanceof Player){
                        new PlaceholderManager().getCd_Placeholder_Map().put(uuidString+"<cd_cast_command>",args[1]);

                        new PlayerTrigger2(player).onTwo(player, null, "~oncommand");
                    }
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("bind")) {
                if(!(args[1].isEmpty())){
                    ItemStack item = ((Player) sender).getItemInHand();
                    if (item == null || item.getType() == Material.AIR){
                        sender.sendMessage("You have no items in your hands.");
                        return true;
                    }
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        List<String> commandList = new ArrayList<>();

        /**玩家列表**/
        List<String> playerNameList = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> { playerNameList.add(player.getName()); });
        playerName = playerNameList.toArray(new String[playerNameList.size()]);
        /**職業列表**/
        File file = new File(cd.getDataFolder(),"Class/Main");

        List<String> stringList = new ArrayList<>();
        for(String s : file.list()){
            stringList.add(s.replace(".yml",""));
        }
        classNameList = stringList.toArray(new String[stringList.size()]);

        if (args.length == 1){
            commandList = Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

        }
        if (args.length == 2){
            if(args[0].contains("changeclass") || args[0].contains("rebirth")){
                commandList = Arrays.stream(playerName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
            }
        }

        if (args.length == 3){
            if(args[0].contains("changeclass") || args[0].contains("rebirth")){
                commandList = Arrays.stream(classNameList).filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
            }
        }

        return commandList;
    }




}
