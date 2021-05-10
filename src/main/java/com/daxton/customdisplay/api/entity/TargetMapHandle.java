package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class TargetMapHandle {

    private Map<String, String> action_Map;
    private LivingEntity self;
    private LivingEntity target;

    public TargetMapHandle(){

    }

    public TargetMapHandle(LivingEntity self, LivingEntity target, Map<String, String> action_Map){
        this.action_Map = action_Map;
        this.self = self;
        this.target = target;
    }

    public String getString(String[] key, String def) {
        String output = def;

        for(String ss : key){
            if(this.action_Map.get(ss.toLowerCase()) != null){
                output = this.action_Map.get(ss.toLowerCase());
                if(output.contains("&")){
                    output = ConversionMain.valueOf(this.self,this.target,output);
                }
                return output;
            }
        }

        return output;
    }

    public int getInt(String[] key, int defaultKey){
        int output = defaultKey;

        String inputString = getString(key,null);
        if(inputString != null){
            try {
                output = Integer.parseInt(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }

    public double getDouble(String[] key, double defaultKey){
        double output = defaultKey;
        String inputString = getString(key,null);
        if(inputString != null){
            try {
                output = Double.parseDouble(inputString);
            }catch (NumberFormatException exception){

            }
        }

        return output;
    }

    public boolean getBoolean(String[] key, boolean def){
        boolean output = def;
        String inputString = getString(key,null);
        if(inputString != null){
            output = Boolean.parseBoolean(inputString);
        }
        return output;
    }

    public String[] getStringList(String[] key, String[] def, String split, int amount){
        String[] output = def;
        String inputString = getString(key,null);
        if(inputString != null){
            output = inputString.split(split);
            if(output.length == amount){
                return output;
            }
        }
        return output;
    }

}
