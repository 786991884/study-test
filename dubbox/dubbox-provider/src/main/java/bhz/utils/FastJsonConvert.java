/**
 * <br>项目名: pay-service
 * <br>文件名: FastJsonConvert.java
 * <br>Copyright 2015 恒昌互联网运营中
 */
package bhz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * <br>
 * 类 名: FastJsonConvert <br>
 * 描 述: FastJsonConvert 类描述: FastJSON 与 JavaBean 相互转换类 <br>
 */
public class FastJsonConvert {


    private static final SerializerFeature[] featuresWithNullValue = {SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty};

    /**
     * JsonString 转换 Object
     *
     * @param <T>
     * @param data
     * @param clzss
     * @return
     */
    public static <T> T convertJSONToObject(String data, Class<T> clzss) {
        try {
            T t = JSON.parseObject(data, clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * JsonString 转换 List<Object>
     *
     * @param <T>
     * @param data
     * @param clzss
     * @return
     */
    public static <T> List<T> convertJSONToArray(String data, Class<T> clzss) {
        try {
            List<T> t = JSON.parseArray(data, clzss);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * Object to JsonString
     *
     * @param <T>
     * @param data
     * @param valueType
     * @return
     */
    public static String convertObjectToJSON(Object obj) {
        try {
            String text = JSON.toJSONString(obj);
            System.out.println(text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Object to JsonString
     *
     * @param <T>
     * @param data
     * @param valueType
     * @return
     */
    public static String convertObjectToJSONWithNullValue(Object obj) {
        try {
            String text = JSON.toJSONString(obj, featuresWithNullValue);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.err.println(System.getProperties());
    }
}
