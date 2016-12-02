package bhz.test;

import bhz.http.HttpCallerUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpTest2 {


    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String str = HttpCallerUtils.get("http://localhost:8888/userService/1001", map);
        System.out.println(str);
    }
}
