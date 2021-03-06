package com.daxton.customdisplay.task.action.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Message3 {

    public Message3(){

    }

    public static void setMessage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);



        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {

            ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);

            //獲得內容
            String message = actionMapHandle2.getString(new String[]{"message","m"},"");

            sendMessage(livingEntity, target, message);
            //sendMessage2(livingEntity, target, message);
        });




    }

    public static void sendMessage2(LivingEntity self, LivingEntity target, String message){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        char[] messageChar = message.toCharArray();
        int charLength = messageChar.length;
        String key = "";
        for(int i = 0; i < charLength; ++i) {

            key += String.valueOf(messageChar[i]);

            cd.getLogger().info(String.valueOf(messageChar[i]));
        }

    }

    public static void sendMessage(LivingEntity self, LivingEntity target, String message){

        if(message.contains("{") && message.contains("}")){
            TextComponent all = new TextComponent("");
            for(String me : StringFind.getBlockList(message,"{}")){
                all.addExtra(getKey(self, target, me));
            }
            self.spigot().sendMessage(all);
        }else {
            self.sendMessage(message);
        }

    }

    public static TextComponent getKey(LivingEntity self,LivingEntity target, String messageString){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        TextComponent textComponent = new TextComponent(messageString);
        File filePatch = new File(cd.getDataFolder(),"Message");
        for(String ymlName : filePatch.list()){
            if(ymlName.contains(".yml")){

                File fileYmlPatch = new File(cd.getDataFolder(),"Message/"+ymlName);
                FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(fileYmlPatch);
                ConfigurationSection buttonNameList = messageConfig.getConfigurationSection("");
                if(buttonNameList.getKeys(false).contains(messageString)){
                    String text = messageConfig.getString(messageString+".Text");
                    boolean bold = messageConfig.getBoolean(messageString+".Bold");
                    if(text.contains("&")){
                        text = new ConversionMain().valueOf(self,target,text);
                    }
                    String color = messageConfig.getString(messageString+".Color");
                    String click_action = messageConfig.getString(messageString+".ClickEvent.Action");
                    String click_value = messageConfig.getString(messageString+".ClickEvent.Text");
                    if(click_value.contains("&")){
                        click_value = new ConversionMain().valueOf(self,target,click_value);
                    }

                    String hover_action = messageConfig.getString(messageString+".HoverEvent.Action");
                    String hover_value = messageConfig.getString(messageString+".HoverEvent.Text");
                    if(hover_value.contains("&")){
                        hover_value = new ConversionMain().valueOf(self,target,hover_value);
                    }
                    textComponent.setBold(bold);
                    if(color != null){
//                        BigInteger bigint = new BigInteger(color, 16);
//                        int numb = bigint.intValue();
//                        fromRGB(numb)
                        textComponent.setColor(ChatColor.valueOf(color));
                    }

                    if(click_action != null && click_value != null){

                        textComponent.setClickEvent(new ClickEvent(Enum.valueOf(ClickEvent.Action.class,click_action),click_value));
                    }

                    if(hover_action != null && hover_value != null){

                        textComponent.setHoverEvent( new HoverEvent(Enum.valueOf(HoverEvent.Action.class,hover_action), new Text(hover_value)));
                    }
                    if(text != null){
                        textComponent.setText(text);
                    }

                    //player.sendMessage("包含: "+click_action);
                }
                //buttonNameList.getKeys(false).forEach((line)->{player.sendMessage(line);});


            }

        }
        return textComponent;
    }


}
