package com.wefind.context;

public enum EnumTest {
    MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6),MAN(9);



    private int value;

    EnumTest(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
 class Test {
    public static void main(String[] args) {
        System.out.println("EnumTest.FRI 的 value = " + EnumTest.FRI.getValue()+EnumTest.MAN.getValue());
    }
}