package com.github.bingoohuang.mysql.pump;

public class Person {
    String id, name, addr;
    int sex;

    public Person(String id, String name, int sex, String addr) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.addr = addr;
    }

    public static Person create(String id, String name, int sex, String addr) {
        return new Person(id, name, sex, addr);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


}
