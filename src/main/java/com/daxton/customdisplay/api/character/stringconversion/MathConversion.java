package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.ChatColor;

public class MathConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public MathConversion(){

    }

    public String valueOf(String inputString,String changeString){
        String outputString = "";
        String function = null;
        String message = "#";
        for(String string : new StringFind().getBlockList(changeString,";")){

            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function= strings[1];
                }
            }

            if(string.toLowerCase().contains("message=") || string.toLowerCase().contains("m=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    message= strings[1];
                }
            }

        }

        if(function != null){
            outputString = MathConversion(inputString,function,message);
        }else {
            outputString = inputString;
        }




        return outputString;
    }

    public String MathConversion(String inputString,String function,String message){
        String outputString = "";

        if(function.toLowerCase().contains("arithmetic") || function.toLowerCase().contains("arith")){
            try {
                double number = Arithmetic.eval(inputString);
                outputString = String.valueOf(number);
            }catch (Exception exception){
                outputString =  "0";
            }
        }

        if(function.toLowerCase().contains("accumulate") || function.toLowerCase().contains("acc")){

            String[] strings = inputString.split(",");
            if(strings.length == 2){
                try{
                    int count = 0;
                    for(int i = Integer.valueOf(strings[0]) ; i <= Integer.valueOf(strings[1]) ; i++){
                        count = count + i;

                        cd.getLogger().info(i+":"+Integer.valueOf(strings[1])+"數字: "+count);
                    }
                    outputString = String.valueOf(count);
                }catch (NumberFormatException exception){
                    outputString =  "0";
                }
            }
        }
        if(function.toLowerCase().contains("decimal") || function.toLowerCase().contains("dec")){
            try{
                double number = Double.valueOf(inputString);
                outputString = new NumberUtil(number,message).getDecimalString();
            }catch (NumberFormatException exception){
                outputString =  "0";
            }
        }

        return outputString;
    }

}
