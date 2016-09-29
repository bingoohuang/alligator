package learning.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MHD1 {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findStatic(MHD1.class, "hello",
                MethodType.methodType(void.class));
        mh.invokeExact();
    }

    static void hello() {
        System.out.println("hello");
    }
}