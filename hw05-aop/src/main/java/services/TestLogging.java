package services;

import annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Override
    @Log
    public void calculation(int param1) {
        System.out.println("Calculation with one parameter. Result:" + param1);
    }

    @Override
    @Log
    public void calculation(int param1, int param2) {
        System.out.println("Calculation with two parameters. Result:" + (param1 + param2));
    }

    @Override
    @Log
    public void calculation(int param1, int param2, int param3) {
        System.out.println("Calculation with three parameters. Result:" + (param1 + param2 + param3));
    }
}
