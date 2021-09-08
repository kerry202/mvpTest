package com.smartmqtt.jetpacktest;

/**
 * @author: kerry
 * date: On $ {DATE}
 * 建造者模式
 * 建造者模式（Builder Pattern） 又叫生成器模式，是一种对象构建模式。它可以将复杂对象的建造过程抽象出来（抽象类别），
 * 使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
 * 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们， 用户不需要知道内部的具体构建细节。
 * <p>
 * 四个角色：
 * Product（产品角色）： 一个具体的产品对象。
 * Builder（抽象建造者）： 创建一个 Product 对象的各个部件指定的 接口抽象类。
 * ConcreteBuilder（具体建造者）： 实现接口，构建和装配各个部件。
 * Director（指挥者）： 构建一个使用 Builder 接口的对象。它主要是用于创建一个复杂的对象。它主要有两个作用，一是：隔离了客户与对象的生产过程，二是：负责控制产品对象的生产过程。
 */


/**
 * 客户端(使用程序)不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。
 *
 * 每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者， 用户使用不同的具体建造者即可得到不同的产品对象。
 *
 * 主要解决在软件系统中，有时候面临着"一个复杂对象"的创建工作，其通常由各个部分的子对象用一定的算法构成；由于需求的变化，这个复杂对象的各个部分经常面临着剧烈的变化，但是将它们组合在一起的算法却相对稳定。
 *
 * 抽象工厂模式 VS 建造者模式
 *
 * 抽象工厂模式：实现对产品家族（一系列具有不同分类维度的产品组合）的创建，不需要关心构建过程，只关心什么产品由什么工厂生产即可。
 * 建造者模式：要求按照指定的蓝图建造产，建造者模式更加关注与零件装配的顺序。
 * */
public class ComputerModel {

    public static void main(String[] args) {
        ComputerConcreteBuilder computerConcreteBuilder = new ComputerConcreteBuilder();
        ComputerConcreteBuilder computerConcreteBuilder2 = new ComputerConcreteBuilder();

        Computer computer = computerConcreteBuilder.buildCpu("AMD")
                .buildDisk("金士顿")
                .buildMemory("三星")
                .build();

        Computer computer2 = computerConcreteBuilder2.buildCpu("AMD")
                .buildDisk("金士顿22")
                .buildMemory("三星22")
                .build();

        System.out.println(computer);
        System.out.println(computer2);
    }

}

/**
 * 具体要构建的对象，电脑
 */
class Computer {

    public String cpu;
    public String memory;
    public String disk;

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", disk='" + disk + '\'' +
                '}';
    }
}

/**
 * 抽象接口来描述电脑构建和装配过程
 */
interface ComputerBuilder {

    ComputerBuilder buildCpu(String cpu);

    ComputerBuilder buildMemory(String memory);

    ComputerBuilder buildDisk(String disk);

    Computer build();

}

/**
 * 定义ComputerBuild接口的具体实现,实现构建方法和装配电脑的各个组件
 */
class ComputerConcreteBuilder implements ComputerBuilder {

    private final Computer computer = new Computer();

    @Override
    public ComputerBuilder buildCpu(String cpu) {
        computer.cpu = cpu;
        return this;
    }

    @Override
    public ComputerBuilder buildMemory(String memory) {
        computer.memory = memory;
        return this;
    }

    @Override
    public ComputerBuilder buildDisk(String disk) {
        computer.disk = disk;
        return this;
    }

    @Override
    public Computer build() {
        return computer;
    }
}
