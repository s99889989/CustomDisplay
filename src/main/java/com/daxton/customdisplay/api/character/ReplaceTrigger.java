package com.daxton.customdisplay.api.character;

public class ReplaceTrigger {

    public ReplaceTrigger(){

    }

    public String valueOf(String input){
        String output = "";
        input = input.replace("~onAttack","");
        input = input.replace("~onCrit","");
        input = input.replace("~onMagic","");
        input = input.replace("~onMCrit","");
        input = input.replace("~onAtkMiss","");
        input = input.replace("~onDamaged","");
        input = input.replace("~onDamagedMiss","");
        input = input.replace("~onRegainHealth","");
        input = input.replace("~onJoin","");
        input = input.replace("~onQuit","");
        input = input.replace("~onMove","");
        input = input.replace("~onSneak","");
        input = input.replace("~onStandup","");
        input = input.replace("~onDeath","");
        input = input.replace("~onKeyFON","");
        input = input.replace("~onKeyFOFF","");
        input = input.replace("~onKey1","");
        input = input.replace("~onKey2","");
        input = input.replace("~onKey3","");
        input = input.replace("~onKey4","");
        input = input.replace("~onKey5","");
        input = input.replace("~onKey6","");
        input = input.replace("~onKey7","");
        input = input.replace("~onKey8","");
        input = input.replace("~onKey9","");
        input = input.replace("~onChat","");
        input = input.replace("~onCommand","");
        input = input.replace("~onLevelUp","");
        input = input.replace("~onLevelDown","");
        input = input.replace("~onExpUp","");
        input = input.replace("~onExpDown","");
        input = input.replace("~onMobDeath","");
        input = input.replace("~onMMobDeath","");
        input = input.replace("~onMMobDropExp","");
        input = input.replace("~onMMobDropMoney","");
        input = input.replace("~onMMobDropItem","");
        output = input;

        return output;
    }

}
