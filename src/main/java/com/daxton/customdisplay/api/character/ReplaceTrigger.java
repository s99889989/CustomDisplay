package com.daxton.customdisplay.api.character;

public class ReplaceTrigger {

    public ReplaceTrigger(){

    }

    public static String valueOf(String input){
        String output = "";
        input = input.replace("~onAttack","").replace("~onattack","");
        input = input.replace("~onCrit","").replace("~oncrit","");
        input = input.replace("~onMagic","").replace("~onmagic","");
        input = input.replace("~onMCrit","").replace("~onmcrit","");
        input = input.replace("~onAtkMiss","").replace("~onatkmiss","");
        input = input.replace("~onDamaged","").replace("~ondamaged","");
        input = input.replace("~onDamagedMiss","").replace("~ondamagedmiss","");
        input = input.replace("~onRegainHealth","").replace("~onregainhealth","");
        input = input.replace("~onJoin","").replace("~onjoin","");
        input = input.replace("~onQuit","").replace("~onquit","");
        input = input.replace("~onMove","").replace("~onmove","");
        input = input.replace("~onSneak","").replace("~onsneak","");
        input = input.replace("~onStandup","").replace("~onstandup","");
        input = input.replace("~onDeath","").replace("~ondeath","");
        input = input.replace("~onKeyFON","").replace("~onKeyfon","");
        input = input.replace("~onKeyFOFF","").replace("~onKeyfoff","");
        input = input.replace("~onKey1","").replace("~onkey1","");
        input = input.replace("~onKey2","").replace("~onkey2","");
        input = input.replace("~onKey3","").replace("~onkey3","");
        input = input.replace("~onKey4","").replace("~onkey4","");
        input = input.replace("~onKey5","").replace("~onkey5","");
        input = input.replace("~onKey6","").replace("~onkey6","");
        input = input.replace("~onKey7","").replace("~onkey7","");
        input = input.replace("~onKey8","").replace("~onkey8","");
        input = input.replace("~onKey9","").replace("~onkey9","");
        input = input.replace("~onChat","").replace("~onchat","");
        input = input.replace("~onCommand","").replace("~oncommand","");
        input = input.replace("~onLevelUp","").replace("~onlevelup","");
        input = input.replace("~onLevelDown","").replace("~onleveldown","");
        input = input.replace("~onExpUp","").replace("~onexpup","");
        input = input.replace("~onExpDown","").replace("~onexpdown","");
        input = input.replace("~onMobDeath","").replace("~onmobdeath","");
        input = input.replace("~onMMobDeath","").replace("~onmmobdeath","");
        input = input.replace("~onMMobDropExp","").replace("~onmmobdropexp","");
        input = input.replace("~onMMobDropMoney","").replace("~onmmobdropmoney","");
        input = input.replace("~onMMobDropItem","").replace("~onmmobdropitem","");
        output = input;

        return output;
    }

}
