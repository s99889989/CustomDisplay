package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.util.ContentUtil;
import com.daxton.customdisplay.util.NumberUtil;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;

public class Holographic {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    protected static final Random random = new Random();

    private BukkitRunnable bukkitRunnable;

    private Hologram hologram;

    private Player player;

    private LivingEntity target;

    private double damageNumber;

    private Location location;

    private double relativeX;

    private double relativeZ;

    private String firstString;

    private List<String> stringList = new ArrayList<>();
    private String actionString = "";
    private String type = "";
    private String cx = "";
    private String cy = "";
    private String cz = "";
    private String x = "";
    private double xx = 0;
    private String y = "";
    private String z = "";
    private double zz = 0;
    private String message = "";
    private String targetString = "";
    private Double hight = 0.0;
    private int period = 1;

    private String healthConversion;
    private String healthNumber;
    private String health_conversion;


    public Holographic(Player player, LivingEntity target, String firstString, double damageNumber){
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        this.firstString = firstString;
        /**抓取資料**/
        StringTokenizer stringTokenizer = new StringTokenizer(firstString, "[,] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string : stringList){
            if(string.toLowerCase().contains("createhd")){
                actionString = string;
            }
            if(string.toLowerCase().contains("m=")){
                String[] strings1 = string.split("=");
                message = strings1[1];
            }
            if(string.toLowerCase().contains("x=")){
                String[] strings1 = string.split("=");
                cx = strings1[1];
            }
            if(string.toLowerCase().contains("y=")){
                String[] strings1 = string.split("=");
                cy = strings1[1];
            }
            if(string.toLowerCase().contains("z=")){
                String[] strings1 = string.split("=");
                cz = strings1[1];
            }
            if(string.toLowerCase().contains("period=")){
                String[] strings1 = string.split("=");
                period = Integer.valueOf(strings1[1]);
            }
            if(string.toLowerCase().contains("healthconver=")){
                String[] strings1 = string.split("=");
                health_conversion = strings1[1];
            }
            if(string.toLowerCase().contains("@=")){
                String[] strings1 = string.split("=");
                targetString = strings1[1];
            }
        }
        if(targetString.toLowerCase().contains("self") || targetString.toLowerCase().contains("selflocation")){
            hight = player.getHeight();
        }
        if(targetString.toLowerCase().contains("target") || targetString.toLowerCase().contains("targetlocation")){
            hight = target.getHeight();
        }



    }
    /**創造HD**/
    public void createHD(){
        String attackNumber = damageNumber();
        healthConversion = targetHealth();
        healthNumber = targetHealthNumber();
        message = new ContentUtil(message, player, "Character").getOutputString();
        message = message.replace("{cd_damage}", attackNumber).replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber);

        if(targetString.toLowerCase().contains("targetlocation")){
            location = target.getLocation().add(Double.valueOf(cx),Double.valueOf(cy),Double.valueOf(cz));
        }
        if(targetString.toLowerCase().contains("selflocation")){
            location = player.getLocation().add(Double.valueOf(cx),Double.valueOf(cy),Double.valueOf(cz));
        }
        if(targetString.toLowerCase().contains("target")){
            location = target.getLocation().add(Double.valueOf(cx),Double.valueOf(cy)+hight,Double.valueOf(cz));
        }
        if(targetString.toLowerCase().contains("self")){
            location = player.getLocation().add(Double.valueOf(cx),Double.valueOf(cy)+hight,Double.valueOf(cz));
        }
        if(targetString == null){
            location = new Location(player.getWorld(), Double.valueOf(cx),Double.valueOf(cy)+hight,Double.valueOf(cz));
        }
        hologram = HologramsAPI.createHologram(cd, location);

