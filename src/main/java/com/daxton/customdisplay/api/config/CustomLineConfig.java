package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

import java.math.BigInteger;
import java.util.*;

import static org.bukkit.Color.fromRGB;

public class CustomLineConfig implements Cloneable{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self;
    private LivingEntity target;

    private String actionKey;
    private String aimsKey = "@Self";
    private String permission;



    private List<LivingEntity> livingEntityList;
    private List<Location> locationList;

    private Map<String, String> actionMap;

    public CustomLineConfig(){

    }

    public CustomLineConfig(String inputString){

        String input = ReplaceTrigger.valueOf(inputString);

        if (input.contains("[") && input.contains("]")) {

            int num1 = NumberUtil.appearNumber(input, "\\[");
            int num2 = NumberUtil.appearNumber(input, "\\]");
            if (num1 == 1 && num2 == 1) {

                this.actionMap = new HashMap<>();
                this.actionKey = input.substring(0, input.indexOf("[")).trim();
                if(input.substring(input.indexOf("]")).contains("@")){
                    if(input.substring(input.indexOf("]")).contains("#")){
                        this.aimsKey = input.substring(input.indexOf("]") + 1,input.indexOf("#")).trim();
                        String s2 = input.substring(input.indexOf("]"));
                        this.permission = s2.substring(s2.indexOf("#") + 1).trim();
                    }else {
                        this.aimsKey = input.substring(input.indexOf("]") + 1).trim();
                    }
                }else {
                    if(input.substring(input.indexOf("]")).contains("#")){
                        String s2 = input.substring(input.indexOf("]"));
                        this.permission = s2.substring(s2.indexOf("#") + 1).trim();
                    }
                }


                String listString = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
                if(this.actionKey.toLowerCase().contains("condition")){
                    //String conditionKey = input.substring(input.indexOf("[") + 1,input.indexOf("]"));
                    actionMap.put("condition", listString);
                }
                List<String> stringList = getBlockList(listString, ";");
                stringList.forEach(s -> {
                    String[] strings = s.split("=");
                    if (strings.length == 2) {
                        actionMap.put(strings[0].toLowerCase(), strings[1]);
                    }
                });

            }
        }


    }


    /**獲取對象**/
    public List<LivingEntity> getLivingEntityList(LivingEntity self, LivingEntity target){

        livingEntityList = new Aims().valueOf2(self, target,this.aimsKey);
        return livingEntityList;
    }


    public String getString(String[] key, String def, LivingEntity self, LivingEntity target) {
        String output = null;

        for(String ss : key){
            output = this.actionMap.get(ss.toLowerCase());
            if(output != null){
                if(output.contains("&")){
                    output = new ConversionMain().valueOf(self,target,output);
                }
                return output;
            }
        }

        return def;
    }

    public String[] getStringList(String[] key, String[] def, String split, int amount, LivingEntity self, LivingEntity target){
        String[] output = null;
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            output = inputString.split(split);
            if(output.length == amount){
                return output;
            }
        }
        return def;
    }

    public boolean getBoolean(String[] key, boolean def, LivingEntity self, LivingEntity target){
        boolean output = def;
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            output = Boolean.valueOf(inputString);
        }
        return output;
    }

    public int getInt(String[] key, int defaultKey, LivingEntity self, LivingEntity target){
        int output = defaultKey;

        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Integer.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }
    public double getDouble(String[] key, double defaultKey, LivingEntity self, LivingEntity target){
        double output = defaultKey;
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Double.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }

        return output;
    }

    public float getFloat(String[] key, float defaultKey, LivingEntity self, LivingEntity target){
        float output = defaultKey;
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Float.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }

    /**BossBarFlag**/
    public BarFlag getBarFlag(String[] key, LivingEntity self, LivingEntity target){
        BarFlag output = null;
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarFlag.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**BossBar顏色**/
    public BarColor getBarColor(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        BarColor output = Enum.valueOf(BarColor.class ,defaultKey);
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarColor.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }
        return output;
    }

    /**BossBar樣式**/
    public BarStyle getBarStyle(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        BarStyle output = Enum.valueOf(BarStyle.class ,defaultKey);
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(BarStyle.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }
        return output;
    }

    /**粒子**/
    public Particle getParticle(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        Particle output = Enum.valueOf(Particle.class ,defaultKey);
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(Particle.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**材質副值**/
    public BlockData getBlockData(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        BlockData output = Enum.valueOf(Material.class ,defaultKey).createBlockData();
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(Material.class ,inputString.toUpperCase()).createBlockData();
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**物品**/
    public ItemStack getItemStack(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        ItemStack output = new ItemStack(Enum.valueOf(Material.class ,defaultKey));
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = new ItemStack(Enum.valueOf(Material.class ,inputString.toUpperCase()));
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**粒子顏色**/
    public Particle.DustOptions getParticleColor(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        BigInteger bigint = new BigInteger(defaultKey, 16);
        int numb = bigint.intValue();
        Particle.DustOptions output = new Particle.DustOptions(fromRGB(numb), 1);

        String inputString = getString(key,defaultKey, self ,target);

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
    public ChatColor getChatColor(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        ChatColor output = Enum.valueOf(ChatColor.class ,defaultKey);
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(ChatColor.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**聲音的分類**/
    public SoundCategory getSoundCategory(String[] key, String defaultKey, LivingEntity self, LivingEntity target){
        SoundCategory output = Enum.valueOf(SoundCategory.class ,defaultKey);
        String inputString = getString(key,null, self ,target);
        if(inputString != null){
            try {
                output = Enum.valueOf(SoundCategory.class ,inputString.toUpperCase());
            }catch (IllegalArgumentException exception){

            }
        }

        return output;
    }

    /**藥水的類型**/
    public PotionEffectType getPotionEffectType(String[] key, PotionEffectType defaultKey, LivingEntity self, LivingEntity target){
        PotionEffectType output = PotionEffectType.INCREASE_DAMAGE;

        String inputString = getString(key,null, self ,target);
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

    public String getActionKey() {
        return actionKey;
    }

    /**根據動作名稱 返回動作列表**/
    public List<CustomLineConfig> getActionKeyList(String[] key, String def, LivingEntity self, LivingEntity target){
        List<CustomLineConfig> customLineConfigList = new ArrayList<>();
        String inputString = getString(key,def, self, target);

        if(inputString != null){
            //cd.getLogger().info(inputString);
            for(String string1 : ConfigMapManager.getFileConfigurationNameMap().values()){
                if(string1.contains("Actions_")){
                    FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(string1);
                    if(fileConfiguration.getKeys(false).contains(inputString)){
                        List<String > stringList = fileConfiguration.getStringList(inputString+".Action");
                        for(String s : stringList){

                            customLineConfigList.add(new CustomLineConfig(s));
                        }
                    }
                }
            }
        }

        return customLineConfigList;
    }

    /**丟入字串和key 轉成List**/
    public List<String> getBlockList(String string, String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }


    public List<Location> getLocationList() {
        return locationList;
    }

    public String getPermission() {
        return permission;
    }

    /**克隆**/
    @Override
    public Object clone() {
        CustomLineConfig customLineConfig = null;
        try{
            customLineConfig = (CustomLineConfig)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return customLineConfig;
    }



}
