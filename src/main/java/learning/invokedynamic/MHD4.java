package learning.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MHD4 {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findStatic(Math.class, "pow",
                MethodType.methodType(double.class,
                        double.class,
                        double.class));
        mh = MethodHandles.insertArguments(mh, 1, 10);
        System.out.printf("2^10 = %d%n", (int) (double) mh.invoke(2.0));
    }
}
