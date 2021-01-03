package com.daxton.customdisplay;

import static java.lang.Integer.parseInt;

public class Test {



    public static void main(String[] args){


        String str = "love23next234csdn3423javaeye";
        str=str.trim();
        String str2="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i ++ ){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    str2 = String.valueOf(str.charAt(i));
                }
            }
        }
        System.out.println(str2);



    }



}
