package com.daxton.customdisplay.nms;

import org.bukkit.Bukkit;

public class NMSVersion {
    //v1_17_R1
    public static String getNMSVersion(){
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }
    //目前版本大於判定版本
    public static boolean upNMSVersion(String version){
        return getMinecraftVersionSize(getMinecraftVersion()) >= getMinecraftVersionSize(version);
    }

    public static int getMinecraftVersionSize(String nms){
        switch(nms){
            case "1.7.2":
                return 1;
            case "1.7.5":
                return 2;
            case "1.7.8":
                return 3;
            case "1.7.10":
                return 4;
            case "1.8.1":
                return 5;
            case "1.8.4":
                return 6;
            case "1.8.8":
                return 7;
            case "1.9.2":
                return 8;
            case "1.9.4":
                return 9;
            case "1.10.2":
                return 10;
            case "1.11.2":
                return 11;
            case "1.12.2":
                return 12;
            case "1.13":
                return 13;
            case "1.13.2":
                return 14;
            case "1.14.4":
                return 15;
            case "1.15.2":
                return 16;
            case "1.16.1":
                return 17;
            case "1.16.3":
                return 18;
            case "1.16.5":
                return 19;
            case "1.17":
                return 20;
        }
        throw new IllegalArgumentException(nms + " isn't a know version");
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
                return "1.11.2";
            case "v1_12_R1":
                return "1.12.2";
            case "v1_13_R1":
                return "1.13";
            case "v1_13_R2":
                return "1.13.2";
            case "v1_14_R1":
                return "1.14.4";
            case "v1_15_R1":
                return "1.15.2";
            case "v1_16_R1":
                return "1.16.1";
            case "v1_16_R2":
                return "1.16.3";
            case "v1_16_R3":
                return "1.16.5";
            case "v1_17_R1":
                return "1.17";
        }
        throw new IllegalArgumentException(nms + " isn't a know version");
    }

}
