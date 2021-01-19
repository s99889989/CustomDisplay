package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.math.BigInteger;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.bukkit.Color.*;
import static org.bukkit.Particle.REDSTONE;

public class SendParticles {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private String function = "";
    private Particle putParticle;
    private Particle inParticle;
    private Particle.DustOptions color = new Particle.DustOptions(fromRGB(0xFF0000), 1);
    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    private String aims = "";
    private double extra = 0;
    private int count = 10;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;


    public SendParticles(){



    }

    public void setParticles(LivingEntity self,LivingEntity target, String firstString, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        stringSetting(firstString);


    }

    public void stringSetting(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String allString : stringList) {
            if (allString.toLowerCase().contains("function=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    function = strings[1];

                }
            }
            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
            if (allString.toLowerCase().contains("particle=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        putParticle = Enum.valueOf(Particle.class,strings[1].toUpperCase());
                    }catch (Exception exception){

                    }

                }
            }
            if (allString.toLowerCase().contains("color=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {


                    try{
                        BigInteger bigint = new BigInteger(strings[1], 16);
                        int numb = bigint.intValue();
                        color = new Particle.DustOptions(fromRGB(numb), 1);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("color錯誤"+strings[1]);
                    }
                }
            }
            if (allString.toLowerCase().contains("extra=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        extra = Double.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("extra錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("count=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        count = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("count錯誤"+strings[1]);
                    }

                }
            }

        }

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if (allString.toLowerCase().contains("x=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        x = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("x錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("y=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        y = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("y錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("z=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        z = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("z錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("xoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        xOffset = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("xOffset錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("yoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        yOffset = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("yOffset錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("zoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        zOffset = Double.valueOf(new ConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("zOffset錯誤");
                    }

                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            if(putParticle != null){
                String particle = putParticle.toString().toLowerCase();
                PlaceholderManager.getParticles_function().put(target.getUniqueId().toString()+"particle",particle);
            }
            PlaceholderManager.getParticles_function().put(target.getUniqueId().toString()+"function",function);
            location = target.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("self")){
            if(putParticle != null){
                String particle = putParticle.toString().toLowerCase();
                PlaceholderManager.getParticles_function().put(self.getUniqueId().toString()+"particle",particle);
            }
            PlaceholderManager.getParticles_function().put(self.getUniqueId().toString()+"function",function);
            location = self.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        if(function.toLowerCase().contains("remove")){

        }else if(function.toLowerCase().contains("add")){
            sendParticle();
        }else {
            sendParticle();
        }

    }
    public void sendParticle(){
        try{
            if(putParticle == REDSTONE){
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,color);
            }else {
                target.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra);
            }
        }catch (Exception e){
            //cd.getLogger().info(e.toString());
        }


    }

}
