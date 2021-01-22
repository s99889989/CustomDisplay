package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.ChatColor;

public class ConversionMath {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ConversionMath(){

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
        if(function.toLowerCase().contains("greater")){

            String[] strings = message.split(">");
            if(strings.length == 2){
                try{
                    if(Double.valueOf(inputString) > Double.valueOf(strings[0])){
                        outputString = strings[1];
                    }else {
                        outputString = inputString;
                    }
                }catch (NumberFormatException exception){
                    outputString = inputString;
                }
            }else {
                outputString = inputString;
            }

        }
        if(function.toLowerCase().contains("less")){
            String[] strings = message.split(">");
            if(strings.length == 2){
                try{
                    if(Double.valueOf(inputString) < Double.valueOf(strings[0])){
                        outputString = strings[1];
                    }else {
                        outputString = inputString;
                    }
                }catch (NumberFormatException exception){
                    outputString = inputString;
                }
            }else {
                outputString = inputString;
            }
        }

        return outputString;
    }

}
