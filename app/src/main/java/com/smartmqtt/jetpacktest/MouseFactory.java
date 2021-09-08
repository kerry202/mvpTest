package com.smartmqtt.jetpacktest;

/**
 * @author: kerry
 * date: On $ {DATE}
 * <p>
 *
 *  抽象工厂模式 VS 建造者模式
 *
 *  抽象工厂模式：实现对产品家族（一系列具有不同分类维度的产品组合）的创建，不需要关心构建过程，只关心什么产品由什么工厂生产即可。
 *  建造者模式：要求按照指定的蓝图建造产，建造者模式更加关注与零件装配的顺序。
 *
 */


class Test {

    public static void main(String[] args) {
        MouseFactory.createObj(1).print();
    }
}


/**
 * 工厂设计模式：定义了一个创建对象的类，由这个类来实例化对象的行为
 */
public class MouseFactory {

    public static Mouse createObj(int type) {
        Mouse mouse = null;
        if (type == 0) {
            mouse = new XMMouse();
        } else {
            mouse = new HWMouse();
        }
        return mouse;
    }
}

/**
 * 生产鼠标
 */
interface Mouse {
    void print();
}


/**
 * 小米鼠标
 */
class XMMouse implements Mouse {

    @Override
    public void print() {
        System.out.println("小米鼠标");
    }
}

/**
 * 华为鼠标
 */
class HWMouse implements Mouse {

    @Override
    public void print() {
        System.out.println("华为鼠标");
    }
}
