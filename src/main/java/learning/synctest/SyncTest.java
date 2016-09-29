package learning.synctest;

public class SyncTest {
    public static class Animal {
        public synchronized void sayHello() {
            System.out.println("Hello Animal");
        }
    }

    public static class Dog extends Animal {
        public synchronized void sayHello() {
            System.out.println("Hello Dog");
            super.sayHello();
        }
    }


    public static void main(String[] args) {
        new Dog().sayHello();
    }
}
