package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.api.character.StringConversion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Compare {

    private double left = 0;
    private double right = 0;
    private String symbol = "";

    public Compare(){

    }

    public boolean judgment(String firstString, LivingEntity target, Player player){
        setCompare(firstString,player);
        boolean bo = false;

        bo = condition();

        return bo;
    }

    public boolean judgment(String firstString, Player player){
        setCompare(firstString,player);
        boolean bo = false;

        bo = condition();

        return bo;
    }

    public void setCompare(String firstString,Player player){
        symbol = firstString.toLowerCase().replace("compare=","").replace("[","").replace("]","");
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[=]<> ");
        while (stringTokenizer.hasMoreElements()){

            stringList.add(stringTokenizer.nextToken());
        }
        if(stringList.size() == 4){
            String[] strings = stringList.toArray(new String[stringList.size()]);
            left = Double.valueOf(new StringConversion().customString("Character",strings[2],player));
            right = Double.valueOf(strings[3]);
        }
    }

    public boolean condition(){
        boolean b = true;
        if(symbol.contains("<")){
            if(left < right){
                b = false;
            }
        }
        if(symbol.contains(">")){
            if(left > right){
                b = false;
            }
        }
        if(symbol.contains("=")){
            if(left == right){
                b = false;
            }
        }
        return b;
    }

}
