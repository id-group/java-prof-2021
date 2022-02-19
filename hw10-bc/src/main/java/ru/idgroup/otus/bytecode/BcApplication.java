package ru.idgroup.otus.bytecode;

import ru.idgroup.otus.bytecode.demo.TestLogging;
import ru.idgroup.otus.bytecode.proxy.Ioc;

public class BcApplication {
    public static void main(String ... args) {
        TestLogging testClass = Ioc.createLoggingClass();
        testClass.calculation(1);
        testClass.calculation(2,3);
        testClass.calculation(4,5,"test");
    }
}
