package ru.idgroup.otus.bytecode.demo;

import ru.idgroup.otus.bytecode.annotations.Log;

public class TestLoggingImpl implements TestLogging{

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println(param1);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println(param1 + " " + param2);
    }

    //@Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println(param1 + " " + param2 + " " + param3);
    }
}
