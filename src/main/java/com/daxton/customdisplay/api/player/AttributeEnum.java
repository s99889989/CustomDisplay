package com.daxton.customdisplay.api.player;

public enum AttributeEnum {


    Health_Regeneration(0, "優異"),
    Max_Mana(0, "佳"),
    Mana_Regeneration(0, "良好"),
    Dodge_Rate(0, "普通"),
    Hit_Rate(0, "略差"),
    Critical_Strike_Chance(0, "略差"),
    Critical_Strike_Protection(0, "略差"),
    Critical_Strike_Power(0, "略差"),
    Critical_Strike_Reduction(0, "多努力");


    private int score;
    private String description;


    AttributeEnum(int score, String desc) {
        this.score = score;
        this.description = desc;
    }

    public int getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }



}
