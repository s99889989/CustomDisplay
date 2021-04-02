package com.daxton.customdisplay;




import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.other.NumberUtil;

import java.util.*;




public class Test {

    private String actionKey = "";
    private String aimsKey;
    private Map<String, String> actionMap;


    public static void main(String[] args) {
        String s = "Action[a=BoosBarDisplay;mark=&bsb_self_name&BoosBarDisplay;Loc=0|1|0;book=20f.5] ~onAttack @Target";
        Test test = new Test(s);
        float ss = test.getFloat(new String[]{"book"},1);

            System.out.println("值: "+ss);


    }

    public Test(String inputString){
        String input = ReplaceTrigger.valueOf(inputString);

        if (input.contains("[") && input.contains("]")) {
            int num1 = NumberUtil.appearNumber(input, "\\[");
            int num2 = NumberUtil.appearNumber(input, "\\]");
            if (num1 == 1 && num2 == 1) {
                this.actionMap = new HashMap<>();
                this.actionKey = input.substring(0, input.indexOf("[")).trim();
                this.aimsKey = input.substring(input.indexOf("]") + 1).trim();
                String listString = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
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

    public String getString(String[] key, String def) {
        String s = null;

        for(String ss : key){
            s = this.actionMap.get(ss.toLowerCase());
            if(s != null){
                return s;
            }
        }

        return def;
    }

    public String[] getStringList(String[] key, String[] def, String split, int amount){
        String[] output = null;
        String inputString = getString(key,null);
        if(inputString != null){
            output = inputString.split(split);
            if(output.length == amount){
                return output;
            }
        }
        return def;
    }

    public boolean getBoolean(String[] key){
        boolean output = false;
        String inputString = getString(key,null);
        if(inputString != null){
            output = Boolean.valueOf(inputString);
        }
        return output;
    }

    public int getInt(String[] key, int defaultKey){
        int output = defaultKey;

        String inputString = getString(key,null);
        if(inputString != null){
            try {
                output = Integer.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }
    public double getDouble(String[] key, double defaultKey){
        double output = defaultKey;
        String inputString = getString(key,null);
        if(inputString != null){
            try {
                output = Double.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }

        return output;
    }

    public float getFloat(String[] key, float defaultKey){
        float output = defaultKey;
        String inputString = getString(key,null);
        if(inputString != null){
            try {
                output = Float.valueOf(inputString);
            }catch (NumberFormatException exception){

            }
        }
        return output;
    }

    /**丟入字串和key 轉成List**/
    public static List<String> getBlockList(String string, String key){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(string,key);
        while(stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        return stringList;
    }

    public String getActionKey() {
        return actionKey;
    }

    public Map<String, String> getActionMap() {
        return actionMap;
    }
}
