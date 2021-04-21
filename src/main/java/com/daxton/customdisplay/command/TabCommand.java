package com.daxton.customdisplay.command;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class TabCommand implements TabCompleter {

    private final String[] subCommands = {"help", "reload", "cast", "changeclass", "rebirth", "items"};

    public TabCommand(){

    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args){
        List<String> commandList = new ArrayList<>();

        //玩家列表
        String[] playerName = getPlayerNameArray();

        //職業列表
        String[] classNameList = getClassNameArray();


        if (args.length == 1){
            commandList = Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

        }
        if (args.length == 2){
            if(args[0].contains("changeclass") || args[0].contains("rebirth")){
                commandList = Arrays.stream(playerName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
            }
            if(args[0].contains("items")){
                String[] itemsArray = {"edit"};
                commandList = Arrays.stream(itemsArray).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());

            }
        }

        if (args.length == 3){
            if(args[0].contains("changeclass") || args[0].contains("rebirth")){
                commandList = Arrays.stream(classNameList).filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
            }
        }

        return commandList;
    }

    //獲取線上玩家名稱陣列
    public static String[] getPlayerNameArray(){
        String[] playerNameArray;

        List<String> playerNameList = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> { playerNameList.add(player.getName()); });

        playerNameArray = playerNameList.toArray(new String[playerNameList.size()]);

        return playerNameArray;
    }

    //獲取線上玩家名稱List
    public static List<String> getPlayerNameList(){

        List<String> playerNameList = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> { playerNameList.add(player.getName()); });

        return playerNameList;
    }

    //獲取線上玩家名稱Map
    public static Map<String, Player> getPlayerNameMap(){

        Map<String,Player> playerMap = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(player2 -> {
            playerMap.put(player2.getName(),player2);
        });

        return playerMap;
    }

    //獲取職業名稱陣列
    public static String[] getClassNameArray(){
        String[] classNameArray;
        File file = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Class/Main");

        List<String> stringList = new ArrayList<>();
        for(String s : file.list()){
            stringList.add(s.replace(".yml",""));
        }
        classNameArray = stringList.toArray(new String[stringList.size()]);

        return classNameArray;
    }

    //獲取職業名稱List
    public static List<String> getClassNameList(){

        File file = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"Class/Main");

        List<String> stringList = new ArrayList<>();
        for(String s : file.list()){
            stringList.add(s.replace(".yml",""));
        }

        return stringList;
    }

}
