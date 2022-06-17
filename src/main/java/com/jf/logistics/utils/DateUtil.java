package com.jf.logistics.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.stream.events.EndDocument;
import java.text.SimpleDateFormat;

public class DateUtil {

    public static String getNowTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }
    public static void main(String args[]){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }
}
