package com.xubh.utils;

import java.math.BigDecimal;

/**
 * 常用数字工具
 */
public class CommonUtil {

    /**
     * 将 number保留scale小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static double decimal(double number, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b = new BigDecimal(number);
        double decimal = b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return decimal;
    }

}
