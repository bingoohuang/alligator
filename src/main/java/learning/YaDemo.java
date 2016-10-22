package learning;

import lombok.val;

import static org.apache.commons.lang3.StringUtils.left;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;

/**
 * Created by bingoohuang on 2016/10/10.
 */
public class YaDemo {

    /*
    方法生成一个字符串 key 值，
    传入两个参数：服务全类名和方法名，然后返回 key 值，
    key 的长度受外部条件约束不能超过 50 个字符。
     */
    public String generateKeyVersion0(String service, String method) {
        String head = "DBO$";
        String key = "";

        int len = head.length() + service.length() + method.length();
        if (len <= 50) {
            key = head + service + "." + method;
        } else {
            service = service.substring(service.lastIndexOf(".") + 1);
            len = head.length() + service.length() + method.length();
            if (len > 50) {
                key = head + method;
                if (key.length() > 50) {
                    key = key.substring(0, 48) + ".~";
                }
            }
        }

        return key;
    }

    public String generateKeyVersion1(String serviceWithPkg, String method) {
        val head = "DBO$";
        val maxSize = 50;

        val key1 = head + serviceWithPkg + "." + method;
        if (key1.length() <= maxSize) return key1;

        val serviceWoPkg = substringAfterLast(serviceWithPkg, "/");
        val key2 = head + serviceWoPkg + "." + method;
        if (key2.length() <= maxSize) return key2;

        val key3 = head + method;
        if (key3.length() <= maxSize) return key3;

        return left(key3, maxSize - 2) + ".~";
    }
}
