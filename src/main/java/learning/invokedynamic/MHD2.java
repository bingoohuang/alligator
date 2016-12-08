package learning.invokedynamic;

import lombok.SneakyThrows;
import lombok.val;

import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodType.methodType;

class HW {
    public void hello1() {
        System.out.println("hello from hello1");
    }
}

public class MHD2 {
    @SneakyThrows public static void main(String[] args) {
        HW hw = new HW();
        val lookup = MethodHandles.lookup();
        val mh = lookup.findVirtual(HW.class, "hello1", methodType(void.class));
        mh.invoke(hw);
    }
}