package learning.hacker;

import lombok.val;

import javax.xml.bind.DatatypeConverter;

import static com.google.common.base.Charsets.UTF_8;
import static javax.xml.bind.DatatypeConverter.parseHexBinary;

/*
邮件地址：
        XDY1XDc2XDY5XDZjXDYzXDZmXDczXDQwXDY3XDZkXDYxXDY5XDZjXDJlXDYzXDZmXDZk
        邮件地址的解法是：base64 解下，然后 16 进制解下就好
*/

public class YuxianEmailDecode {
    public static void main(String[] args) {
        val original = "XDY1XDc2XDY5XDZjXDYzXDZmXDczXDQwXDY3XDZkXDYxXDY5XDZjXDJlXDYzXDZmXDZk";
        byte[] bytes = DatatypeConverter.parseBase64Binary(original);
        for (byte b : bytes) {
            System.out.print((char) b); // \65\76\69\6c\63\6f\73\40\67\6d\61\69\6c\2e\63\6f\6d
        }
        System.out.println();

        int size = bytes.length / 3;
        byte[] newb = new byte[size * 2];
        for (int i = 0; i < size; i++) {
            newb[i * 2] = bytes[i * 3 + 1];
            newb[i * 2 + 1] = bytes[i * 3 + 2];
        }

        val email = new String(parseHexBinary(new String(newb)), UTF_8);
        System.out.println(email); // evilcos@gmail.com
    }
}
