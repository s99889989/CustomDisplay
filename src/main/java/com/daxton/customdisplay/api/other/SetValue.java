package com.daxton.customdisplay.api.other;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import static org.bukkit.Color.fromRGB;

public class SetValue {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    String inputString = "";

    public SetValue(){

    }

    public SetValue(LivingEntity self, LivingEntity target, String string, String cut, String defaultValue, String... key){
        List<String> KeyList = getBlockList(string,cut);
        String[] itemIDStrings = KeyList.stream().filter(s -> Arrays.stream(key).anyMatch(s.toLowerCase()::contains)).collect(Collectors.joining()).split("=");
        String outPut = defaultValue;
        if(itemIDStrings.length == 2){
            outPut = itemIDStrings[1];

            if(outPut.contains("&")){
                outPut = new ConversionMain().valueOf(self,target,outPut);
            }
        }
        inputString = outPut;
    }

    public String getString(){
        String output = "";
        output = inputString;
        return output;
    }

    public String[] getStringList(String sq){
        String[] output = null;
        output = inputString.split(sq);
        return output;
    }

    public boolean getBoolean(){
        boolean output = false;
        output = Boolean.valueOf(inputString);
        return output;
    }

    public int getInt(int defaultKey){
        int output = defaultKey;
        try {
            output = Integer.valueOf(inputString);
        }catch (NumberFormatException exception){

        }

        return output;
    }

    public double getDouble(double defaultKey){
        double output = defaultKey;
        try {
            output = Double.valueOf(inputString);
        }catch (NumberFormatException exception){

        }

        return output;
    }

    public float getFloat(float defaultKey){
        float output = defaultKey;
        try {
            output = Float.valueOf(inputString);
        }catch (NumberFormatException exception){

        }

        return output;
    }

    /**BossBarFlag**/
    public BarFlag getBarFlag(){
        BarFlag output = null;
        try {
            output = Enum.valueOf(BarFlag.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }
    /**BossBar顏色**/
    public BarColor getBarColor(String defaultKey){
        BarColor output = Enum.valueOf(BarColor.class ,defaultKey);
        try {
            output = Enum.valueOf(BarColor.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }
    /**BossBar樣式**/
    public BarStyle getBarStyle(String defaultKey){
        BarStyle output = Enum.valueOf(BarStyle.class ,defaultKey);
        try {
            output = Enum.valueOf(BarStyle.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**粒子**/
    public Particle getParticle(String defaultKey){
        Particle output = Enum.valueOf(Particle.class ,defaultKey);
        try {
            output = Enum.valueOf(Particle.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**材質副值**/
    public BlockData getBlockData(String defaultKey){
        BlockData output = Enum.valueOf(Material.class ,defaultKey).createBlockData();
        try {
            output = Enum.valueOf(Material.class ,inputString.toUpperCase()).createBlockData();
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**物品**/
    public ItemStack getItemStack(String defaultKey){
        ItemStack output = new ItemStack(Enum.valueOf(Material.class ,defaultKey));
        try {
            output = new ItemStack(Enum.valueOf(Material.class ,inputString.toUpperCase()));
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**粒子顏色**/
    public Particle.DustOptions getParticleColor(String defaultKey){
        BigInteger bigint = new BigInteger(defaultKey, 16);
        int numb = bigint.intValue();
        Particle.DustOptions output = new Particle.DustOptions(fromRGB(numb), 1);
        try {
            bigint = new BigInteger(inputString, 16);
            numb = bigint.intValue();
            output = new Particle.DustOptions(fromRGB(numb), 1);
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**顏色**/
    public ChatColor getChatColor(String defaultKey){
        ChatColor output = Enum.valueOf(ChatColor.class ,defaultKey);
        try {
            output = Enum.valueOf(ChatColor.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**聲音的分類**/
    public SoundCategory getSoundCategory(String defaultKey){
        SoundCategory output = Enum.valueOf(SoundCategory.class ,defaultKey);
        try {
            output = Enum.valueOf(SoundCategory.class ,inputString.toUpperCase());
        }catch (IllegalArgumentException exception){

        }
        return output;
    }

    /**丟入字串和key 轉成List**/
    public List<String> getBlockList(String string,String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

}
