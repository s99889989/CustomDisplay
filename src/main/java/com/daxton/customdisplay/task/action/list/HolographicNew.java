package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.util.NumberUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class HolographicNew {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private double damageNumber;

    private Map<String,String> actionMap = new HashMap<>();

    public Hologram hologram;

    Location createLocation;

    private String health_conversion;

    private String taskID;

    public HolographicNew(){

    }

    public void setHD(Player player, LivingEntity target, String firstString, double damageNumber,String taskID){
        this.taskID = taskID;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("createhd") || string1.toLowerCase().contains("addlinehd") || string1.toLowerCase().contains("removelinehd") || string1.toLowerCase().contains("teleporthd") || string1.toLowerCase().contains("deletehd")){
                actionMap.put("actionname",string1.toLowerCase());
            }

            if(string1.toLowerCase().contains("m=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1]);
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
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
            }

            if(string1.toLowerCase().contains("healthconver=")){
                String[] strings1 = string1.split("=");
                health_conversion = strings1[1];
            }

            if(string1.toLowerCase().contains("hdtype=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1].toLowerCase());
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
        String attackNumber = damageNumber();
        String healthConversion = targetHealth();
        String healthNumber = targetHealthNumber();

        hologram = HologramsAPI.createHologram(cd, createLocation);
        //hologram.appendTextLine(new StringConversion("Character",actionMap.get("m"),player).getFinalString().replace("{cd_damage}", attackNumber));
        if(target.getType().toString().toLowerCase().equals("player")){
            Player targetPlayer = (Player) target;
            hologram.appendTextLine(new StringConversion().getString("Character",actionMap.get("m"),targetPlayer).replace("{cd_damage}", attackNumber).replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber));
        }else {
            hologram.appendTextLine(new StringConversion().getString("Character",actionMap.get("m"),player).replace("{cd_damage}", attackNumber).replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber));
        }


    }

    public void addLineHD(){
        String healthConversion = targetHealth();
        String healthNumber = targetHealthNumber();
        if(target.getType().toString().toLowerCase().equals("player")){
            Player targetPlayer = (Player) target;
            hologram.appendTextLine(new StringConversion().getString("Character",actionMap.get("m"),targetPlayer).replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber));
        }else {
            hologram.appendTextLine(new StringConversion().getString("Character",actionMap.get("m"),player).replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber));
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
                createLocation = target.getLocation().add(vectorX(target)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+target.getHeight(),vectorZ(target)*Double.valueOf(actionMap.get("z")));
            }else if(actionMap.get("@").contains("self")){
                createLocation = player.getLocation().add(vectorX(player)*Double.valueOf(actionMap.get("x")),Double.valueOf(actionMap.get("y"))+player.getHeight(),vectorZ(player)*Double.valueOf(actionMap.get("z")));
            }
        }


        hologram.teleport(createLocation);

    }

    public void deleteHD(){
        if(TriggerManager.getAttackListenerTaskMap().get(taskID) != null){
            TriggerManager.getAttackListenerTaskMap().remove(taskID);
        }
        if(TriggerManager.getOnAttackTaskMap().get(taskID) != null){
            TriggerManager.getOnAttackTaskMap().remove(taskID);
        }
        if(TriggerManager.getJudgmentActionTaskMap().get(taskID) != null){
            TriggerManager.getJudgmentActionTaskMap().remove(taskID);
        }
        if(TriggerManager.getHolographicTaskMap().get(taskID) != null){
            TriggerManager.getHolographicTaskMap().remove(taskID);
        }
        if(TriggerManager.getLoopTaskMap().get(taskID) != null){
            TriggerManager.getLoopTaskMap().remove(taskID);
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
    public String damageNumber(){
        String snumber = new NumberUtil(damageNumber, ConfigMapManager.getFileConfigurationMap().get("Character_System_AttackNumber.yml").getString("player-damage.decimal")).getDecimalString();
        snumber = new NumberUtil(snumber, ConfigMapManager.getFileConfigurationMap().get("Character_System_AttackNumber.yml").getStringList("player-damage.conversion")).getNineString();
        return snumber;
    }

    /**設定怪物血量顯示**/
    public String targetHealth(){
        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowhealth = target.getHealth();
        int counthealth = (int) nowhealth*10/(int) maxhealth;
        String mhealth = new NumberUtil(counthealth, ConfigMapManager.getFileConfigurationMap().get("Character_System_Health.yml").getStringList(health_conversion+".conversion")).getTenString();
        return mhealth;
    }
    /**設定怪物血量顯示數字**/
    public String targetHealthNumber(){
        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        String nowhealth = new NumberUtil(target.getHealth(),"0.#").getDecimalString();
        String mhealthNumber = nowhealth +"/"+maxhealth;
        return mhealthNumber;
    }

}
