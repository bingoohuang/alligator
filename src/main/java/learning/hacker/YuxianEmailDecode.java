package learning.hacker;

import javax.xml.bind.DatatypeConverter;

/*
邮件地址：
        XDY1XDc2XDY5XDZjXDYzXDZmXDczXDQwXDY3XDZkXDYxXDY5XDZjXDJlXDYzXDZmXDZk
        邮件地址的解法是：base64 解下，然后 16 进制解下就好
*/

public class YuxianEmailDecode {
    public static void main(String[] args) {
        String original = "XDY1XDc2XDY5XDZjXDYzXDZmXDczXDQwXDY3XDZkXDYxXDY5XDZjXDJlXDYzXDZmXDZk";
        byte[] bytes = DatatypeConverter.parseBase64Binary(original);
        for (byte b : bytes) {
            System.out.print((char)b); // \65\76\69\6c\63\6f\73\40\67\6d\61\69\6c\2e\63\6f\6d
        }
        System.out.println();

        int size = bytes.length / 3 ;
        byte[] newb = new byte[size * 2];
        for (int i = 0; i < size; i++) {
            newb[i * 2] = bytes[i * 3 + 1];
            newb[i * 2 + 1] = bytes[i * 3 + 2];
        }

        String email = new String(DatatypeConverter.parseHexBinary(new String(newb)));
        System.out.println(email); // evilcos@gmail.com
    }
}
