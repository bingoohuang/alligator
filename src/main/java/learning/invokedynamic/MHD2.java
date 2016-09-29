package learning.invokedynamic;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class HW {
    public void hello1() {
        System.out.println("hello from hello1");
    }
}

public class MHD2 {
    @SneakyThrows
    public static void main(String[] args) {
        HW hw = new HW();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(HW.class, "hello1",
                MethodType.methodType(void.class));
        mh.invoke(hw);
    }
}