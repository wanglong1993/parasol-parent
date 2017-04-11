package com.ginkgocap.parasol.util;

import java.sql.Timestamp;



public class MakePrimaryKey {
    public static String getPrimaryKey(){
        int year=new Timestamp(System.currentTimeMillis()).getYear();
        int month=(new Timestamp(System.currentTimeMillis()).getMonth()+1);
        int day=new Timestamp(System.currentTimeMillis()).getDate();
        int hours=new Timestamp(System.currentTimeMillis()).getHours();
        int minte=new Timestamp(System.currentTimeMillis()).getMinutes();
        long time=new Timestamp(System.currentTimeMillis()).getTime();
        
        String num=String.valueOf(year).substring(1)+String.valueOf(month)+String.valueOf(day)
        +String.valueOf(hours)+String.valueOf(minte)+String.valueOf(time).substring(8, 13);
        return num;
    }
}
