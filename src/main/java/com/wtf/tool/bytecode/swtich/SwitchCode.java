package com.wtf.tool.bytecode.swtich;

/**
 * @author strugglesnail
 * @date 2021/3/16
 * @desc
 */
public class SwitchCode {

    static void switchCode() {
        SwitchEnum switchEnum = SwitchEnum.ONE;
        switch (switchEnum) {
            case ONE:
                System.out.println("1");
                break;
            case TWO:
                System.out.println("2");
                break;
            case THREE:
                System.out.println("3");
                break;
            default:
                System.out.println("0");
                break;
        }
    }

    enum SwitchEnum {
       ONE,
       TWO ,
        THREE
    }
}