        hologram.appendTextLine(message);
        //hologram.appendTextLine("");


    }

    public void bukkitRun(){
        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {

                hologram.removeLine(0);

                /**返回傷害**/
                healthConversion = targetHealth();
                healthNumber = targetHealthNumber();
                message = new ContentUtil(message, player, "Character").getOutputString();
                message = message.replace("{cd_health_conversion}", healthConversion).replace("{cd_health_number}", healthNumber);

                hologram.appendTextLine(message);

                if(targetString.toLowerCase().contains("targetlocation") && type.toLowerCase().contains("loc")){
                    hologram.teleport(location.add(Double.valueOf(x),Double.valueOf(y),Double.valueOf(z)));
                }else if(targetString.toLowerCase().contains("selflocation") && type.toLowerCase().contains("loc")){
                    hologram.teleport(location.add(Double.valueOf(x),Double.valueOf(y),Double.valueOf(z)));
                }else if(targetString.toLowerCase().contains("target") && type.toLowerCase().contains("loc")){
                    hologram.teleport(target.getLocation().add(Double.valueOf(x),Double.valueOf(y)+hight,Double.valueOf(z)));
                }else if(targetString.toLowerCase().contains("self") && type.toLowerCase().contains("loc")){
                    hologram.teleport(player.getLocation().add(Double.valueOf(x),Double.valueOf(y)+hight,Double.valueOf(z)));
                }

                if(targetString.toLowerCase().contains("targetlocation") && type.toLowerCase().contains("vec")){
                    hologram.teleport(location.add(xx,Double.valueOf(y),zz));
                }else if(targetString.toLowerCase().contains("selflocation") && type.toLowerCase().contains("vec")){
                    hologram.teleport(location.add(xx,Double.valueOf(y),zz));
                } else if(targetString.toLowerCase().contains("target") && type.toLowerCase().contains("vec")){
                    hologram.teleport(target.getLocation().add(xx,Double.valueOf(y)+hight,zz));
                } else if(targetString.toLowerCase().contains("self") && type.toLowerCase().contains("vec")){
                    hologram.teleport(player.getLocation().add(xx,Double.valueOf(y)+hight,zz));
                }


            }
        };
        bukkitRunnable.runTaskTimer(cd , 1,period);
    }

    /**設定傷害**/
    public String damageNumber(){
        String snumber = new NumberUtil(damageNumber, ConfigMapManager.getFileConfigurationMap().get("Character_CustomDisplay.yml").getString("player-damage.decimal")).getDecimalString();
        snumber = new NumberUtil(snumber, ConfigMapManager.getFileConfigurationMap().get("Character_CustomDisplay.yml").getStringList("player-damage.conversion")).getNineString();
        return snumber;
    }

    /**設定怪物血量顯示**/
    public String targetHealth(){
        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowhealth = target.getHealth();
        int counthealth = (int) nowhealth*10/(int) maxhealth;
        String mhealth = new NumberUtil(counthealth, ConfigMapManager.getFileConfigurationMap().get("Character_CustomDisplay.yml").getStringList(health_conversion+".conversion")).getTenString();
        return mhealth;
    }
    /**設定怪物血量顯示數字**/
    public String targetHealthNumber(){
        double maxhealth = target.getAttribute(GENERIC_MAX_HEALTH).getValue();
        double nowhealth = target.getHealth();
        String mhealthNumber = nowhealth +"/"+maxhealth;
        return mhealthNumber;
    }

    /**移動HD**/
    public void teleportHD(String firstString){

        /**抓取資料**/
        this.firstString = firstString;
        StringTokenizer stringTokenizer = new StringTokenizer(firstString, "[,] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string : stringList){
            if( string.toLowerCase().contains("teleporthd") || string.toLowerCase().contains("teleporthdthrow")){
                actionString = string;
            }
            if(string.toLowerCase().contains("m=")){
                String[] strings1 = string.split("=");
                message = strings1[1];
            }
            if(string.toLowerCase().contains("type=")){
                String[] strings1 = string.split("=");
                type = strings1[1];
            }
            if(string.toLowerCase().contains("x=")){
                String[] strings1 = string.split("=");
                x = strings1[1];
            }
            if(string.toLowerCase().contains("y=")){
                String[] strings1 = string.split("=");
                y = strings1[1];
            }
            if(string.toLowerCase().contains("z=")){
                String[] strings1 = string.split("=");
                z = strings1[1];
            }
            if(string.toLowerCase().contains("period=")){
                String[] strings1 = string.split("=");
                period = Integer.valueOf(strings1[1]);
            }
            if(string.toLowerCase().contains("healthConver=")){
                String[] strings1 = string.split("=");
                health_conversion = strings1[1];
            }
            if(string.toLowerCase().contains("@=")){
                String[] strings1 = string.split("=");
                targetString = strings1[1];
            }
        }
        if(targetString.toLowerCase().contains("self")){
            hight = player.getHeight();
        }
        if(targetString.toLowerCase().contains("target")){
            hight = target.getHeight();
        }

        /**判斷如何移動HD**/
        if(targetString.toLowerCase().contains("target") && type.toLowerCase().contains("vec")){
                xx = vectorX(target)*Double.valueOf(x);
                zz = vectorZ(target)*Double.valueOf(z);

        }
        if(targetString.toLowerCase().contains("self") && type.toLowerCase().contains("vec")){
                xx = vectorX(player)*Double.valueOf(x);
                zz = vectorZ(player)*Double.valueOf(z);
        }
    }

    private double vectorX(LivingEntity livingEntity){
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

    private Vector randomVector(LivingEntity player) {
        double a = Math.toRadians((double)(player.getEyeLocation().getYaw() + 90.0F));
        a += (double)(random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 2.0D + 1.0D) * 3.141592653589793D / 6.0D;
        return (new Vector(Math.cos(a), 0.8D, Math.sin(a))).normalize().multiply(0.4D);
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public Hologram getHologram() {
        return hologram;
    }
}
