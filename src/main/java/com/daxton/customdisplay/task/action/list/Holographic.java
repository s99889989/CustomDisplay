package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Holographic {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private double damageNumber;

    private Map<String,String> actionMap = new HashMap<>();

    public Hologram hologram;

    Location createLocation;

    private String taskID;

    private String test = "";

    public Holographic(){

    }

    public void setHD(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){

        this.test = firstString;
        this.taskID = taskID;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;

        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("createhd") || string1.toLowerCase().contains("addlinehd") || string1.toLowerCase().contains("removelinehd") || string1.toLowerCase().contains("teleporthd") || string1.toLowerCase().contains("deletehd")){
                actionMap.put("actionname",string1.toLowerCase());
            }

            if(string1.toLowerCase().contains("x=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }

            if(string1.toLowerCase().contains("y=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }

            if(string1.toLowerCase().contains("z=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }

            if(string1.toLowerCase().contains("@=")){
                string1 = string1.replace(" ","");
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }


            if(string1.toLowerCase().contains("hdtype=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }

        }

        List<String> stringList2 = new StringFind().getStringMessageList(firstString);
        for(String string2 : stringList2){
            if(string2.toLowerCase().contains("message=") || string2.toLowerCase().contains("m=")){
                String[] strings2 = string2.split("=");
                if(strings2.length == 2){
                    actionMap.put(strings2[0].toLowerCase(),strings2[1]);
                }
            }
        }

        if(actionMap.get("actionname").contains("createhd") && hologram == null){
            createHD();
        }
        if(actionMap.get("actionname").contains("addlinehd") && hologram != null){
            addLineHD();
        }
        if(actionMap.get("actionname").contains("removelinehd") && hologram != null){
            removeLineHD();
        }
        if(actionMap.get("actionname").contains("teleporthd") && hologram != null){
            teleportHD();
        }
        if(actionMap.get("actionname").contains("deletehd") && hologram != null){
            deleteHD();
        }

    }

    public void createHD(){

        createLocation = new Location(player.getWorld(),Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
        /**座標**/
        if(actionMap.get("hdtype").contains("loc")){
            if(actionMap.get("@").contains("targetlocation")){
                createLocation = target.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("selflocation")){
                createLocation = player.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("target")){
                createLocation = target.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("self")){
                createLocation = player.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),Double.valueOf(actionMap.get("z")));
            }
        }

        /**向量**/
        if(actionMap.get("hdtype").contains("vec")){
            if(actionMap.get("@").contains("targetlocation")){
                createLocation = target.getLocation().add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(target)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("selflocation")){
                createLocation = player.getLocation().add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(player)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("target")){
                createLocation = target.getLocation().add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),vectorZ(target)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("self")){
                createLocation = player.getLocation().add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),vectorZ(player)*Double.valueOf(actionMap.get("z")));
            }
        }

        String attackNumber = damageNumberAction();


        hologram = HologramsAPI.createHologram(cd, createLocation);
        if(target.getType().toString().toLowerCase().equals("player")){
            Player targetPlayer = (Player) target;
            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),targetPlayer,target).getResultString().replace("{cd_damage}", attackNumber));
        }else {
            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),player,target).getResultString().replace("{cd_damage}", attackNumber));
        }


    }

    public void addLineHD(){

        if(target.getType().toString().toLowerCase().equals("player")){
            Player targetPlayer = (Player) target;
            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),targetPlayer,target).getResultString());
        }else {
            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),player,target).getResultString());
        }

    }

    public void removeLineHD(){

        hologram.removeLine(Integer.valueOf(actionMap.get("m")));
    }

    public void teleportHD(){


        /**座標**/
        if(actionMap.get("hdtype").contains("loc")){
            if(actionMap.get("@").contains("targetlocation")){
                createLocation = createLocation.add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("selflocation")){
                createLocation = createLocation.add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("target")){
                if(target.getHealth() < 1){
                    deleteHD();
                    return;
                }
                createLocation = target.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("self")){
                createLocation = player.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),Double.valueOf(actionMap.get("z")));
            }
        }

        /**向量**/
        if(actionMap.get("hdtype").contains("vec")){
            if(actionMap.get("@").contains("targetlocation")){
                createLocation = createLocation.add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(target)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("selflocation")){
                createLocation = createLocation.add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(player)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("target")){
                if(target.getHealth() < 1){
                    deleteHD();
                    return;
                }
                createLocation = target.getLocation().add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),vectorZ(target)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("self")){
                createLocation = player.getLocation().add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),vectorZ(player)*Double.valueOf(actionMap.get("z")));
            }
        }


        hologram.teleport(createLocation);

    }

    public void deleteHD(){
        if(ActionManager.getAction_Judgment_Map().get(taskID) != null){
            ActionManager.getAction_Judgment_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_Loop_Map().get(taskID) != null){
            ActionManager.getJudgment_Loop_Map().remove(taskID);
        }
        if(ActionManager.getJudgment_Holographic_Map().get(taskID) != null){
            ActionManager.getJudgment_Holographic_Map().remove(taskID);
        }
        if(ActionManager.getLoop_Judgment_Map().get(taskID) != null){
            ActionManager.getLoop_Judgment_Map().remove(taskID);
        }
        hologram.delete();
    }

    public double vectorX(LivingEntity livingEntity){
        double xVector = livingEntity.getLocation().getDirection().getX();
        double rxVector = 0;
        if(xVector > 0){
            rxVector = 1;
        }else{
            rxVector = -1;
        }
        return rxVector;
    }
    public double vectorZ(LivingEntity livingEntity){
        double zVector = livingEntity.getLocation().getDirection().getZ();
        double rzVector = 0;
        if(zVector > 0){
            rzVector = 1;
        }else{
            rzVector = -1;
        }
        return rzVector;
    }

    /**設定傷害**/
    public String damageNumberAction(){
        FileConfiguration config = ConfigMapManager.getFileConfigurationMap().get("Character_System_AttackNumber.yml");
        List<String> headList = new ArrayList<>();
        List<String> doubleList = new ArrayList<>();
        List<String> unitsList = new ArrayList<>();
        if(taskID.toLowerCase().contains("~oncrit")){
            headList = config.getStringList("player-damage-crit.head_conversion");
            doubleList = config.getStringList("player-damage-crit.double_conversion");
            unitsList = config.getStringList("player-damage-crit.units_conversion");
        }else {
            headList = config.getStringList("player-damage.head_conversion");
            doubleList = config.getStringList("player-damage.double_conversion");
            unitsList = config.getStringList("player-damage.units_conversion");
        }
        String snumber = new NumberUtil(damageNumber, config.getString("player-damage.decimal")).getDecimalString();
        String lastSnumber = new NumberUtil(snumber, headList,doubleList,unitsList).getNineThreeString();
        return lastSnumber;
    }


    private Vector randomVector(LivingEntity player) {
        double a = Math.toRadians((double)(player.getEyeLocation().getYaw() + 90.0F));
        a += (double)(random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 2.0D + 1.0D) * 3.141592653589793D / 6.0D;
        return (new Vector(Math.cos(a), 0.8D, Math.sin(a))).normalize().multiply(0.4D);
    }

}
