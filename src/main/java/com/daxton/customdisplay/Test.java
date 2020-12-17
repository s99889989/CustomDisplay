package com.daxton.customdisplay;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Test {



    public static void main(String[] args){
        String str = "0xFF0000";
        int ss = 0xFF0000;

        BigInteger bigint=new BigInteger("FF0000", 16);
        int numb=bigint.intValue();

        System.out.println(ss);
        System.out.println(numb);
    }





}
