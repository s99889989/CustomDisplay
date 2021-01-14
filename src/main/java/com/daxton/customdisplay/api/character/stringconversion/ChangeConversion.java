package com.daxton.customdisplay.api.character.stringconversion;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.other.StringFind;

import java.util.List;

public class ChangeConversion {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ChangeConversion(){

    }

    public String valueOf(String inputString,String changeString){
        String outputString = "";
        String function = null;
        String message = null;
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
        if(function != null && message != null){
            outputString = ChangeConversion(inputString,function,message);
        }else {
            outputString = inputString;
        }

        return outputString;
    }

    public String ChangeConversion(String inputString,String function,String changeMessage){
        String outputString = "";

        switch (function.toLowerCase()){
            case "contain":
                String[] containKeyList = changeMessage.split(",");
                for(String containKey : containKeyList){
                    String[] containKeyList2 = containKey.split(">");
                    if(containKeyList2.length == 2){
                        inputString = inputString.replace(containKeyList2[0],containKeyList2[1]);
                    }
                }
                outputString = inputString;
                break;
            case "containall":
                String[] containallKeyList = changeMessage.split(">");
                if(inputString.contains(containallKeyList[0])){
                    outputString = containallKeyList[1];
                }
                break;
            case "exsame":
                String[] exsameKeyList = changeMessage.split(",");
                for(String exsameKey : exsameKeyList){
                    String[] exsameKeyList2 = exsameKey.split(">");
                    if(exsameKeyList2.length == 2){
                        if(inputString.equals(exsameKeyList2[0])){
                            inputString = exsameKeyList2[1];
                            break;
                        }
                    }
                }
                outputString = inputString;
                break;
            case "converhead":
                outputString = new NumberUtil().NumberHead2(inputString,changeMessage);
                break;
            case "converunits":
                outputString = new NumberUtil().NumberUnits2(inputString,changeMessage);
                break;
            case "converdouble":
                outputString = new NumberUtil().NumberDouble2(inputString,changeMessage);
                break;
            case "converaddrl":
                outputString = new NumberUtil().stringAddRight2(inputString,changeMessage);
                break;
            default:
                outputString = inputString;
        }

        return outputString;
    }
}
