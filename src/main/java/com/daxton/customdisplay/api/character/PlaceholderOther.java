package com.daxton.customdisplay.api.character;

import com.daxton.customdisplay.CustomDisplay;

public class PlaceholderOther {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String anser = "";

    public PlaceholderOther(){


    }
    public String getOther(String firstString){
        String key = firstString.toLowerCase().replace("<cd_other_","").replace(">","");
        if(key.toLowerCase().contains("math_random_")){
            String randomKey = key.replace("math_random_","");
            try {
                int randomNumber = Integer.valueOf(randomKey);
                anser = String.valueOf((int)(Math.random()*randomNumber));
            }catch (Exception exception){
                anser = firstString + "內的" + randomKey + "只能放數字";
                anser = "The "+ randomKey +" in "+firstString+" can only hold numbers";
            }
        }


        return anser;
    }


}
