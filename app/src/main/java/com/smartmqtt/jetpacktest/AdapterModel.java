package com.smartmqtt.jetpacktest;

/**
 * @author: kerry
 * date: On $ {DATE}
 * 适配器模式
 */
public class AdapterModel {

    public static void main(String[] args) {

        VoltageAdapter voltageAdapter = new VoltageAdapter();
        Phone phone = new Phone();
        phone.charging(voltageAdapter);
    }
}

/**
 * 手机需要 5V（dst 目标）充电 ，现在有 220V 交流电（src 被适配者），需要转接头转成 5V（充电器）
 */

//适配接口 5v
interface IVoltage5v {

    int output5v();

}

//适配接口 220v
class Voltage220v {

    public int output220v() {
        int src = 220;
        System.out.println("电压 = " + src + "伏");
        return src;
    }
}


class VoltageAdapter extends Voltage220v implements IVoltage5v {

    @Override
    public int output5v() {
        int output220V = output220v();
        //转换成 5v
        return output220V / 40;
    }
}

class Phone {

    public void charging(IVoltage5v iVoltage5v) {
        if (iVoltage5v.output5v() > 5) {
            System.out.println("不能充电");
        } else if (iVoltage5v.output5v() == 5) {
            System.out.println("可以充电");
        }
    }
}
