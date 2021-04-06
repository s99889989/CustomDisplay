package com.daxton.customdisplay.nms;

import org.bukkit.Bukkit;

public class NMSVersion {

    public static String getNMSVersion(){
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }
    public static String getMinecraftVersion(){
        String v = Bukkit.getServer().getClass().getPackage().getName();
        String nms = v.substring(v.lastIndexOf('.') + 1);
        switch(nms){
            case "v_1_7_R1":
                return "1.7.2";
            case "v_1_7_R2":
                return "1.7.5";
            case "v_1_7_R3":
                return "1.7.8";
            case "v_1_7_R4":
                return "1.7.10";
            case "v1_8_R1":
                return "1.8.1";
            case "v1_8_R2":
                return "1.8.4";
            case "v_1_8_R3":
                return "1.8.8";
            case "v1_9_R1":
                return "1.9.2";
            case "v1_9_R2":
                return "1.9.4";
            case "v1_10_R1":
                return "1.10.2";
            case "v1_11_R1":
                return "1.11.2/1.11";
            case "v1_13_R1":
                return "1.13";
            case "v1_13_R2":
                return "1.13.2";
            case "v1_14_R1":
                return "1.14.4";
            case "v1_15_R1":
                return "1.15.2";
            case "v1_16_R1":
                return "1.16.5";
            case "v1_16_R2":
                return "1.16.5";
            case "v1_16_R3":
                return "1.16.5";
        }
        throw new IllegalArgumentException(nms + " isn't a know version");
    }

}
