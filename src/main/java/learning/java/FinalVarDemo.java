package learning.java;


public class FinalVarDemo {
    public static void main(String[] args) {
        final String a = func();
        final String b = null;
        System.out.println(a);
        System.out.println(b);
    }

    private static String func() {
        return null;
    }
}
