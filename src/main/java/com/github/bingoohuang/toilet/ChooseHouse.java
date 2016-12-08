package com.github.bingoohuang.toilet;

import static com.github.bingoohuang.toilet.ChooseHouseV3.Sex.MAN;
import static com.github.bingoohuang.toilet.ChooseHouseV3.Sex.WOMAN;


// 陈旭元『临危受命』，要写代码为大家解决内急的问题
public class ChooseHouse {
    public void chooseHouse(String sex) {
        if ("男".equals(sex)) {
            System.out.println("如男厕");
        } else {
            System.out.println("如女厕");
        }
    }
}

// 史习生同学刚来不久，要调用陈旭元的代码
class NewbieProgramme {
    public static void main(String[] args) {
        // 以前我们都是用1代表男，0代表女，我来试试看
        new ChooseHouse().chooseHouse("0");
        // 结果打印出『如女厕』，正如我所猜测

        // 此处省略若干代码行

        // 这回是要如男厕了，这里就不用再测试了吧，直接上生产
        new ChooseHouse().chooseHouse("1");
        // 生产挂了，因为男生『如女厕』，太流氓，史习生一脸懵逼
    }
}

// 陈旭元被投诉流氓了，表示很无辜，但是仍然反思了，并重构了代码
class ChooseHouseV2 {
    public void chooseHouse(String sex) {
        if ("男".equals(sex)) {
            System.out.println("如男厕");
        } else if ("女".equals(sex)) {
            System.out.println("如女厕");
        } else {
            System.out.println("非男非女，非人，表如厕");
        }
    }
}

// 忻如志是最近入职的有1年工作经验的威猛小伙子
class Newbie2Programme {
    public static void main(String[] args) {
        // 林丹是我的男神，即使出轨了，还是我的男神
        new ChooseHouseV2().chooseHouse("男神");
        // 竟然说男神『非男非女，非人，表如厕』，我打打打你
    }
}

// 陈旭元被投打了，又打不过忻如志，找老板理论没得到支持，
// 只能反思了之后，继续重构了代码
class ChooseHouseV3 {
    enum Sex { MAN, WOMAN }

    public void chooseHouse(Sex sex) {
        switch (sex) {
            case MAN:
                System.out.println("如男厕");
                break;
            case WOMAN:
                System.out.println("如女厕");
                break;
        }
    }
}

// 从此陈旭元终于过上了『幸福』的生活
// 史习生再也没有骂过他，陈旭元再也没打过他，大家之间亲如兄弟
class Newbie3Programme {
    public static void main(String[] args) {
        new ChooseHouseV3().chooseHouse(MAN); // 一切正常
        new ChooseHouseV3().chooseHouse(WOMAN); // 一切正常
    }
}