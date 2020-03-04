package com.goochou.p2b.utils;

import java.util.UUID;

public class UUIDUtil
{
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    public static void main(String args[]){
    	System.out.println(UUIDUtil.getUuid());
    }
}
