package com.wzn.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    //加法
    public static BigDecimal add(double d1,double d2){
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.add(bigDecimal1);
    }
    //减法
    public static BigDecimal sub(double d1,double d2){
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.subtract(bigDecimal1);
    }
    //乘法
    public static BigDecimal mul(double d1,double d2){
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.multiply(bigDecimal1);
    }
    //除法
    public static BigDecimal div(double d1,double d2){
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.divide(bigDecimal1,2,BigDecimal.ROUND_HALF_UP);//四舍五入
    }
}
