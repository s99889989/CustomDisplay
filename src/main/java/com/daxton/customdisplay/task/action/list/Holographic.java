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

    private String taskID = "";
    private Player player = null;
    private LivingEntity target = null;
    private double damageNumber = 0;

    public Hologram hologram;

    Location createLocation;

    private String function = "";
    private String message = "";
    private String locationType = "wod";
    private String aims = "";
    private double x = 0;
    private double y = 0;
    private double z = 0;


    public Holographic(){

    }

    public void setHD(Player player, LivingEntity target, String firstString, double damageNumber,String taskID) {

        this.taskID = taskID;
        this.player = player;
        if (target != null) {
            this.target = target;
        }
        if (damageNumber > 0.01) {
            this.damageNumber = damageNumber;
        }
        setOther(firstString);

    }

    public void setOther(String firstString){
        aims = "";
        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("message=") || string.toLowerCase().contains("m=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    message = new StringConversion("Character",strings[1],player,target).getResultString();
                }
            }
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }

            if(string.toLowerCase().contains("locationtype=") || string.toLowerCase().contains("lt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    locationType = strings[1];
                }
            }

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            createLocation = target.getLocation();
        }else if(aims.toLowerCase().contains("self")){
            createLocation = player.getLocation();
        }else {
            createLocation = new Location(player.getWorld(),0,0,0);
        }

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("x=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        x = Double.valueOf(new StringConversion("Character",strings[1],player,target).getResultString());
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("x不是數字");
                    }
                }
            }

            if(string.toLowerCase().contains("y=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        y = Double.valueOf(new StringConversion("Character",strings[1],player,target).getResultString());
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("y不是數字");
                    }
                }
            }

            if(string.toLowerCase().contains("z=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try {
                        z = Double.valueOf(new StringConversion("Character",strings[1],player,target).getResultString());
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("z不是數字");
                    }
                }
            }
        }



//        cd.getLogger().info("訊息:"+message);
//        cd.getLogger().info("功能:"+function);
//        cd.getLogger().info("位置類型:"+locationType);
//        cd.getLogger().info("目標:"+aims);
//        cd.getLogger().info("x:"+x);
//        cd.getLogger().info("y:"+y);
//        cd.getLogger().info("z:"+z);

        if(function.toLowerCase().contains("create") && hologram == null){
            createHD();
        }
        if(function.toLowerCase().contains("addtextline") && hologram != null){
            addLineHD();
        }
        if(function.toLowerCase().contains("removetextline") && hologram != null){
            removeLineHD();
        }
        if(function.toLowerCase().contains("teleport") && hologram != null){
            teleportHD();
        }
        if(function.toLowerCase().contains("delete") && hologram != null){
            deleteHD();
        }

    }


    public void createHD(){



        String attackNumber = damageNumberAction();

        hologram = HologramsAPI.createHologram(cd, createLocation);
        hologram.appendTextLine(message.replace("{cd_damage}", attackNumber));



    }

    public void addLineHD(){

//        if(target.getType().toString().toLowerCase().equals("player")){
//            Player targetPlayer = (Player) target;
//            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),targetPlayer,target).getResultString());
//        }else {
//            hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),player,target).getResultString());
//        }

    }

    public void removeLineHD(){

        //hologram.removeLine(Integer.valueOf(actionMap.get("m")));
    }

    public void teleportHD(){

//        /**座標**/
//        if(actionMap.get("hdtype").contains("loc")){
//            if(actionMap.get("@").contains("targetlocation")){
//                createLocation = createLocation.add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("selflocation")){
//                createLocation = createLocation.add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("target")){
//                if(target.getHealth() < 1){
//                    deleteHD();
//                    return;
//                }
//                createLocation = target.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("self")){
//                createLocation = player.getLocation().add(Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),Double.valueOf(actionMap.get("z")));
//            }
//        }
//
//        /**向量**/
//        if(actionMap.get("hdtype").contains("vec")){
//            if(actionMap.get("@").contains("targetlocation")){
//                createLocation = createLocation.add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(target)*Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("selflocation")){
//                createLocation = createLocation.add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y")),vectorZ(player)*Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("target")){
//                if(target.getHealth() < 1){
//                    deleteHD();
//                    return;
//                }
//                createLocation = target.getLocation().add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),vectorZ(target)*Double.valueOf(actionMap.get("z")));
//            }else if(actionMap.get("@").contains("self")){
//                createLocation = player.getLocation().add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),vectorZ(player)*Double.valueOf(actionMap.get("z")));
//            }
//        }
//
//
//        hologram.teleport(createLocation);

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
