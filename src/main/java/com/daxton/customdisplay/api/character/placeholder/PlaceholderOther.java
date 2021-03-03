package com.daxton.customdisplay.api.character.placeholder;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;

public class PlaceholderOther {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String anser = "";

    public PlaceholderOther(){


    }
    public String getOther(String firstString){
        String outputString = "";
        String key = firstString.toLowerCase().replace("<cd_other_","").replace(">","");
        if(key.toLowerCase().contains("math_random_")){
            String randomKey = key.replace("math_random_","");
            try {
                int randomNumber = Integer.valueOf(randomKey);
                outputString = String.valueOf((int)(Math.random()*randomNumber));
            }catch (Exception exception){
                outputString = "The "+ randomKey +" in "+firstString+" can only hold numbers";
            }
        }



        return outputString;
    }


}
