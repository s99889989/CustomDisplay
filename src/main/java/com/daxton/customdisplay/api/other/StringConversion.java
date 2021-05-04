package com.daxton.customdisplay.api.other;

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
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;

import static org.bukkit.Color.fromRGB;

public class StringConversion {

    public StringConversion(){

    }

    public StringConversion(LivingEntity self, LivingEntity target){

    }

    public static String getString(LivingEntity self, LivingEntity target, String content) {
        String output = content;
        output = ConversionMain.valueOf(self, target, output);
        return output;
    }

    public static boolean getBoolean(LivingEntity self, LivingEntity target, boolean defaultKey, String content){
        boolean output = defaultKey;
        String inputString = getString(self, target, content);
        if(inputString != null){
            output = Boolean.parseBoolean(inputString);
        }
        return output;
    }

    public static long getLong(LivingEntity self, LivingEntity target, long defaultKey, String content){
        long output = defaultKey;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Long.parseLong(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }

    public static int getInt(LivingEntity self, LivingEntity target, int defaultKey, String content){
        int output = defaultKey;

        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Integer.parseInt(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }
    public static double getDouble(LivingEntity self, LivingEntity target, double defaultKey, String content){
        double output = defaultKey;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Double.parseDouble(inputString);
            }catch (NumberFormatException exception){

            }
        }

        return output;
    }

    public static float getFloat(LivingEntity self, LivingEntity target, float defaultKey, String content){
        float output = defaultKey;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Float.parseFloat(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }

    /**BossBarFlag**/
    public static BarFlag getBarFlag(LivingEntity self, LivingEntity target, String content){
        BarFlag output = null;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarFlag.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }


    /**BossBar顏色**/
    public static BarColor getBarColor(LivingEntity self, LivingEntity target, float defaultKey, String content){
        //BarColor output = Enum.valueOf(BarColor.class ,defaultKey);
        BarColor output = null;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarColor.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }
        return output;
    }

    /**BossBar樣式**/
    public static BarStyle getBarStyle(LivingEntity self, LivingEntity target, float defaultKey, String content){
        //BarStyle output = Enum.valueOf(BarStyle.class ,defaultKey);
        BarStyle output = null;
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarStyle.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }
        return output;
    }

    /**粒子**/
    public static Particle getParticle(LivingEntity self, LivingEntity target, String defaultKey, String content){
        Particle output = Enum.valueOf(Particle.class ,defaultKey);
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(Particle.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**材質副值**/
    public static BlockData getBlockData(LivingEntity self, LivingEntity target, String defaultKey, String content){
        BlockData output = Enum.valueOf(Material.class ,defaultKey).createBlockData();
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(Material.class ,inputString.toUpperCase()).createBlockData();
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**物品**/
    public static ItemStack getItemStack(LivingEntity self, LivingEntity target, String defaultKey, String content){
        ItemStack output = new ItemStack(Enum.valueOf(Material.class ,defaultKey));
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = new ItemStack(Enum.valueOf(Material.class ,inputString.toUpperCase()));
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**粒子顏色**/
    public static Particle.DustOptions getParticleColor(LivingEntity self, LivingEntity target, String defaultKey, String content){
        BigInteger bigint = new BigInteger(defaultKey, 16);
        int numb = bigint.intValue();
        Particle.DustOptions output = new Particle.DustOptions(fromRGB(numb), 1);

        String inputString = getString(self, target, content);

        if(inputString != null){
            try {
                bigint = new BigInteger(inputString, 16);
                numb = bigint.intValue();
                output = new Particle.DustOptions(fromRGB(numb), 1);
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**顏色**/
    public static ChatColor getChatColor(LivingEntity self, LivingEntity target, String defaultKey, String content){
        ChatColor output = Enum.valueOf(ChatColor.class ,defaultKey);
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(ChatColor.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**聲音的分類**/
    public static SoundCategory getSoundCategory(LivingEntity self, LivingEntity target, String defaultKey, String content){
        SoundCategory output = Enum.valueOf(SoundCategory.class ,defaultKey);
        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                output = Enum.valueOf(SoundCategory.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**藥水的類型**/
    public static PotionEffectType getPotionEffectType(LivingEntity self, LivingEntity target, String defaultKey, String content){
        PotionEffectType output = PotionEffectType.INCREASE_DAMAGE;

        String inputString = getString(self, target, content);
        if(inputString != null){
            try {
                for (PotionEffectType pe : PotionEffectType.values()) {
                    if(pe.getName().equals(inputString.toUpperCase())){
                        output = pe;
                    }
                }
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }


}
