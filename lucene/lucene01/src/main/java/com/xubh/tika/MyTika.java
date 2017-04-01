package com.xubh.tika;

import org.apache.tika.Tika;
import org.junit.Test;

import java.io.File;

public class MyTika {

    @Test
    public void readerDoc() throws Exception {
        //创建tika对象
        Tika tika = new Tika();
        String str = tika.parseToString(new File("F:\\searchsource\\2.xls"));
        System.out.println(str);

    }

}
