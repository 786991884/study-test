package com.xubh.http;

import com.xubh.http.javanet.SendDataByHTTPS;

public class SendDataToolTest {


    public static void main(String[] args) throws Exception {
        String url = "https://www.facebook.com/";
        System.out.println(new SendDataByHTTPS().send("", url, null));
    }

}