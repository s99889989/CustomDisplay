package com.daxton.customdisplay.task.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.JudgmentAction;
import com.daxton.customdisplay.api.action.list.*;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.util.ContentUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OnAttack {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID;

    private Player player;

    private UUID playerUUID;

    private LivingEntity target;

    private UUID targetUUID;

    private String firstString;

    private double damageNumber;

    /**動作列表**/
    private List<String> actionList = new ArrayList<>();

    private BukkitRunnable bukkitRunnable;

    private Map<Player, Holographic> holographicMap = new HashMap<>();

    private String entityType;

    public OnAttack(Player player, LivingEntity target, String firstString, double damageNumber){
        this.player = player;
        this.target = target;
        this.playerUUID = player.getUniqueId();
        this.targetUUID = target.getUniqueId();
        this.damageNumber = damageNumber;
        this.firstString = firstString;
        taskID = String.valueOf((int)(Math.random()*100000));
        actionList = new ConfigFind().getActionList(firstString);

//        setEntityType();
//        player.sendMessage("玩家"+target.getType().toString());
//        if(!(targetType())){
//            return;
//        }
        startAction();
    }

    public void startAction(){
        int delay = 0;
        next:
        for(String string : actionList){
            /**判斷條件**/
//            if (string.toLowerCase().contains("condition")) {
//                if(condition(string)){
//                    break next;
//                }
//            }
            if (string.toLowerCase().contains("delay ")) {
                String[] slt = string.split(" ");
                if (slt.length == 2) {
                    delay = delay + Integer.valueOf(slt[1]);
                }
            }
            bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    new JudgmentAction().execute(player,target,string,damageNumber,taskID);
                }
            };
            bukkitRunnable.runTaskLater(cd, delay);
        }
    }

    public void task(String string){

//        if (string.toLowerCase().contains("sendtitle")) {
//            new SendTitle(player, string).sendTitle();
//            bukkitRunnable.cancel();
//        }
//        if (string.toLowerCase().contains("sound")) {
//            new Sound(player, string).playSound();
//            bukkitRunnable.cancel();
//        }
//
//        if (string.toLowerCase().contains("sendactionbar")) {
//            new SendActionBar(player, string);
//            bukkitRunnable.cancel();
//        }
//
//        /**目標唯一HD**/
//        if (string.toLowerCase().contains("createhd") && string.toLowerCase().contains("mark=target")) {
//            if(TriggerManager.getHolographicMap().get(targetUUID) == null){
//                TriggerManager.getHolographicMap().put(targetUUID,new Holographic(player,target, string, damageNumber));
//                TriggerManager.getHolographicMap().get(targetUUID).createHD();
//                TriggerManager.getHolographicMap().get(targetUUID).bukkitRun();
//            }
//            bukkitRunnable.cancel();
//        }
//        if (string.toLowerCase().contains("teleporthd") && string.toLowerCase().contains("mark=target")) {
//            if(TriggerManager.getHolographicMap().get(targetUUID) != null){
//                TriggerManager.getHolographicMap().get(targetUUID).teleportHD(string);
//            }
//            bukkitRunnable.cancel();
//        }
//        if (string.toLowerCase().contains("deletehd") && string.toLowerCase().contains("mark=target")) {
//            if(TriggerManager.getHolographicMap().get(targetUUID) != null ){
//                TriggerManager.getHolographicMap().get(targetUUID).getHologram().delete();
//                TriggerManager.getHolographicMap().get(targetUUID).getBukkitRunnable().cancel();
//                TriggerManager.getHolographicMap().remove(targetUUID);
//            }
//            bukkitRunnable.cancel();
//        }
//
//        /**玩家唯一HD**/
//
//        /**HD**/
//        if (!(string.toLowerCase().contains("mark=target")) && !(string.toLowerCase().contains("mark=self")) && string.toLowerCase().contains("createhd")) {
//            if(holographicMap.get(player) == null){
//                holographicMap.put(player,new Holographic(player,target, string, damageNumber));
//                holographicMap.get(player).createHD();
//                holographicMap.get(player).bukkitRun();
//            }
//            bukkitRunnable.cancel();
//        }
//        if (!(string.toLowerCase().contains("mark=target")) && !(string.toLowerCase().contains("mark=self")) && string.toLowerCase().contains("teleporthd")) {
//            if(holographicMap.get(player) != null){
//                holographicMap.get(player).teleportHD(string);
//            }
//            bukkitRunnable.cancel();
//        }
//        if (!(string.toLowerCase().contains("mark=target")) && !(string.toLowerCase().contains("mark=self")) && string.toLowerCase().contains("deletehd")) {
//            if(holographicMap.get(player) != null){
//                holographicMap.get(player).getHologram().delete();
//                holographicMap.get(player).getBukkitRunnable().cancel();
//                holographicMap.remove(player);
//            }
//            bukkitRunnable.cancel();
//        }


    }

    public boolean targetType(){

        boolean b = false;
        List<String> stringList = ConfigMapManager.getFileConfigurationMap().get("Character_EntityConfig.yml").getStringList(entityType.toLowerCase()+".entityType");
        for(String s : stringList){
            if(s.toLowerCase().contains(target.getType().toString().toLowerCase())){
                b = true;
            }
        }
        return b;
    }

    /**條件判斷**/
    public boolean condition(String string){
        boolean b = false;
        String string1 = string.replace("Condition[","").replace("condition[","").replace("]","");
        if(string1.contains("<")){

            String[] strings1 = string1.split("<");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) < Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains(">")){
            String[] strings1 = string1.split(">");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) > Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains("=")){
            String[] strings1 = string1.split("=");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]).equals(Double.valueOf(strings1[1])))){
                b = true;
            }
        }

        return b;
    }

    /**設定實體類型**/
    public void setEntityType(){
        List<String> stringList = new ArrayList<>();
        for(String string : actionList){
            if(string.toLowerCase().contains("entitytype=") && string.toLowerCase().contains("createhd")){
                StringTokenizer stringTokenizer = new StringTokenizer(string,"[,] ");
                while (stringTokenizer.hasMoreElements()){
                    stringList.add(stringTokenizer.nextToken());
                }
            }
        }

        for(String string1 : stringList){
            if(string1.toLowerCase().contains("entitytype=")){
                String[] strings = string1.split("=");
                entityType = strings[1];
            }
        }

    }

}
