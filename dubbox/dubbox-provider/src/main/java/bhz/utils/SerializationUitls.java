package bhz.utils;

import java.io.*;

/**
 */
public class SerializationUitls {

    //private static String FILE_NAME  = "C:/obj.bin";

    //序列化方法
    public static void writeObject(Serializable s, String filename) {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(s);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反序列化
    public static Object readObject(String filename) {
        Object obj = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
